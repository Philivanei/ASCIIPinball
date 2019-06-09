package asciipinball.corelogic;

import asciipinball.GameView;
import asciipinball.corelogic.launchcontrol.LaunchControl;
import asciipinball.corelogic.players.Player;
import asciipinball.corelogic.players.PlayerManager;
import asciipinball.Settings;
import asciipinball.graphics.GameOverScreen;
import asciipinball.graphics.Gui;
import asciipinball.graphics.StartScreen;
import asciipinball.levels.Levels;
import asciipinball.objects.flipperfinger.FlipperFingerControl;
import asciipinball.objects.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.objects.physicobject.polygonial.Table;

import java.awt.*;
import java.util.ArrayList;

public class PinballGame {

    private GameView gameView;

    /*IMPORTANT: Max Koordinate is WIDTH/HEIGHT -1*/


    private Ball ball;
    private Player[] players;
    private Table table;
    private PhysicEntity[] physicEntities;
    private FlipperFingerControl flipperFinger;
    private StartScreen startScreen;
    private Control control;
    private LaunchControl launchControl;
    private Gui gui;
    private boolean gameOver = false;
    private Player currentPlayer;
    private PlayerManager playerManager;

    public PinballGame() {

        /* Init GameView */
        gameView = new GameView(Settings.HEIGHT, 240, "Pinball");
        gameView.setWindowsSize(GameView.WINDOWSIZE_LARGE);
        gameView.show();


        /* Init Colormap */
        Settings.initColorMap(gameView);


        /*Init Arrays and Values*/
        try {
            playerManager = new PlayerManager((byte) 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchControl = new LaunchControl(playerManager, 20, 30, 50, Settings.BALL_RADIUS);
        resetBall();
        players = new Player[4];
        physicEntities = new PhysicEntity[300];
        table = new Table(playerManager, Settings.WIDTH, Settings.HEIGHT, Settings.HOLE_WIDTH);
        flipperFinger = new FlipperFingerControl(playerManager, (((float) Settings.WIDTH / 2) - (Settings.HOLE_WIDTH / 2)),
                15, (((float) Settings.WIDTH / 2) + (Settings.HOLE_WIDTH / 2)), 15,
                11f, 45, 135);
        control = new Control(flipperFinger, launchControl, startScreen);
        gui = new Gui(gameView);
        //TODO Playernumber selection

        //TODO Remove before final release
        //DEBUG STUFF REMOVE BEFORE RELEASE!
        //physicEntities[0] = new LineWall(30, 30,50,0);
        //physicEntities[0] = new Bumper(38, 30,4f);
        physicEntities = new Levels(playerManager).getLevel1();
        currentPlayer = new Player();

    }

    public void simulateTick() {

        if (!gameOver) {

            ball.calculateFuture(Settings.GRAVITATION);

            launchControl.updateLaunchControl();
            control.updateControls(gameView);
            flipperFinger.updateFlipperFinger();

            Ball newBall = updateAll();

            if (newBall != null) {
                ball.updateBall(newBall);
            } else {
                ball.updateBall();
            }
            checkBallOutOfGame();
        }
    }

    private void checkBallOutOfGame() {
        if (ball.getPositionY() < -10) {
            playerManager.currentPlayerLoseRound();
            playerManager.nextPlayer();
            resetBall();
        }

        gameOver = !playerManager.isBallLeft();
    }

    private void resetBall() {
        ball = new Ball(90f, 30f, Settings.BALL_RADIUS, 100, 0.05f);
        launchControl.reset();
    }

    private Ball updateAll() {

        ArrayList<Ball> collisionBalls = new ArrayList<>();

        for (PhysicEntity physicEntity : physicEntities) {
            if (physicEntity != null) {
                Ball physicEntityBall = physicEntity.updateEntity(ball);
                if (physicEntityBall != null) {
                    collisionBalls.add(physicEntityBall);
                }
            }
        }

        Ball tableBall = table.updateEntity(ball);
        if (tableBall != null) {
            collisionBalls.add(tableBall);
        }

        Ball flipperBall = flipperFinger.updateEntity(ball);
        if (flipperBall != null) {
            collisionBalls.add(flipperBall);
        }

        Ball launchBall = launchControl.updateEntity(ball);
        if (launchBall != null) {
            collisionBalls.add(launchBall);
        }

        if (collisionBalls.isEmpty()) {
            return null;
        }

        return ball.joinBalls(collisionBalls);

    }

    public void printAll() {

        gameView.clearCanvas();
        if (!gameOver) {
            try {
                gui.addToCanvas(table);

                for (PhysicEntity physicEntity : physicEntities) {
                    if (physicEntity != null) {
                        gui.addToCanvas(physicEntity);
                    }
                }

                gui.addToCanvas(launchControl);

                gui.addToCanvas(flipperFinger);

                gui.addToCanvas(ball);

                gui.addStringToCanvas(playerManager.getCurrentPlayerScoreString(), (int) Settings.HEIGHT/2, 10);;

            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            gui.addToCanvas(new GameOverScreen());
        }
        gameView.printCanvas();
    }

}
