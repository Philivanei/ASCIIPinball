package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.interfaces.Drawable;
import asciipinball.logic.Ball;
import asciipinball.logic.flipperfinger.*;
import asciipinball.logic.launchcontrol.LaunchControl;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Control {

    FlipperFingerControl flipperFingerControl;
    LaunchControl launchControl;

    public Control(FlipperFingerControl flipperFingerControl, LaunchControl launchControl) {
        this.flipperFingerControl = flipperFingerControl;
        this.launchControl = launchControl;
    }

    public void updateControls(GameView gameView) {

        KeyEvent[] keyEvents = gameView.getKeyEvents();

        if (keyEvents.length != 0) {

            for (KeyEvent keyEvent : keyEvents) {

                if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {

                    if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {

                        flipperFingerControl.onLeftDown();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {

                        flipperFingerControl.onRightDown();

                    } else if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE){
                        launchControl.onSpaceDown();
                    }
                } else if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {

                    if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {

                        flipperFingerControl.onLeftUp();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {

                        flipperFingerControl.onRightUp();

                    } else if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE){
                        launchControl.onSpaceUp();
                    }
                }
            }
        }
    }
}
