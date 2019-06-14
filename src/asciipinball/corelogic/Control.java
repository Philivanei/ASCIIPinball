package asciipinball.corelogic;

import asciipinball.GameView;
import asciipinball.objects.launchcontrol.LaunchControl;
import asciipinball.graphics.GameOverScreen;
import asciipinball.graphics.StartScreen;
import asciipinball.objects.flipperfinger.*;

import java.awt.event.KeyEvent;

public class Control {

    private LaunchControl launchControl;
    private FlipperFingerManager flipperFingerManager;
    private StartScreen startScreen;
    private GameOverScreen gameOverScreen;
    private GameView gameView;
    private PinballGame pinballGame;
    private boolean skipRequested;

    public Control(GameView gameView, FlipperFingerManager flipperFingerManager, LaunchControl launchControl,
                   StartScreen startScreen, GameOverScreen gameOverScreen, PinballGame pinballGame) {

        this.flipperFingerManager = flipperFingerManager;
        this.launchControl = launchControl;
        this.startScreen = startScreen;
        this.gameView = gameView;
        this.pinballGame = pinballGame;
        this.gameOverScreen = gameOverScreen;
        skipRequested = false;
    }

    public void updateControls() {

        KeyEvent[] keyEvents = gameView.getKeyEvents();

        if (keyEvents.length != 0) {

            for (KeyEvent keyEvent : keyEvents) {

                if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {

                    //keys for FlipperFingers and GameOverScreen
                    if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {

                        flipperFingerManager.onLeftDown();
                        gameOverScreen.arrowLeftPressed();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {

                        flipperFingerManager.onRightDown();
                        gameOverScreen.arrowRightPressed();


                        //key for launchControl
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {

                        launchControl.onSpaceDown();

                        //keys for StartScreen and GameOverScreen
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {

                        startScreen.arrowDownPressed();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {

                        startScreen.arrowUpPressed();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {

                        startScreen.enterDownPressed();
                        gameOverScreen.enterDownPressed();

                    } else if(keyEvent.getKeyCode() == KeyEvent.VK_R){
                        if(!skipRequested){
                            skipRequested = true;
                            pinballGame.skipRound();
                        }
                    }


                } else if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {

                    //keys for FlipperFingers
                    if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {

                        flipperFingerManager.onLeftUp();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {

                        flipperFingerManager.onRightUp();

                        //key for launchControl
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {

                        launchControl.onSpaceUp();
                    } else if(keyEvent.getKeyCode() == KeyEvent.VK_R){
                        skipRequested = false;
                    }
                }
            }
        }
    }
}
