import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

public class DrawMainComponent extends Panel{

    public static final int VIEW_WIDTH = 40;
    public static final int VIEW_HEIGHT = 40;
    public static final int VIEW_NUMBER = 15;
    private Image iBuffer;
    private Graphics gBuffer;
    private Snake snake;
    private Node egg;

    /**
     * draws main frame
     * @param snake the active snake
     * @param egg the active egg
     */
    public DrawMainComponent(Snake snake, Node egg) {

        this.snake = snake;
        this.egg = egg;
    }

    /**
     * rewrite the paint method, distinguished from the built-in one
     */
    @Override
    public void paint(Graphics g) {

        snake.snakeMove();
        this.drawGridding(g);
        this.drawSnake(g);
        this.drawEgg(g);
        snake.eatEgg(egg);
    }

    /**
     * rewrite the update method, distinguished from the built-in one
     */
    @Override
    public void update(Graphics g) {
        if(iBuffer==null)
        {
            iBuffer=createImage(DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER + 1, DrawMainComponent.VIEW_HEIGHT * DrawMainComponent.VIEW_NUMBER + 1);
            gBuffer=iBuffer.getGraphics();
        }
        gBuffer.setColor(getBackground());
        gBuffer.fillRect(0,0, DrawMainComponent.VIEW_WIDTH * DrawMainComponent.VIEW_NUMBER + 1, DrawMainComponent.VIEW_HEIGHT * DrawMainComponent.VIEW_NUMBER + 1);
        paint(gBuffer);
        g.drawImage(iBuffer,0,0,this);
    }

    /**
     * Draw the nodes specified
     */
    public void drawGridding(Graphics g) {

        g.setColor(new Color(128,128,128));
        for(int i = 0; i < VIEW_WIDTH; i++) {
            g.drawLine(0, i * VIEW_NUMBER, VIEW_WIDTH * VIEW_NUMBER, i * VIEW_NUMBER);
        }
        for(int i = 0; i < VIEW_HEIGHT; i++) {
            g.drawLine(i * VIEW_NUMBER, 0, i * VIEW_NUMBER, VIEW_WIDTH * VIEW_NUMBER);
        }

    }

    /**
     * draw the snake
     * The snake is separated to two parts. The head is marked deep-grey, while other body parts are light-grey
     */
    public void drawSnake(Graphics g) {

        for(int i = 0; i < snake.getSnake().size(); i ++) {
            g.setColor(Color.white);
            if(i == 0)
                g.setColor(Color.lightGray);
            g.fillRect(snake.getSnake().get(i).getNodeX(), snake.getSnake().get(i).getNodeY(), VIEW_NUMBER, VIEW_NUMBER);
        }
    }

    /**
     * Draw the egg on the grid
     */
    public void drawEgg(Graphics g) {

        g.setColor(Color.yellow);
        g.fillRect(egg.getNodeX(), egg.getNodeY(), VIEW_NUMBER, VIEW_NUMBER);
    }

}