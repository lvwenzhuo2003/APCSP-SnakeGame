import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Snake {
    private ArrayList<Node> snake;
    private Random random;
    private int direction = 0;//0=up, 1=right, 2=down, 3=left
    private static Logger logger = Logger.getLogger("Snake");
    private Main main;

    /**
     * This is the snake. It is saved as ArrayList<Node>
     * @param snakeHeadX saves the snake head
     * @param snakeHeadY saves the snake body
     * @param main the component where the snake should be drawn to
     */
    public Snake(int snakeHeadX, int snakeHeadY, Main main){
        random = new Random();
        snake = new ArrayList<>();
        snake.add(new Node(snakeHeadX,snakeHeadY));
        this.main = main;
        logger.log(Level.INFO, "Created snake");
    }

    /**
     * Sets where snake should go to.
     * @param direction See above: direction
     */
    public void setDirection(int direction){
        this.direction = direction;
    }

    /**
     * This is the snake head.
     * @return the coordination of snake head
     */
    public Node getSnakeHead(){
        return snake.get(0);
    }

    /**
     * This is the snake body.
     * @return the ArrayList<Node> of snake
     */
    public ArrayList<Node> getSnake(){
        return snake;
    }

    /**
     * Allows snake to move on grid
     */
    public void snakeMove(){
        switch (direction) {
            case 0 -> {
                snake.add(0,new Node(getSnakeHead().getNodeX(), getSnakeHead().getNodeY() - DrawMainComponent.VIEW_NUMBER));
                logger.log(Level.CONFIG, "Snake changed direction to up");
            }
            case 1 -> {
                snake.add(0,new Node(getSnakeHead().getNodeX() + DrawMainComponent.VIEW_NUMBER, getSnakeHead().getNodeY()));
                logger.log(Level.CONFIG, "Snake changed direction to right");
            }
            case 2 -> {
                snake.add(0,new Node(getSnakeHead().getNodeX(), getSnakeHead().getNodeY() + DrawMainComponent.VIEW_NUMBER));
                logger.log(Level.CONFIG, "Snake changed direction to down");
            }
            case 3 -> {
                snake.add(0,new Node(getSnakeHead().getNodeX() - DrawMainComponent.VIEW_NUMBER, getSnakeHead().getNodeY()));
                logger.log(Level.CONFIG, "Snake changed direction to left");
            }
        }
        snake.remove(snake.size() - 1);
        logger.log(Level.CONFIG, "Snake moved");
    }

    /**
     * When snake head touches the egg, this function will be triggered.
     * When the egg was eaten, the snake will grow up with 1 length.
     * @param egg where the egg is
     */
    public void eatEgg(Node egg){
        if(snake.get(0).getNodeX() == egg.getNodeX() && snake.get(0).getNodeY() == egg.getNodeY()) {
            snake.add(egg);
            //Generate egg
            main.setEgg(random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER,
                    random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER);
            Main.gameScore = Main.gameScore + 5;
            main.getCurrentScore().setText(Main.gameScore + "");
            Toolkit.getDefaultToolkit().beep();//Let computer signals you have eaten an egg
            logger.log(Level.WARNING, "Snake eat an egg, position (" + egg.getNodeX() + ", " + egg.getNodeY() + ")");
        }

    }

    /**
     * When the snake head touches the bound, this function will be triggered.
     * When it really happens, the game will stop
     */
    public void snakeRunInterface() throws InterruptedException, URISyntaxException, IOException {
        if (this.getSnakeHead().getNodeX() < 0 || this.getSnakeHead().getNodeY() < 0||
                this.getSnakeHead().getNodeX() > (DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER)||
                this.getSnakeHead().getNodeY() > (DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER)) {
            Toolkit.getDefaultToolkit().beep();
            boolean dieRestart = JOptionPane.showConfirmDialog(null,"Game Over!\nRestart the game?","Game Over!",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE) == 0;
            logger.log(Level.WARNING, "Player failed");
            if (dieRestart){
                logger.log(Level.WARNING, "Player restarting the game");
                final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                final String javaCompiler = System.getProperty("java.home") + File.separator + "bin" + File.separator + "javac";
                final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                Main.getWindows()[0].setVisible(false);

                final ArrayList<String> command = new ArrayList<>();
                /* is it a jar file? */
                if(!currentJar.getName().endsWith(".jar")) {
                    logger.log(Level.WARNING, "The program is running under .class, will try compile a new one and run");
                    command.add(javaCompiler);
                    command.add("Main.java");
                    final ProcessBuilder builder = new ProcessBuilder(command);
                    //builder.redirectOutput(ProcessBuilder.Redirect.PIPE);
                    builder.directory(currentJar);
                    builder.start();
                    command.clear();
                    command.add(javaBin);
                    command.add("Main");
                    command.addAll(Arrays.asList(Main.arguments));
                    final ProcessBuilder runner = new ProcessBuilder(command);
                    //runner.redirectOutput(ProcessBuilder.Redirect.PIPE);
                    runner.directory(currentJar);
                    runner.start();
                    System.exit(0);
                }

                // Build command: java -jar application.jar
                logger.log(Level.WARNING, "The program is running as a .jar, will start a new instance");
                command.add(javaBin);
                command.add("-jar");
                command.add(currentJar.getPath());
                command.addAll(Arrays.asList(Main.arguments));


                final ProcessBuilder runner = new ProcessBuilder(command);
                runner.start();
            }
            System.exit(0);
        }
    }
}
