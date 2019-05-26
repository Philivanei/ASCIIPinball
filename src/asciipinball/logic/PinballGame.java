package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.objects.physicobject.polygonial.LineWall;
import asciipinball.shapes.Line;

public class PinballGame {
    public static final float GRAVITATION = 0.00001f;
    public static final int OFFSET = 10;
    public static final int WIDTH = 200;
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
        ball = new Ball(40f,70f,2.5f,0,0.0f);
        players = new Player[4];
        physicEntities = new PhysicEntity[300];
        table = new Table(WIDTH, HEIGHT);

        //TODO
        //DEBUG STUFF REMOVE BEFORE RELEASE!
        physicEntities[0] = new LineWall(30, 30,50,0);

    }

    public void simulateTick(){
        ball.calculateFuture(PinballGame.GRAVITATION);
        Ball newBall = updateAll();

        if(newBall != null){
            ball.updateBall(newBall);
        }else{
            ball.updateBall();
        }

        printAll();
    }

    public Ball updateAll(){

        Ball resultBall = null;

        for (PhysicEntity physicEntity : physicEntities) {
            if(physicEntity != null){
                Ball physicEntityBall = physicEntity.updateEntity(ball);
                if(physicEntityBall != null){
                    resultBall = physicEntityBall;
                }
            }
        }

        Ball tableBall = table.updateEntity(ball);
        if(tableBall != null){
            resultBall = tableBall;
        }

        return  resultBall;
    }

    public void printAll(){

        gameView.clearCanvas();

        table.addToCanvas(gameView);

        for (PhysicEntity physicEntity : physicEntities) {
            if(physicEntity != null) {
                physicEntity.addToCanvas(gameView);
            }
        }

        ball.addToCanvas(gameView);

        gameView.printCanvas();
    }

}
