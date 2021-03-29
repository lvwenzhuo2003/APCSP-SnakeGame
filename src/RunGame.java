public class RunGame implements Runnable{

    private int refreshRate = 100;
    private DrawMainComponent drawMainComponent;
    private Snake snake;

    public RunGame(DrawMainComponent drawMainComponent, Snake snake){
        this.drawMainComponent = drawMainComponent;
        this.snake = snake;
    }

    public void run(){
        while(true){
            snake.snakeRunInterface();
            if (Main.gameState){
                drawMainComponent.repaint();
            }
            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException exp){
                exp.printStackTrace();
            }
        }
    }
}
