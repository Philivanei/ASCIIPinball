package asciipinball.logic;

import asciipinball.GameView;

import java.awt.event.KeyEvent;

public class FlipperFinger {

    private boolean isFlipperFingeractive;

    public FlipperFinger(GameView gameView) {
        flipperFinger(gameView);
        isFlipperFingeractive = true;
    }

    public void flipperFinger(GameView gameView) {
        //drawing(gameView) for moving the fingers
        while (isFlipperFingeractive) {
            KeyEvent[] keyEvent;
            try {
                keyEvent = gameView.getKeyEvents();
                if (keyEvent.length != 0) {
                    //moving the left flipperfinger with 'a' or 'left'
                    //TODO don't forget to set is flipperfingeractive to false
                    if (((keyEvent[0].getKeyCode() == KeyEvent.VK_LEFT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_A))
                            && (keyEvent[0].getID() == KeyEvent.KEY_PRESSED)) {

                    }
                    //moving the right flipperfinger with 'd' or 'right
                    //TODO don't forget to set is flipperfingeractive to false
                    if (((keyEvent[0].getKeyCode() == KeyEvent.VK_RIGHT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_D))
                            && (keyEvent[0].getID() == KeyEvent.KEY_PRESSED)) {

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
