package asciipinball.objects.physicobject.polygonial;

import asciipinball.logic.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Line;

import java.awt.geom.Line2D;

public abstract class PolygonEntity extends PhysicEntity {

    protected Line[] lines;


    @Override
    protected boolean isCollided(Ball ball) {

        boolean collisionDetected = false;

        float minDistance = Float.MAX_VALUE;

        for(int i = 0; i< lines.length; i++){

            float mLine = (lines[i].getY2() - lines[i].getY1()) / (lines[i].getX2() - lines[i].getX1());
            float bLine = lines[i].getY1() - mLine * lines[i].getX1();
            float mVertical = -1/mLine;
            float bVertical = ball.getPositionY() - mVertical * ball.getPositionX();

            float x = (bLine - bVertical) / (bVertical - mLine);
            float y = mVertical * x + bVertical;

            //TODO evaluierung ob Lot fußpunkt auf strecke ist oder nicht Wenn nicht - abstand zu Strecken Außenpunkten berechnen

            //calc distance Ball to lotfußpunkt
            float distanceToBall = (float) Math.sqrt((x-ball.getPositionX()) * (x-ball.getPositionX()) + (y-ball.getPositionY()) * (y-ball.getPositionY()));



            if(distanceToBall < ball.getRadius()) {
                collisionDetected = true;
                if(distanceToBall < minDistance){
                    minDistance = distanceToBall;
                    collisionPositionX = x;
                    collisionPositionY = y;
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
