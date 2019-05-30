package asciipinball.objects.physicobject.circular;

import asciipinball.CollisionData;
import asciipinball.logic.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Circle;

import java.util.ArrayList;

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
        //TODO Add collision physics to circle Entities
        ArrayList<Ball> ballList = new ArrayList<Ball>();

        while(!collisionList.isEmpty()) {

            CollisionData collisionData = collisionList.get(0);
            Circle collisionCircle = (Circle) collisionData.getCollisionShape();
            collisionList.remove(0);

            float gradientMiddleToCollisionPoint = (collisionData.getCollisionY() - collisionCircle.getY())/(collisionData.getCollisionX() - collisionCircle.getX());
            float tangentGradient = -1/gradientMiddleToCollisionPoint;

            ballList.add(new Ball(ball.getPositionX(),ball.getPositionY(), ball.getRadius(), calculateBallAngleFromGradient(ball,tangentGradient),ball.getVelocity()));

        }


        return null;
    }
}
