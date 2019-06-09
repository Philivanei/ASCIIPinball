package asciipinball.corelogic;

import asciipinball.GameView;
import asciipinball.corelogic.launchcontrol.LaunchControl;
import asciipinball.objects.flipperfinger.*;

import java.awt.event.KeyEvent;

public class Control {

    private LaunchControl launchControl;
    private FlipperFingerControl flipperFingerControl;

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

                    }
                }
            }
        }
    }
}
