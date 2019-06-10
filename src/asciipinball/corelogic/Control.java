package asciipinball.corelogic;

import asciipinball.GameView;
import asciipinball.corelogic.launchcontrol.LaunchControl;
import asciipinball.graphics.StartScreen;
import asciipinball.objects.flipperfinger.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

public class Control {

    private LaunchControl launchControl;
    private FlipperFingerControl flipperFingerControl;
    private StartScreen startScreen;
    private GameView gameView;
    private PinballGame pinballGame;
    private boolean resetRequested;

    public Control(GameView gameView, FlipperFingerControl flipperFingerControl, LaunchControl launchControl, StartScreen startScreen, PinballGame pinballGame) {

        this.flipperFingerControl = flipperFingerControl;
        this.launchControl = launchControl;
        this.startScreen = startScreen;
        this.gameView = gameView;
        this.pinballGame = pinballGame;
        resetRequested = false;
    }

    public void updateControls() {

        KeyEvent[] keyEvents = gameView.getKeyEvents();

        if (keyEvents.length != 0) {

            for (KeyEvent keyEvent : keyEvents) {

                if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {

                    //keys for FlipperFingers
                    if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {

                        flipperFingerControl.onLeftDown();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {

                        flipperFingerControl.onRightDown();

                        //key for launchControl
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {

                        launchControl.onSpaceDown();

                        //keys for StartScreen
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {

                        startScreen.arrowDownPressed();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {

                        startScreen.arrowUpPressed();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {

                        startScreen.enterDownPressed();

                    } else if(keyEvent.getKeyCode() == KeyEvent.VK_R){
                        if(!resetRequested){
                            resetRequested = true;
                            pinballGame.skipRound();
                        }
                    }


                } else if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {

                    //keys for FlipperFingers
                    if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {

                        flipperFingerControl.onLeftUp();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {

                        flipperFingerControl.onRightUp();

                        //key for launchControl
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {

                        launchControl.onSpaceUp();
                    } else if(keyEvent.getKeyCode() == KeyEvent.VK_R){
                        resetRequested = false;
                    }
                }
            }
        }
    }
}
