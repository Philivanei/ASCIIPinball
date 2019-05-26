package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.shapes.Line;

import java.awt.event.KeyEvent;

public class RightFlipperFinger extends FlipperFinger {

    public RightFlipperFinger(float x, float y, float startAngle, float stopAngle, float length) {
        super(x, y, startAngle, stopAngle, length);
    }

    @Override
    protected Line calculateLine(float startAngle, float length) {
        adjacentSide = ((float) Math.cos(Math.toRadians(startAngle))) * length;
        oppositeSide = ((float) Math.sin(Math.toRadians(startAngle))) * length;
        float xRes = x - oppositeSide;
        float yRes = y - adjacentSide;
        return new Line(x, y, xRes, yRes);
    }

    @Override
    protected void updateFlipperfinger(Ball ball, GameView gameView) {
        KeyEvent[] keyEvent;
        keyEvent = gameView.getKeyEvents();
        try {
            if (keyEvent.length != 0) {
                //moving the right flipperfinger with 'd' or 'right
                if (((keyEvent[0].getKeyCode() == KeyEvent.VK_RIGHT)/* || (keyEvent[0].getKeyCode() == KeyEvent.VK_D)*/)
                        && (keyEvent[0].getID() == KeyEvent.KEY_PRESSED)) {
                    flipperFingerDirection = true;
                    startTime = System.currentTimeMillis();
                    abortTimeStop = System.currentTimeMillis() - stopTime;

                } else if (((keyEvent[0].getKeyCode() == KeyEvent.VK_RIGHT)/* || (keyEvent[0].getKeyCode() == KeyEvent.VK_D)*/)
                        && (keyEvent[0].getID() == KeyEvent.KEY_RELEASED)) {
                    flipperFingerDirection = false;
                    stopTime = System.currentTimeMillis();
                    abortTimeStart = System.currentTimeMillis() - startTime;
                }

                float angle;
                if (flipperFingerDirection) {
                    angle = calculateAngleUp((TIMEFORSTOPANGLE - abortTimeStop + System.currentTimeMillis() - startTime), startAngle);
                } else {
                    angle = calculateAngleDown((TIMEFORSTOPANGLE - abortTimeStart + (System.currentTimeMillis() - stopTime)), startAngle);
                }
                lines[0] = calculateLine(angle, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
