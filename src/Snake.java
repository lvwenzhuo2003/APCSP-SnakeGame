import java.util.ArrayList;
import java.util.Random;

public class Snake {
    private ArrayList<Node> snake;
    private Random random;
    private int direction = 0;//0=up, 1=right, 2=down, 3=left

    private Main main;

    public Snake(int snakeHeadX, int snakeHeadY, Main main){
        random = new Random();
        snake =new ArrayList<>();
        snake.add(new Node(snakeHeadX,snakeHeadY));
        this.main = main;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }
    public Node getSnakeHead(){
        return snake.get(0);
    }

    public ArrayList<Node> getSnake(){
        return snake;
    }
    public void snakeMove(){
        switch (direction) {
            case 0 -> snake.add(0,new Node(getSnakeHead().getNodeX(), getSnakeHead().getNodeY() - DrawMainComponent.VIEW_NUMBER));
            case 1 -> snake.add(0,new Node(getSnakeHead().getNodeX() + DrawMainComponent.VIEW_NUMBER, getSnakeHead().getNodeY()));
            case 2 -> snake.add(0,new Node(getSnakeHead().getNodeX(), getSnakeHead().getNodeY() + DrawMainComponent.VIEW_NUMBER));
            case 3 -> snake.add(0,new Node(getSnakeHead().getNodeX() - DrawMainComponent.VIEW_NUMBER, getSnakeHead().getNodeY()));
        }
        snake.remove(snake.size() - 1);
    }
    public void eatEgg(Node egg){
        if(snake.get(0).getNodeX() == egg.getNodeX() && snake.get(0).getNodeY() == egg.getNodeY()) {
            snake.add(egg);
            //Generate egg
            main.setEgg(random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER,
                    random.nextInt(DrawMainComponent.VIEW_WIDTH - 1) * DrawMainComponent.VIEW_NUMBER);
            main.gameScore = main.gameScore + 5;
            main.getCurrentScore().setText(main.gameScore + "");
            System.out.print("\07");
        }
    }
    public void snakeRunInterface(){
        if(this.getSnakeHead().getNodeX() < 0 || this.getSnakeHead().getNodeY() < 0||
                this.getSnakeHead().getNodeX() > (DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER)||
                this.getSnakeHead().getNodeY() > (DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER)) {
            Main.gameState = false;
        }
    }
}
