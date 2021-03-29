import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class Main extends Frame {
    public static int gameScore = 0;
    public static boolean gameState = true;
    private Panel rule;
    private Random random;
    private Snake snake;
    private Node egg;
    private DrawMainComponent drawMainComponent;
    private RunGame runGame;
    private final JLabel title = new JLabel("Snake Eat Egg Game v1.0", JLabel.CENTER);
    private final JLabel scoreTitle = new JLabel("Score", JLabel.CENTER);
    private JLabel scoreBulletinBoard;
    private Panel panel;
    private static Thread thread;
    private final JTextArea hint = new JTextArea("Use following key to control:\n      ↑\n  ←      →\n      ↓\n\n[Esc] to exit");
    public Main(){
        random = new Random();
        snake = new Snake(10 * DrawMainComponent.VIEW_NUMBER + random.nextInt(19) * DrawMainComponent.VIEW_NUMBER,
                10 * DrawMainComponent.VIEW_NUMBER + random.nextInt(19) * DrawMainComponent.VIEW_NUMBER,this);
        egg = new Node(random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER,
                random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER);
        drawMainComponent = new DrawMainComponent(snake, egg);
        runGame = new RunGame(drawMainComponent,snake);
        thread = new Thread(runGame);
        panel = new Panel();
        scoreBulletinBoard = new JLabel(gameScore + "", JLabel.CENTER);
        rule = new Panel();
    }
    public void setEgg(int eggNodeX, int eggNodeY){
        this.egg.setNodeX(eggNodeX);
        this.egg.setNodeY(eggNodeY);
    }
    public JLabel getCurrentScore(){
        return scoreBulletinBoard;
    }
    public void showView() {

        title.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 18));
        title.setForeground(Color.white);
        title.setBounds(0, 0, DrawMainComponent.VIEW_HEIGHT * DrawMainComponent.VIEW_NUMBER + 70, 40);
        drawMainComponent.setBackground(new Color(51,51,51));
        drawMainComponent.setBounds(20, 50, DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER + 1, DrawMainComponent.VIEW_HEIGHT * DrawMainComponent.VIEW_NUMBER + 1);
        panel.setLayout(null);
        panel.setBackground(new Color(0,102,102));
        panel.setBounds(DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER + 40, 50, 150, DrawMainComponent.VIEW_HEIGHT * DrawMainComponent.VIEW_NUMBER);
        scoreTitle.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 18));
        scoreTitle.setForeground(Color.white);
        scoreTitle.setBounds(0, 0, 150, 50);
        scoreBulletinBoard.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
        scoreBulletinBoard.setForeground(Color.white);
        scoreBulletinBoard.setBounds(0, 50, 150, 40);
        rule.setBackground(new Color(0,128,128));
        rule.setBounds(0, 90, 150, 1);
        hint.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        hint.setBounds(10, 100, 130, 150);
        hint.setBackground(new Color(0,102,102));

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
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        this.setVisible(true);

    }
    public static void main(String[] args){
        new Main().showView();
        thread.start();
    }
}
