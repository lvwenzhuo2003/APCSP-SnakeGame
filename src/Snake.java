import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the main snake which will run on the screen UI
 * @author This is co-worked
 */
public class Snake {
    private ArrayList<Node> snake;
    private Random random;
    private int direction = 0;//0=up, 1=right, 2=down, 3=left
    private static Logger logger = Logger.getLogger("Snake");
    private Main main;
    public boolean snakeHitItself;

    /**
     * This is the snake. It is saved as ArrayList<Node>
     * @param snakeHeadX saves the snake head
     * @param snakeHeadY saves the snake body
     * @param main the component where the snake should be drawn to
     * @author This is my partner's work
     */
    public Snake(int snakeHeadX, int snakeHeadY, Main main){
        random = new Random();
        snake = new ArrayList<>();
        snake.add(new Node(snakeHeadX,snakeHeadY));
        this.main = main;
        logger.log(Level.INFO, "Created snake");
    }

    /**
     * @author This is my partner's work
     * @return the direction of the snake
     * @see Snake
     */
    public int getDirection(){
        return direction;
    }

    /**
     * Sets where snake should go to.
     * @param direction See above: direction
     * @see Snake
     * @author This is my partner's work
     */
    public void setDirection(int direction){
        this.direction = direction;
    }

    /**
     * This is the snake head.
     * @return the coordination of snake head
     * @author This is my partner's work
     */
    public Node getSnakeHead(){
        return snake.get(0);
    }

    /**
     * This is the snake body.
     * @return the ArrayList<Node> of snake
     * @author This is my partner's work
     */
    public ArrayList<Node> getSnake(){
        return snake;
    }

    /**
     * Allows snake to move on grid
     * @author This is my partner's work
     */
    public void snakeMove(){
        switch (direction) {//0=up, 1=right, 2=down, 3=left
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
     * @author This is my work
     */
    public void eatEgg(Node egg){
        if(snake.get(0).getNodeX() == egg.getNodeX() && snake.get(0).getNodeY() == egg.getNodeY()) {
            snake.add(egg);
            //Generate egg
            main.setEgg(random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER, random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER);
            Main.gameScore = Main.gameScore + 5;
            main.getCurrentScore().setText(Main.gameScore + "");
            Toolkit.getDefaultToolkit().beep();//Let computer signals you have eaten an egg
            logger.log(Level.WARNING, "Snake eat an egg, position (" + egg.getNodeX() + ", " + egg.getNodeY() + ")");
        }

    }

    /**
     * When the snake head touches the bound, this function will be triggered.
     * When it really happens, the game will stop
     * @author This is my work
     */
    public void snakeRunInterface() throws InterruptedException, URISyntaxException, IOException {
        snakeHitItself = false;
        label:
        for (int i = 1; i < snake.size(); i++){
            Node each = snake.get(i);
            //0=up, 1=right, 2=down, 3=left
            switch (direction) {
                case 0:
                    if (each.getNodeX() == getSnakeHead().getNodeX() && each.getNodeY() == getSnakeHead().getNodeY() - DrawMainComponent.VIEW_NUMBER) {
                        snakeHitItself = true;
                        Thread.sleep(Main.refreshRate);
                        break label;
                    }
                    break;
                case 1:
                    if (each.getNodeX() == getSnakeHead().getNodeX() + DrawMainComponent.VIEW_NUMBER && each.getNodeY() == getSnakeHead().getNodeY()) {
                        snakeHitItself = true;
                        Thread.sleep(Main.refreshRate);
                        break label;
                    }
                    break;
                case 2:
                    if (each.getNodeX() == getSnakeHead().getNodeX() && each.getNodeY() == getSnakeHead().getNodeY() + DrawMainComponent.VIEW_NUMBER) {
                        snakeHitItself = true;
                        Thread.sleep(Main.refreshRate);
                        break label;
                    }
                    break;
                case 3:
                    if (each.getNodeX() == getSnakeHead().getNodeX() - DrawMainComponent.VIEW_NUMBER && each.getNodeY() == getSnakeHead().getNodeY()) {
                        snakeHitItself = true;
                        Thread.sleep(Main.refreshRate);
                        break label;
                    }
                    break;
            }
        }
        if ((this.getSnakeHead().getNodeX() < 0 || this.getSnakeHead().getNodeY() < 0||
                this.getSnakeHead().getNodeX() > (DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER)||
                this.getSnakeHead().getNodeY() > (DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER)) || snakeHitItself) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"Game Over!","Game Over!",JOptionPane.INFORMATION_MESSAGE);
            logger.log(Level.WARNING, "Player failed");
            logger.log(Level.WARNING, "Player restarting the game");
            Main.getWindows()[0].setVisible(false);
            System.exit(0);
        }
    }
}
