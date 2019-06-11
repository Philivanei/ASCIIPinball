package asciipinball.objects.flipperfinger;

import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.objects.Ball;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Line;

/**
 * Ist zuständig für die Mechanik der Flipper Finger. Außerdem werden diese erstellt und animiert.
 */
public abstract class FlipperFinger extends PolygonEntity {

    protected float x;
    protected float y;
    private float minAngle;
    private float maxAngle;
    protected float length;
    private static final float TIME_FOR_FLIP = 250;
    private MoveStatus moveStatus = MoveStatus.STOP;

    /**
     * Erstellt die Flipper Finger.
     * @param playerManager PlayerManager des Spiels
     * @param x Knotenpunkt, an dem die Flipper Finger aufgebaut werden.
     * @param y Knotenpunkt, an dem die Flipper Finger aufgebaut werden.
     * @param length Setzt die Länge der FlipperFinger.
     * @param minAngle Legt die MinimalHöhe der FlipperFinger fest.
     * @param maxAngle Legt die Maximalhöhe der FlipperFinger fest.
     */
    public FlipperFinger(PlayerManager playerManager, float x, float y, float length, float minAngle, float maxAngle) {
        super(playerManager);
        lines = new Line[1];
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
        this.length = length;
        this.x = x;
        this.y = y;
        generateLine(minAngle);
    }

    /**
     * Erstellt die sichtbare Linie der Flipperfinger.
     * @param angle aktueller Winkel des Flipperfingers.
     */
    protected abstract void generateLine(float angle);

    /**
     * Berechnet den Winkel eines sich aufwärtsbewegenden Flipper Fingers abhängig der Zeit.
     * @param startTime Zeit seit Aktivierung des Flipper Fingers.
     * @param timeSinceDown Zeit seit Deaktivierung des Flipper Fingers.
     * @return Der nach der Zeit resultierende Winkel
     */
    public float calculateAngleUp(long startTime, long timeSinceDown) {

        float startShift = (TIME_FOR_FLIP - timeSinceDown);
        if (startShift < 0) {
            startShift = 0;
        }

        float time = startShift + (System.currentTimeMillis() - startTime);

        float result = ((maxAngle - minAngle) / TIME_FOR_FLIP) * time + minAngle;
        if (result > maxAngle) {
            moveStatus = MoveStatus.STOP;
            result = maxAngle;
        } else {
            moveStatus = MoveStatus.UP;
        }
        return result;
    }

    /**
     * Berechnet den Winkel eines sich abwärtsbewegenden Flipper Fingers abhängig der Zeit.
     * @param startTime Zeit seit Deaktivierung des Flipper Fingers.
     * @param timeSinceUp Zeit seit Aktivierung des Flipper Fingers.
     * @return Der nach der Zeit resultierende Winkel
     */
    public float calculateAngleDown(long startTime, long timeSinceUp) {

        float startShift = (TIME_FOR_FLIP - timeSinceUp);
        if (startShift < 0) {
            startShift = 0;
        }

        float time = startShift + (System.currentTimeMillis() - startTime);

        float result = (minAngle - maxAngle) / TIME_FOR_FLIP * time + maxAngle;
        if (result < minAngle) {
            moveStatus = MoveStatus.STOP;
            result = minAngle;
        } else {
            moveStatus = MoveStatus.DOWN;
        }
        return result;
    }

    @Override
    public Ball updateEntity(Ball ball) {
        Ball unmodifiedBall = super.updateEntity(ball);
        if (unmodifiedBall != null) {
            if (moveStatus == MoveStatus.UP) {
                unmodifiedBall.addVelocity(0.03f);
            } else if (moveStatus == MoveStatus.DOWN) {
                unmodifiedBall.addVelocity(-0.006f);
            }
            unmodifiedBall.jumpToFuture(75);

        }
        return unmodifiedBall;
    }
}
