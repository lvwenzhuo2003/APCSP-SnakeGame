import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

public class Snake {
    private ArrayList<Node> snake;
    private Random random;
    private int direction = 0;//0=up, 1=right, 2=down, 3=left

    private Main main;

    /**
     * This is the snake. It is saved as ArrayList<Node>
     * @param snakeHeadX saves the snake head
     * @param snakeHeadY saves the snake body
     * @param main the component where the snake should be drawed to
     */
    public Snake(int snakeHeadX, int snakeHeadY, Main main){
        random = new Random();
        snake =new ArrayList<>();
        snake.add(new Node(snakeHeadX,snakeHeadY));
        this.main = main;
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
            case 0 -> snake.add(0,new Node(getSnakeHead().getNodeX(), getSnakeHead().getNodeY() - DrawMainComponent.VIEW_NUMBER));
            case 1 -> snake.add(0,new Node(getSnakeHead().getNodeX() + DrawMainComponent.VIEW_NUMBER, getSnakeHead().getNodeY()));
            case 2 -> snake.add(0,new Node(getSnakeHead().getNodeX(), getSnakeHead().getNodeY() + DrawMainComponent.VIEW_NUMBER));
            case 3 -> snake.add(0,new Node(getSnakeHead().getNodeX() - DrawMainComponent.VIEW_NUMBER, getSnakeHead().getNodeY()));
        }
        snake.remove(snake.size() - 1);
    }

    /**
     * When snake head touches the egg, this function will be triggered.
     * @implNote when the egg was eaten, the snake will grow up with 1 length.
     * @param egg where the egg is
     */
    public void eatEgg(Node egg){
        if(snake.get(0).getNodeX() == egg.getNodeX() && snake.get(0).getNodeY() == egg.getNodeY()) {
            snake.add(egg);
            //Generate egg
            main.setEgg(random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER,
                    random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER);
            main.gameScore = main.gameScore + 5;
            main.getCurrentScore().setText(main.gameScore + "");
            Toolkit.getDefaultToolkit().beep();//Let computer signals you have eaten an egg
        }
    }

    /**
     * When the snake head touches the bound, this function will be triggered.
     * @implNote when it really happens, the game will stop
     */
    public void snakeRunInterface(){
        if(this.getSnakeHead().getNodeX() < 0 || this.getSnakeHead().getNodeY() < 0||
                this.getSnakeHead().getNodeX() > (DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER)||
                this.getSnakeHead().getNodeY() > (DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER)) {
            Main.gameState = false;
        }
    }
}
