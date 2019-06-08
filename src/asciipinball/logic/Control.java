package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.interfaces.Drawable;
import asciipinball.logic.Ball;
import asciipinball.logic.flipperfinger.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Control {

    FlipperFingerControl flipperFingerControl;

    public Control(FlipperFingerControl flipperFingerControl) {
        this.flipperFingerControl = flipperFingerControl;
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

                    }
                } else if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {

                    if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {

                        flipperFingerControl.onLeftUp();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {

                        flipperFingerControl.onRightUp();

                    }
                }
            }
        }
    }
}
