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

    public Control(FlipperFingerControl flipperFingerControl, LaunchControl launchControl, StartScreen startScreen) {

        this.flipperFingerControl = flipperFingerControl;
        this.launchControl = launchControl;
        this.startScreen = startScreen;
    }

    public void updateControls(GameView gameView) {

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
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_S) {

                        startScreen.arrowDownPressed();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_W) {

                        startScreen.arrowUpPressed();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {

                        startScreen.enterDownPressed();

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
                    }
                }
            }
        }
    }
}
