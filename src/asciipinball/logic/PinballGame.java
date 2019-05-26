package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.Settings;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.objects.physicobject.polygonial.LineWall;
import asciipinball.shapes.Line;

import java.util.Set;

public class PinballGame {

    public GameView gameView;

    //TODO IMPORTANT: Max Koordinate is WIDTH/HEIGHT -1


    private Ball ball;
    private Player[] players;
    private Table table;
    private PhysicEntity[] physicEntities;

    public PinballGame() {

        /** Init asciipinball.asciipinball.asciipinball.GameView **/
        gameView = new GameView(Settings.HEIGHT, 240, "Pinball");
        gameView.setWindowsSize(GameView.WINDOWSIZE_LARGE);
        gameView.show();


        /**Init Arrays and Values**/
        ball = new Ball(40f,70f,2.5f,90,0.03f);
        players = new Player[4];
        physicEntities = new PhysicEntity[300];
        table = new Table(Settings.WIDTH, Settings.HEIGHT);

        //TODO
        //DEBUG STUFF REMOVE BEFORE RELEASE!
        physicEntities[0] = new LineWall(30, 30,50,0);

    }

    public void simulateTick(){
        ball.calculateFuture(Settings.GRAVITATION);
        Ball newBall = updateAll();

        if(newBall != null){
            ball.updateBall(newBall);
        }else{
            ball.updateBall();
        }

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
