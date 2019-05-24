package asciipinball.objects.physicobject.circular;

import asciipinball.logic.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Circle;

public abstract class CircleEntity extends PhysicEntity {

    protected Circle[] circles;
    protected Circle collisionCircle;

    @Override
    protected boolean isCollided(Ball ball) {
        //TODO
        float closestCollision = Float.MAX_VALUE;

        boolean collisionDetected = false;

        for (Circle circle : circles) {
            float distanceToBall = (float) (Math.sqrt(Math.pow((circle.getX() - ball.getPositionX()), 2) +
                    Math.pow((circle.getY() - ball.getPositionY()), 2)));

            if (distanceToBall < (circle.getRadius() + ball.getRadius())) {
                collisionDetected = true;
                if (distanceToBall < closestCollision) {
                    closestCollision = distanceToBall;
                    float angleToCollisionPoint = (float) (Math.atan((circle.getY() - ball.getPositionY()) / (circle.getX() - ball.getPositionX())));
                    collisionPositionX = (float) (Math.cos(angleToCollisionPoint) * circle.getRadius());
                    collisionPositionY = (float) (Math.sin(angleToCollisionPoint) * circle.getRadius());
                    collisionCircle = circle;
                }
            }
        }
        return collisionDetected;
    }

    @Override
    protected Ball interactWitBall(Ball ball) {
        //TODO
        return null;
    }
}
