package asciipinball.objects.physicobject.circular;

import asciipinball.CollisionData;
import asciipinball.objects.ball.BallHelper;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.interfaces.Drawable;
import asciipinball.objects.ball.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Circle;

import java.util.ArrayList;

/**
 * Eine abstrakte Oberklasse für Entities, die aus Kreisen bestehen
 */
public abstract class CircleEntity extends PhysicEntity implements Drawable {

    protected Circle[] circles;

    protected CircleEntity(PlayerManager playerManager) {
        super(playerManager);
    }

    /**
     * Gibt alle Kreise zurück, aus der die CircularEntity besteht
     *
     * @return alle Linien
     */
    public Circle[] getCircles() {
        return circles;
    }

    @Override
    protected boolean isCollided(Ball ball) {

        boolean collisionDetected = false;

        for (Circle circle : circles) {
            float distanceToBall = (float) (Math.sqrt(Math.pow((circle.getX() - ball.getX()), 2) +
                    Math.pow((circle.getY() - ball.getY()), 2)));

            if (distanceToBall <= (circle.getRadius() + ball.getRadius())) {

                collisionDetected = true;
                float angleToCollisionPoint = (float) (Math.atan((circle.getY() - ball.getY()) / (circle.getX() - ball.getX())));

                float collisionPositionX = (float) (Math.cos(angleToCollisionPoint) * circle.getRadius()) + circle.getX();
                float collisionPositionY = (float) (Math.sin(angleToCollisionPoint) * circle.getRadius()) + circle.getY();

                collisionList.add(new CollisionData<>(collisionPositionX, collisionPositionY, circle, distanceToBall));
            }
        }
        cleanupCollisionList();

        return collisionDetected;
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        ArrayList<Ball> ballList = new ArrayList<>();

        while (!collisionList.isEmpty()) {

            CollisionData collisionData = collisionList.get(0);
            //collisionData is only filled by overwritten isColided Method so i can guarantee it is Circle
            Circle collisionCircle = (Circle) collisionData.getCollisionShape();
            collisionList.remove(0);


            float gradientMiddleToCollisionPoint = (collisionData.getCollisionY() - collisionCircle.getY()) / (collisionData.getCollisionX() - collisionCircle.getX());
            float tangentGradient;
            if (Float.isFinite(gradientMiddleToCollisionPoint)) {
                tangentGradient = -1 / gradientMiddleToCollisionPoint;
            } else {
                tangentGradient = 0;
            }


            float newAngle = calculateBallAngleFromGradient(ball, tangentGradient);

            ballList.add(new Ball(ball.getX(), ball.getY(), ball.getRadius(), newAngle, ball.getVelocity()));

        }


        return new BallHelper().joinBalls(ballList);
    }
}
