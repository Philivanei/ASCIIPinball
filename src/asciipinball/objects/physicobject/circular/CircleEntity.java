package asciipinball.objects.physicobject.circular;

import asciipinball.CollisionData;
import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.interfaces.Drawable;
import asciipinball.objects.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Circle;

import java.util.ArrayList;

/**
 * Eine Abstrakte Oberklasse für Entities die aus Kreisen bestehen
 */
public abstract class CircleEntity extends PhysicEntity implements Drawable {

    protected Circle[] circles;

    public CircleEntity(PlayerManager playerManager) {
        super(playerManager);
    }

    /**
     * Gibt alle Kreise zurück aus der die CircularEntity besteht
     * @return alle Linien
     */
    public Circle[] getCircles() {
        return circles;
    }

    /**
     * Stellt fest ob der Ball kollidiert und Speichert ggf. den Punkt der Collision sowie den konkreten Kreis mit der Kollidiert wurde.
     * @param ball Ball für den die Kollisionsabfrage durchgefüht wird.
     * @return Boolscher ausdruck ob eine Collision gefunden wurde.
     */
    @Override
    protected boolean isCollided(Ball ball) {

        boolean collisionDetected = false;

        for (Circle circle : circles) {
            float distanceToBall = (float) (Math.sqrt(Math.pow((circle.getX() - ball.getPositionX()), 2) +
                    Math.pow((circle.getY() - ball.getPositionY()), 2)));

            if (distanceToBall <= (circle.getRadius() + ball.getRadius())) {

                collisionDetected = true;
                float angleToCollisionPoint = (float) (Math.atan((circle.getY() - ball.getPositionY()) / (circle.getX() - ball.getPositionX())));

                float collisionPositionX = (float) (Math.cos(angleToCollisionPoint) * circle.getRadius()) + circle.getX();
                float collisionPositionY = (float) (Math.sin(angleToCollisionPoint) * circle.getRadius()) + circle.getY();

                collisionList.add(new CollisionData(collisionPositionX, collisionPositionY, circle, distanceToBall));
            }
        }
        cleanupCollisionList();

        return collisionDetected;
    }

    /**
     * Gibt ball nach Collision zurück
     * @param ball Ball der kollidiert
     * @return Ball nach kollision
     */
    @Override
    protected Ball interactWithBall(Ball ball) {
        ArrayList<Ball> ballList = new ArrayList<>();

        while(!collisionList.isEmpty()) {

            CollisionData collisionData = collisionList.get(0);
            Circle collisionCircle = (Circle) collisionData.getCollisionShape();
            collisionList.remove(0);


            float gradientMiddleToCollisionPoint = (collisionData.getCollisionY() - collisionCircle.getY())/(collisionData.getCollisionX() - collisionCircle.getX());
            float tangentGradient;
            if(Float.isFinite(gradientMiddleToCollisionPoint)){
                tangentGradient = -1/gradientMiddleToCollisionPoint;
            }else{
                tangentGradient = 0;
            }


            float newAngle = calculateBallAngleFromGradient(ball,tangentGradient);

            ballList.add(new Ball(ball.getPositionX(),ball.getPositionY(), ball.getRadius(), newAngle ,ball.getVelocity()));

        }


        return ball.joinBalls(ballList);
    }
}
