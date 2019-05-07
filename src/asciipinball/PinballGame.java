package asciipinball;

public class PinballGame {
    public static final float GRAVITATION = (float) 0.3;
    public static final int OFFSET = 0;

    private int width;
    private int hight;

    private GameView gameView;
    private Ball[] balls;
    private Player[] players;
    private Entity[] entities;
    private Table table;

    public PinballGame(int width, int hight) {

        /** Init asciipinball.asciipinball.asciipinball.GameView **/
        gameView = new GameView(30,30,"Pinball");
        gameView.setWindowsSize(GameView.WINDOWSIZE_LARGE);
        gameView.show();


        /**Init Arrays and Values**/
        table = new Table(width,hight);
        balls = new Ball[4];
        players = new Player[4];
        entities = new Entity[500];


        this.width = width;
        this.hight = hight;



        /** Test Stuff - Remove before final commit!**/
        balls[0] = new Ball(28, 1, (float) 120, (float) 2);

    }

    public void simulateTick(){

        for(int i = 0; i < balls.length; i++) {
            if (balls[i] != null) {
                balls[i].simulateTick(GRAVITATION);
                interactWithEntities(balls[i]);
                table.interactWithGameTable(balls[i], GRAVITATION);

            }
        }

    }

    public void drawAll(){

        gameView.clearCanvas();
        table.drawToCanvas(gameView, OFFSET);

        for(int i = 0; i < balls.length; i++) {
            if(balls[i] != null) {
                balls[i].drawToCanvas(gameView, OFFSET , hight);
            }
        }

        for(int i = 0; i < entities.length; i++){
            if(entities[i] != null){
                entities[i].drawToCanvas(gameView, OFFSET, hight);
            }
        }

        gameView.printCanvas();
    }

    public void interactWithEntities(Ball ball){

        for(int i = 0; i < entities.length; i++){
            entities[i].interactWithBall(ball);
        }

    }
}
