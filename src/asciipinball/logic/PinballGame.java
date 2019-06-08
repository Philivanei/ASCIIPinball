package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.Settings;
import asciipinball.levels.Levels;
import asciipinball.logic.flipperfinger.FlipperFingerControl;
import asciipinball.logic.launchcontrol.LaunchControl;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.objects.physicobject.polygonial.Table;

import java.util.ArrayList;

public class PinballGame {

    public GameView gameView;

    /*IMPORTANT: Max Koordinate is WIDTH/HEIGHT -1*/


    private Ball ball;
    private Player[] players;
    private Table table;
    private PhysicEntity[] physicEntities;
    private FlipperFingerControl flipperFinger;
    private Control control;
    private LaunchControl launchControl;

    public PinballGame() {

        /** Init GameView **/
        gameView = new GameView(Settings.HEIGHT, 240, "Pinball");
        gameView.setWindowsSize(GameView.WINDOWSIZE_LARGE);
        gameView.show();


        /* Init Colormap */
        Settings.initColorMap(gameView);


        /**Init Arrays and Values**/
        ball = new Ball(90f,30f,2.5f,100,0.05f);
        players = new Player[4];
        physicEntities = new PhysicEntity[300];
        table = new Table(Settings.WIDTH, Settings.HEIGHT, Settings.HOLE_WIDTH);
        flipperFinger = new FlipperFingerControl((((float)Settings.WIDTH/2) - (Settings.HOLE_WIDTH/2)),
                15, (((float)Settings.WIDTH/2) + (Settings.HOLE_WIDTH/2)), 15,
                11f, 45, 135);
        launchControl = new LaunchControl(20,30,30, 50);
        control = new Control(flipperFinger, launchControl);


        //TODO Remove before final release
        //DEBUG STUFF REMOVE BEFORE RELEASE!
        //physicEntities[0] = new LineWall(30, 30,50,0);
        //physicEntities[0] = new Bumper(38, 30,4f);
        physicEntities = Levels.LEVEL1;

    }

    public void simulateTick(){

        ball.calculateFuture(Settings.GRAVITATION);

        control.updateControls(gameView);
        flipperFinger.updateFlipperFinger();
        launchControl.updateLaunchControl();

        Ball newBall = updateAll();

        if(newBall != null){
            ball.updateBall(newBall);
        }else{
            ball.updateBall();
        }

    }

    public Ball updateAll(){

        ArrayList<Ball> collisionBalls = new ArrayList<>();

        for (PhysicEntity physicEntity : physicEntities) {
            if(physicEntity != null){
                Ball physicEntityBall = physicEntity.updateEntity(ball);
                if(physicEntityBall != null){
                    collisionBalls.add(physicEntityBall);
                }
            }
        }

        Ball tableBall = table.updateEntity(ball);
        if(tableBall != null){
            collisionBalls.add(tableBall);
        }

        Ball flipperBall = flipperFinger.updateEntity(ball);
        if(flipperBall != null){
            collisionBalls.add(flipperBall);
        }

        Ball launchBall = launchControl.updateEntity(ball);
        if(launchBall != null){
            collisionBalls.add(launchBall);
        }

        if(collisionBalls.isEmpty()){
            return null;
        }

        return  ball.joinBalls(collisionBalls);

    }

    public void printAll(){

        gameView.clearCanvas();

        table.addToCanvas(gameView);

        for (PhysicEntity physicEntity : physicEntities) {
            if(physicEntity != null) {
                physicEntity.addToCanvas(gameView);
            }
        }

        launchControl.addToCanvas(gameView);

        flipperFinger.addToCanvas(gameView);

        ball.addToCanvas(gameView);

        gameView.printCanvas();
    }

}
