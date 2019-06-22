package asciipinball.objects.flipperfinger;

import asciipinball.playersandscore.PlayerManager;
import asciipinball.objects.Ball;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Line;
import asciipinball.sounds.Aui;

/**
 * Ist zuständig für die Mechanik der Flipperfinger. Außerdem werden die Flipperfinger erstellt und animiert
 */
public abstract class FlipperFinger extends PolygonEntity {

    protected float x;
    protected float y;
    private float minAngle;
    private float maxAngle;
    protected float length;
    private static final float TIME_FOR_FLIP = 250;
    private MoveStatus moveStatus = MoveStatus.STOP;

    protected FlipperFinger(PlayerManager playerManager, float x, float y, float length, float minAngle, float maxAngle) {
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
     * Erstellt die sichtbare Linie der Flipperfinger
     *
     * @param angle aktueller Winkel des Flipperfingers
     */
    protected abstract void generateLine(float angle);

    /**
     * Berechnet den Winkel eines sich aufwärtsbewegenden Flipperfingers abhängig von der Zeit
     *
     * @param startTime     Zeit seit Aktivierung des Flipperfingers.
     * @param timeSinceDown Zeit seit Deaktivierung des Flipperfingers.
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
     * Berechnet den Winkel eines sich abwärtsbewegenden Flipperfingers abhängig von der Zeit
     *
     * @param startTime   Zeit seit Deaktivierung des Flipperfingers.
     * @param timeSinceUp Zeit seit Aktivierung des Flipperfingers.
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
    public Ball updateEntity(Ball ball, Aui aui) {
        Ball unmodifiedBall = super.updateEntity(ball, aui);
        if (unmodifiedBall != null) {
            if (moveStatus == MoveStatus.UP) {
                unmodifiedBall.addVelocity(0.03f);
            } else if (moveStatus == MoveStatus.DOWN) {
                unmodifiedBall.addVelocity(0.005f);
            }
            unmodifiedBall.jumpToFuture(75);

        }
        return unmodifiedBall;
    }
}
