import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This is the thread runner
 * @author SteveLyu03 (lvwenzhuo2003@126.com)
 */
@SuppressWarnings("InfiniteLoopStatement")
public class RunGame implements Runnable {

    private DrawMainComponent drawMainComponent;
    private Snake snake;
    private static Logger logger = Logger.getLogger("RunGame");

    /**
     * This is the game running
     * @param drawMainComponent main frame
     * @param snake the snake
     */
    public RunGame(DrawMainComponent drawMainComponent, Snake snake){
        this.drawMainComponent = drawMainComponent;
        this.snake = snake;
    }

    /**
     * Rewrite the default run()
     * The snake will refresh every specified refreshRate (in milliseconds)
     */
    @Override
    public void run(){
        while(true){
            try {
                snake.snakeRunInterface();
            } catch (InterruptedException | URISyntaxException | IOException e) {
                logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            }
            if (Main.gameState){
                drawMainComponent.repaint();
            }
            try {
                Thread.sleep(Main.refreshRate);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
