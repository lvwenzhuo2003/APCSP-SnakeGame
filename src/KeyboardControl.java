import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     */
    public void keyReleased(KeyEvent keyEvent){
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP -> snake.setDirection(0);
            case KeyEvent.VK_DOWN -> snake.setDirection(2);
            case KeyEvent.VK_RIGHT ->snake.setDirection(1);
            case KeyEvent.VK_LEFT -> snake.setDirection(3);
            case KeyEvent.VK_ESCAPE -> System.exit(0);
            case KeyEvent.VK_ENTER -> Main.gameState = !Main.gameState;
        }
        logger.log(Level.WARNING, "Key pressed, keycode " + keyEvent.getExtendedKeyCode());
    }
    public void keyPressed(KeyEvent keyEvent){}
    public void keyTyped(KeyEvent keyEvent){}
}
