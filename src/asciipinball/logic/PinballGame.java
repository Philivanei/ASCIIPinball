package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Line;

public class PinballGame {
    public static final float GRAVITATION = (float) 0.3;
    public static final int OFFSET = 10;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 135;

    public GameView gameView;

    //TODO IMPORTANT: Max Koordinate is WIDTH/HEIGHT -1


    private Ball ball;
    private Player[] players;
    private Table table;
    private PhysicEntity[] physicEntities;

    public PinballGame() {

        /** Init asciipinball.asciipinball.asciipinball.GameView **/
        gameView = new GameView(HEIGHT, 240, "Pinball");
        gameView.setWindowsSize(GameView.WINDOWSIZE_LARGE);
        gameView.show();


        /**Init Arrays and Values**/
        ball = new Ball(40f,40f,2.5f);
        players = new Player[4];
        physicEntities = new PhysicEntity[300];
        table = new Table(WIDTH, HEIGHT);

        //TODO
        /**DEBUG STUFF REMOVE BEFORE RELEASE!**/
        Line testLine = new Line(0,0,20,150);
        //Line testLine = new Line(20,3,33,122);
        //Line testLine = new Line(20,122,25,3);
        testLine.addToCanvas(gameView);
        gameView.printCanvas();
    }

    public void updateAll(){
        ball.calculateFuture(PinballGame.GRAVITATION);
        for (PhysicEntity physicEntity : physicEntities) {
            if(physicEntity != null){
                physicEntity.updateEntity(ball);
            }
        }
    }

    public void addAllToCanvas(){

        table.addToCanvas(gameView);

        for (PhysicEntity physicEntity : physicEntities) {
            if(physicEntity != null) {
                physicEntity.addToCanvas(gameView);
            }
        }

        ball.addToCanvas(gameView);
    }

}
