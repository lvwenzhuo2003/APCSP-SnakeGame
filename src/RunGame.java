@SuppressWarnings("InfiniteLoopStatement")
public class RunGame implements Runnable {

    private DrawMainComponent drawMainComponent;
    private Snake snake;

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
            snake.snakeRunInterface();
            if (Main.gameState){
                drawMainComponent.repaint();
            }
            try {
                Thread.sleep(Main.refreshRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
