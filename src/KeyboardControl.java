import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardControl implements KeyListener {
    private Snake snake;
    public KeyboardControl(Snake snake){
        this.snake = snake;
    }

    public void keyReleased(KeyEvent keyEvent){
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP -> snake.setDirection(0);
            case KeyEvent.VK_DOWN -> snake.setDirection(2);
            case KeyEvent.VK_RIGHT ->snake.setDirection(1);
            case KeyEvent.VK_LEFT -> snake.setDirection(3);
            case KeyEvent.VK_ESCAPE -> System.exit(0);
            case KeyEvent.VK_ENTER -> Main.gameState = !Main.gameState;
        }
    }
    public void keyPressed(KeyEvent keyEvent){}
    public void keyTyped(KeyEvent keyEvent){}
}
