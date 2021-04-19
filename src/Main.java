import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main UI Controller and thread creator
 * @author SteveLyu03 (lvwenzhuo2003@126.com)
 */
public class Main extends Frame {
    @Serial
    private static final long serialVersionUID = -2315964205549870089L;

    private final Random random;

    public static boolean canContinue = false;
    public static int refreshRate;
    public static int gameScore = 0;
    public static boolean gameState = true;
    public static Thread thread;
    public static String[] arguments;

    private final Panel rule;
    private Snake snake;
    private Node egg;
    private DrawMainComponent drawMainComponent;
    private RunGame runGame;
    private Panel panel;

    private final JLabel title = new JLabel("Snake Eat Egg Game v1.0", JLabel.CENTER);
    private final JLabel scoreTitle = new JLabel("Score", JLabel.CENTER);
    private JLabel scoreBulletinBoard;
    private final JTextArea hint = new JTextArea("Use following\nkey to control:\n      ↑\n  ←      →\n      ↓\n\n[Esc] to exit\n[Enter] to pause");

    private static Logger logger = Logger.getLogger("Main");

    /**
     * This is the main frame of the game
     */
    public Main(){
        random = new Random();
        logger.log(Level.INFO, "Random initialized");
        snake = new Snake(10 * DrawMainComponent.VIEW_NUMBER + random.nextInt(19) * DrawMainComponent.VIEW_NUMBER,
                10 * DrawMainComponent.VIEW_NUMBER + random.nextInt(19) * DrawMainComponent.VIEW_NUMBER,this);
        logger.log(Level.INFO, "Snake created");
        egg = new Node(random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER,
                random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER);
        logger.log(Level.INFO, "Egg created");
        drawMainComponent = new DrawMainComponent(snake, egg);
        logger.log(Level.INFO, "Main component initialized");
        runGame = new RunGame(drawMainComponent,snake);
        thread = new Thread(runGame);
        panel = new Panel();
        scoreBulletinBoard = new JLabel(gameScore + "", JLabel.CENTER);
        rule = new Panel();
    }

    /**
     * This is the function will be called when the snake meets the egg
     * @param eggNodeX egg coordination
     * @param eggNodeY egg coordination
     */
    public void setEgg(int eggNodeX, int eggNodeY){
        this.egg.setNodeX(eggNodeX);
        this.egg.setNodeY(eggNodeY);
    }

    /**
     * This is the bulletin board of the score
     * @return the bulletin board
     */
    public JLabel getCurrentScore(){
        return scoreBulletinBoard;
    }

    /**
     * main frame
     * @throws InterruptedException see below
     */
    public void showView() throws InterruptedException {
        if (2 == arguments.length && arguments[0].equalsIgnoreCase("--difficulty")) {
            if (arguments[1].equalsIgnoreCase("easy")) {
                Main.refreshRate = 500;
            } else if (arguments[1].equalsIgnoreCase("hard")) {
                Main.refreshRate = 200;
            } else if (arguments[1].equalsIgnoreCase("expert")) {
                Main.refreshRate = 100;
            }
            canContinue = true;
        } else if (2 == arguments.length && arguments[0].equalsIgnoreCase("--refreshrate")){
            try {
                Main.refreshRate = Integer.parseInt(arguments[1]);
            } catch (NumberFormatException e){
                e.printStackTrace();
                System.out.println("\033[1;31;40mError\033[0m: Invalid arguments, you should input a number");
                System.out.println("Usage: ");
                System.out.println("\t...(main program) [--difficulty {\"easy\",\"hard\",\"expert\"}]");
                System.out.println("OR");
                System.out.println("\t...(main program) [--refreshrate (in milliseconds)] (smaller number means faster rate)");
                System.out.println("OR");
                System.out.println("\t...(main program)");
                System.exit(1);
            }
            canContinue = true;
        } else if (0 == arguments.length){
            boolean once = true;
            do {
                if (once) {
                    new showDifficultyDialog();
                    once = false;
                }
                Thread.sleep(100);//F**king Java
                //Dont ask me why to sleep 0.01 second can run the program. I dont know why.
                //Dont try to delete this. I spend a whole night to find this solution.
            } while (!canContinue);
        } else {
            System.out.println("\033[1;31;40mError\033[0m: Invalid arguments");
            System.out.println("Usage: ");
            System.out.println("\t...(main program) [--difficulty {\"easy\",\"hard\",\"expert\"}]");
            System.out.println("OR");
            System.out.println("\t...(main program) [--refreshrate (in milliseconds)] (smaller number means faster rate)");
            System.out.println("OR");
            System.out.println("\t...(main program)");
            System.exit(1);
        }

        title.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 18));
        title.setForeground(Color.white);
        title.setBounds(0, 0, DrawMainComponent.VIEW_HEIGHT * DrawMainComponent.VIEW_NUMBER + 70, 40);
        drawMainComponent.setBackground(new Color(0,0,0));
        drawMainComponent.setBounds(20, 50, DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER + 1, DrawMainComponent.VIEW_HEIGHT * DrawMainComponent.VIEW_NUMBER + 1);
        panel.setLayout(null);
        panel.setBackground(new Color(255,255,255));
        panel.setBounds(DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER + 40, 50, 150, DrawMainComponent.VIEW_HEIGHT * DrawMainComponent.VIEW_NUMBER);
        scoreTitle.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 18));
        scoreTitle.setForeground(new Color(0,0,0));
        scoreTitle.setBounds(0, 0, 150, 50);
        scoreTitle.setBackground(new Color(255,255,255));
        scoreBulletinBoard.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
        scoreBulletinBoard.setForeground(new Color(0,0,0));
        scoreBulletinBoard.setBounds(0, 50, 150, 40);
        scoreBulletinBoard.setBackground(new Color(255,255,255));
        rule.setBackground(new Color(0,0,0));
        rule.setBounds(0, 90, 150, 1);
        hint.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        hint.setForeground(new Color(0,0,0));
        hint.setBounds(10, 100, 130, 200);
        hint.setBackground(new Color(255,255,255));

        panel.add(scoreTitle);
        panel.add(scoreBulletinBoard);
        panel.add(rule);
        panel.add(hint);
        this.add(title);
        this.setTitle("SnakeGame-3.0");
        this.setSize(DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER + 210, DrawMainComponent.VIEW_HEIGHT * DrawMainComponent.VIEW_NUMBER + 70);
        this.setLocation(500, 200);
        this.setLayout(null);
        this.setBackground(new Color(0,128,128));
        this.add(drawMainComponent);
        this.add(panel);
        this.addKeyListener(new KeyboardControl(snake));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                logger.log(Level.WARNING, "User closed window");
                System.exit(0);
            }
        }
        );
        this.setVisible(true);
    }


    /**
     * Main program
     * @throws InterruptedException see below
     * @see Main
     * @param args receives arguments from command line (although this program does not need it)
     */
    public static void main(String[] args) throws InterruptedException{
        arguments = args;
        new Main().showView();
        while (true) {
            if (canContinue) {
                thread.start();
                break;
            }
        }
    }
}
