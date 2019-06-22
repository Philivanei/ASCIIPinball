package asciipinball.objects;

import java.util.ArrayList;

/**
 * Hilfsmethoden für operationen mit Balls
 */
public class BallHelper {

    /**
     * Erstellt einen BallHelper
     */
    public BallHelper(){

    }

    /**
     * Führt eine Liste aus Bällen zu einem Ball zusammen
     *
     * @param balls Liste an Bällen
     * @return zusammengeführter Ball
     */
    public Ball joinBalls(ArrayList<Ball> balls) {
        // calculate average if a ball hits a top or a corner

        int arrayLength = balls.size();

        if (balls.isEmpty()) {
            return null;
        } else if (arrayLength == 1) {
            return balls.get(0);
        } else if (arrayLength == 2) {
            Ball ball1 = balls.get(0);
            Ball ball2 = balls.get(1);
            float newDirection = convertDirection((((ball1.getDirection() + 360) % 360) + ((ball2.getDirection() + 360) % 360)) / 2);
            return new Ball(ball1.getX(), ball1.getY(), ball1.getRadius(), newDirection, ball1.getVelocity());

        } else {
            Ball ball1 = balls.get(0);
            Ball ball2 = balls.get(1);
            Ball ball3 = balls.get(2);
            float newDirection = convertDirection((((ball1.getDirection() + 360) % 360)
                    + ((ball2.getDirection() + 360) % 360) + ((ball3.getDirection() + 360) % 360)) / 3);
            return new Ball(ball1.getX(), ball1.getY(), ball1.getRadius(), newDirection, ball1.getVelocity());
        }
    }

    /**
     * Rechnet eine übergebene Richtung um auf einen Bereich von -180° - 180°
     *
     * @param direction zu umrechnende Richtung
     * @return umgerechnete Richtung
     */
    public float convertDirection(float direction) {
        float result;

        if (direction > -180 && direction <= 180) {
            result = direction;
        } else {

            float clippedDirection = direction % 360;

            if (clippedDirection > 180) {
                result = clippedDirection - 360;
            } else {
                result = clippedDirection;
            }
        }
        return result;
    }

}
