package asciipinball.objects.flipperfinger;

import asciipinball.playersandscore.PlayerManager;
import asciipinball.interfaces.Drawable;
import asciipinball.objects.Ball;
import asciipinball.sounds.Aui;

import java.util.ArrayList;

/**
 * Steuerung der Flipper Finger.
 */
public class FlipperFingerControl implements Drawable {

    private LeftFlipperFinger leftFlipperFinger;
    private RightFlipperFinger rightFlipperFinger;
    private Direction leftDirection;
    private Direction rightDirection;
    private long upStartTimeLeft;
    private long downStartTimeLeft;
    private long upStartTimeRight;
    private long downStartTimeRight;


    /**
     * Erzeugt zwei Flipper Finger.
     *
     * @param playerManager       PlayerManager des Spiels
     * @param xLeftFlipperFinger  x-Koordinate des linken Flipper Fingers
     * @param yLeftFlipperFinger  y-Koordinate des linken Flipper Fingers
     * @param xRightFlipperFinger x-Koordinate des rechten Flipper Fingers
     * @param yRightFlipperFinger y-Koordinate des rechten Flipper Fingers
     * @param length              Länge der Flipper Finger
     * @param minAngle            Legt die MinimalHöhe der FlipperFinger fest.
     * @param maxAngle            Legt die MaximalHöhe der FlipperFinger fest.
     */
    public FlipperFingerControl(PlayerManager playerManager, float xLeftFlipperFinger, float yLeftFlipperFinger, float xRightFlipperFinger,
                                float yRightFlipperFinger, float length, float minAngle, float maxAngle) {
        leftFlipperFinger = new LeftFlipperFinger(playerManager, xLeftFlipperFinger, yLeftFlipperFinger, length, minAngle, maxAngle);
        rightFlipperFinger = new RightFlipperFinger(playerManager, xRightFlipperFinger, yRightFlipperFinger, length, minAngle, maxAngle);
        leftDirection = Direction.DOWN;
        rightDirection = Direction.DOWN;
        upStartTimeRight = System.currentTimeMillis();
        downStartTimeRight = System.currentTimeMillis();
        upStartTimeLeft = System.currentTimeMillis();
        downStartTimeLeft = System.currentTimeMillis();

    }

    /**
     * Zeichnet die aktuellen Flipper Finger während Bewegungen ein.
     */
    public void updateFlipperFinger() {

        if (leftDirection == Direction.UP) {
            long timeSinceDown = System.currentTimeMillis() - downStartTimeLeft;

            leftFlipperFinger.generateLine(leftFlipperFinger.calculateAngleUp(upStartTimeLeft, timeSinceDown));
        } else {
            long timeSinceUp = System.currentTimeMillis() - upStartTimeLeft;
            leftFlipperFinger.generateLine(leftFlipperFinger.calculateAngleDown(downStartTimeLeft, timeSinceUp));
        }

        if (rightDirection == Direction.UP) {
            long timeSinceDown = System.currentTimeMillis() - downStartTimeRight;
            rightFlipperFinger.generateLine(rightFlipperFinger.calculateAngleUp(upStartTimeRight, timeSinceDown));
        } else {
            long timeSinceUp = System.currentTimeMillis() - upStartTimeRight;
            rightFlipperFinger.generateLine(rightFlipperFinger.calculateAngleDown(downStartTimeRight, timeSinceUp));
        }

    }

    /**
     * Bewegt den linken Flipper Finger nach oben.
     */
    public void onLeftDown() {
        if (leftDirection == Direction.DOWN) {
            upStartTimeLeft = System.currentTimeMillis();
        }
        leftDirection = Direction.UP;
    }

    /**
     * Bewegt den linken Flipper Finger nach unten.
     */
    public void onLeftUp() {
        if (leftDirection == Direction.UP) {
            downStartTimeLeft = System.currentTimeMillis();
        }
        leftDirection = Direction.DOWN;
    }

    /**
     * Bewegt den rechten Flipper Finger nach oben
     */
    public void onRightDown() {
        if (rightDirection == Direction.DOWN) {
            upStartTimeRight = System.currentTimeMillis();
        }
        rightDirection = Direction.UP;
    }

    /**
     * Bewegt den rechten Flipper Finger nach unten
     */
    public void onRightUp() {
        if (rightDirection == Direction.UP) {
            downStartTimeRight = System.currentTimeMillis();
        }
        rightDirection = Direction.DOWN;
    }

    /**
     * Ruft updateEntity der Flipper Finger auf.
     *
     * @param ball Ball, auf den die Kollisionsabfrage ausgeführt werden soll.
     * @return Ball nach Kollision
     */
    public Ball updateEntity(Ball ball, Aui aui) {

        ArrayList<Ball> flipperBalls = new ArrayList<>();

        Ball leftBall = leftFlipperFinger.updateEntity(ball, aui);
        Ball rightBall = rightFlipperFinger.updateEntity(ball, aui);

        if (leftBall != null) {
            flipperBalls.add(leftBall);
        }
        if (rightBall != null) {
            flipperBalls.add(rightBall);
        }

        if (flipperBalls.isEmpty()) {
            return null;
        }
        return ball.joinBalls(flipperBalls);
    }

    public LeftFlipperFinger getLeftFlipperFinger() {
        return leftFlipperFinger;
    }

    public RightFlipperFinger getRightFlipperFinger() {
        return rightFlipperFinger;
    }

    @Override
    public char getColor() {
        return 'B';
    }
}
