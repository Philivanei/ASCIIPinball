package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.shapes.Line;

import java.awt.event.KeyEvent;

public class LeftFlipperFinger extends FlipperFinger {

    public LeftFlipperFinger(float x, float y, float startAngle, float stopAngle, float length) {
        super(x, y, startAngle, stopAngle, length);
    }

    public Line calculateLine(float x, float y, float startAngle, float length) {
        adjacentSide = ((float) Math.cos(Math.toRadians(startAngle))) * length;
        oppositeSide = ((float) Math.sin(Math.toRadians(startAngle))) * length;
        this.x = oppositeSide + x;
        this.y = adjacentSide - y;
        return new Line(x, y, this.x, this.y);
    }

    @Override
    public void updateFlipperfinger(Ball ball, GameView gameView) {
        KeyEvent[] keyEvent;
        keyEvent = gameView.getKeyEvents();
        try {
            if (keyEvent.length != 0) {
                //moving the left flipperfinger with 'a' or 'left'
                if (((keyEvent[0].getKeyCode() == KeyEvent.VK_LEFT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_A))
                        && (keyEvent[0].getID() == KeyEvent.KEY_PRESSED)) {
                    flipperFingerDirection = true;
                    startTime = System.currentTimeMillis();
                    abortTimeStop = System.currentTimeMillis() - stopTime;

                } else if (((keyEvent[0].getKeyCode() == KeyEvent.VK_LEFT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_A))
                        && (keyEvent[0].getID() == KeyEvent.KEY_RELEASED)) {
                    flipperFingerDirection = false;
                    stopTime = System.currentTimeMillis();
                    abortTimeStart = System.currentTimeMillis() - startTime;
                }
                float angle;
                if (flipperFingerDirection) {
                    angle = calculateAngleUp((TIMEFORSTOPANGLE - abortTimeStop + System.currentTimeMillis() - startTime), startAngle);
                } else {
                    angle = calculateAngleUp((TIMEFORSTOPANGLE - abortTimeStart + (System.currentTimeMillis() - stopTime)), startAngle);
                }
                lines[0] = calculateLine(x, y, angle, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
