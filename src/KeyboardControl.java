import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Keyboard Controller which can be TRIGGERED BY USER TOUCHING KEYBOARD
 * @apiNote The keyboard controller will only work while the user touch key 10, 27, 37, 38, 39, 40.
 * @author This is my partner's work
 */
public class KeyboardControl implements KeyListener {
    private Snake snake;
    private static Logger logger = Logger.getLogger("KeyboardControl");

    /**
     * Keyboard controller detects keyboard input
     * @param snake find the snake and change the direction of it
     */
    public KeyboardControl(Snake snake){
        this.snake = snake;
    }

    /**
     * Keyboard controller only acts when the key is released
     * @param keyEvent triggers when the key is pressed
     * 0=up, 1=right, 2=down, 3=left
     */
    public void keyReleased(KeyEvent keyEvent){
        if (snake.getSnake().size() > 1) {
            if ((snake.getDirection() == 0 || snake.getDirection() == 2)) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_RIGHT ->snake.setDirection(1);
                    case KeyEvent.VK_LEFT -> snake.setDirection(3);
                    case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> logger.log(Level.WARNING, "Key pressing ignored");
                }
            } else {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_UP ->snake.setDirection(0);
                    case KeyEvent.VK_DOWN -> snake.setDirection(2);
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT -> logger.log(Level.WARNING, "Key pressing ignored");
                }
            }
        } else {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_RIGHT -> snake.setDirection(1);
                case KeyEvent.VK_LEFT -> snake.setDirection(3);
                case KeyEvent.VK_UP -> snake.setDirection(0);
                case KeyEvent.VK_DOWN -> snake.setDirection(2);
            }
        }
        logger.log(Level.WARNING, "Key pressed, keycode " + keyEvent.getExtendedKeyCode());
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
            logger.log(Level.WARNING, "Game exit");
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            Main.gameState = !Main.gameState;
            logger.log(Level.WARNING, "Game paused");
        }
    }
    public void keyPressed(KeyEvent keyEvent){}
    public void keyTyped(KeyEvent keyEvent){}
}
