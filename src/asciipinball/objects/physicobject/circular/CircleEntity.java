package asciipinball.objects.physicobject.circular;

import asciipinball.CollisionData;
import asciipinball.logic.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Circle;

public abstract class CircleEntity extends PhysicEntity {

    protected Circle[] circles;

    @Override
    protected boolean isCollided(Ball ball) {

        boolean collisionDetected = false;

        for (Circle circle : circles) {
            float distanceToBall = (float) (Math.sqrt(Math.pow((circle.getX() - ball.getPositionX()), 2) +
                    Math.pow((circle.getY() - ball.getPositionY()), 2)));

            if (distanceToBall < (circle.getRadius() + ball.getRadius())) {
                collisionDetected = true;
                float angleToCollisionPoint = (float) (Math.atan((circle.getY() - ball.getPositionY()) / (circle.getX() - ball.getPositionX())));

                float collisionPositionX = (float) (Math.cos(angleToCollisionPoint) * circle.getRadius());
                float collisionPositionY = (float) (Math.sin(angleToCollisionPoint) * circle.getRadius());

                collisionList.add(new CollisionData(collisionPositionX, collisionPositionY, circle, distanceToBall));
            }
        }

        cleanupCollisionList();

        return collisionDetected;
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        //TODO
        return null;
    }
}
