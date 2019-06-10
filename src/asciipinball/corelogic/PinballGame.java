package asciipinball.corelogic;

import asciipinball.GameView;
import asciipinball.corelogic.launchcontrol.LaunchControl;
import asciipinball.corelogic.players.PlayerManager;
import asciipinball.Settings;
import asciipinball.fonts.FontStraight;
import asciipinball.graphics.GameOverScreen;
import asciipinball.graphics.Gui;
import asciipinball.graphics.LifeDisplay;
import asciipinball.graphics.StartScreen;
import asciipinball.levels.Levels;
import asciipinball.objects.flipperfinger.FlipperFingerControl;
import asciipinball.objects.Ball;
import asciipinball.objects.nophysicsobject.NonPhysicEntity;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.objects.physicobject.polygonial.Table;

import java.util.ArrayList;

public class PinballGame {

    private GameView gameView;

    /*IMPORTANT: Max Koordinate is WIDTH/HEIGHT -1*/

    private Ball ball;
    private Table table;
    private PhysicEntity[] physicEntities;
    private NonPhysicEntity[] noPhysicEntities;
    private FlipperFingerControl flipperFinger;
    private StartScreen startScreen;
    private GameOverScreen gameOverScreen;
    private Control control;
    private LaunchControl launchControl;
    private Gui gui;
    private boolean gameOver;
    private PlayerManager playerManager;
    private LifeDisplay lifeDisplay;

    public PinballGame() {

        /* Init GameView */
        gameView = new GameView(Settings.HEIGHT, Settings.GAMEVIEW_WIDTH, "ASCII Pinball");
        gameView.setWindowsSize(GameView.WINDOWSIZE_LARGE);
        gameView.show();


        /* Init Colormap */
        Settings.initColorMap(gameView);

        init();
    }

    public void simulateTick() {

        control.updateControls();

        gameOverScreen.setActive(gameOver);

        if (startScreen.isPlayerNumberSelected()) {

            if (!playerManager.isInitialized()) {
                try {
                    playerManager.init(startScreen.getPlayerNumber());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        if (!gameOver && playerManager.isInitialized()) {

            ball.calculateFuture(Settings.GRAVITATION);
            launchControl.updateLaunchControl();

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

    public void init(){
        /*Init Arrays and Values*/
        gameOver = false;
        playerManager = new PlayerManager();
        startScreen = new StartScreen();
        gameOverScreen = new GameOverScreen(playerManager, this);
        launchControl = new LaunchControl(playerManager, 20, 30, 50, Settings.BALL_RADIUS);
        lifeDisplay = new LifeDisplay(playerManager, Settings.WIDTH + 10, 10 , 2.5f);
        flipperFinger = new FlipperFingerControl(playerManager, (((float) Settings.WIDTH / 2) - (Settings.HOLE_WIDTH / 2)),
                15, (((float) Settings.WIDTH / 2) + (Settings.HOLE_WIDTH / 2)), 15,
                11f, 45, 135);
        control = new Control(gameView, flipperFinger, launchControl, startScreen,gameOverScreen, this);
        gui = new Gui(gameView);

        resetBall();
        table = new Table(playerManager, Settings.WIDTH, Settings.HEIGHT, Settings.HOLE_WIDTH);

        physicEntities = new Levels(playerManager).getLevel1PhysicEntities();
        noPhysicEntities = new Levels(playerManager).getLevel1NoPhysicEntities();
    }

    private void checkBallOutOfGame() {
        if (ball.getPositionY() < -10) {
            playerManager.currentPlayerLoseRound();
            playerManager.nextPlayer();
            resetBall();
        }

        gameOver = !playerManager.isBallLeft();
    }

    public void skipRound(){
        if(playerManager.isInitialized()){
            ball = new Ball(10, -10, Settings.BALL_RADIUS, -90, 0.05f);
        }
    }

    private void resetBall() {
        ball = new Ball(90f, 30f, Settings.BALL_RADIUS, 100, 0.05f);
        launchControl.reset();
    }

    private Ball updateAll() {

        for(NonPhysicEntity entity : noPhysicEntities){
            if(entity != null){
                entity.updateEntity(ball);
            }
        }


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

        if (!startScreen.isPlayerNumberSelected()) {
            startScreen.printStartScreen(gui);
        } else if (!gameOver) {
            try {
                gui.addToCanvas(table);

                for (PhysicEntity physicEntity : physicEntities) {
                    if (physicEntity != null) {
                        gui.addToCanvas(physicEntity);
                    }
                }

                for (NonPhysicEntity noPhysicEntity : noPhysicEntities) {
                    if (noPhysicEntity != null) {
                        gui.addToCanvas(noPhysicEntity);
                    }
                }

                gui.addToCanvas(lifeDisplay.getDisplay(), lifeDisplay.getColor());

                gui.addToCanvas(launchControl);

                gui.addToCanvas(flipperFinger);

                gui.addToCanvas(ball);

                gui.addAsciiStringToCanvas(playerManager.getCurrentPlayerScoreString(), (int) Settings.HEIGHT / 2, 35, new FontStraight());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            gameOverScreen.printGameOverScreen(gui);
        }
        gameView.printCanvas();
    }

}
