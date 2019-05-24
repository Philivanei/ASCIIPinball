package asciipinball.objects.physicobject.polygonial;

import asciipinball.logic.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Line;

import java.awt.geom.Line2D;

public abstract class PolygonEntity extends PhysicEntity {

    protected Line[] lines;

    @Override
    public Ball updateEntity(Ball ball) {
        return super.updateEntity(ball);
    }

    @Override
    protected boolean isCollided(Ball ball) {

        boolean collisionDetected = false;

        float minDistance = Float.MAX_VALUE;

        for(int i = 0; i< lines.length; i++){

            float mLine = lines[i].getM();
            float bLine = lines[i].getB();
            float mVertical = -1/mLine;
            float bVertical = ball.getPositionY() - mVertical * ball.getPositionX();

            float x = (bLine - bVertical) / (bVertical - mLine);
            float y = mVertical * x + bVertical;

            //calc distance Ball to lotfußpunkt
            float distanceToBall = (float) Math.sqrt((x-ball.getPositionX()) * (x-ball.getPositionX()) + (y-ball.getPositionY()) * (y-ball.getPositionY()));

            if(distanceToBall < ball.getRadius()) {

                float distanceToA = (float) (Math.sqrt(Math.pow((x - lines[i].getX1()),2) +
                        Math.pow((y - lines[i].getY1()),2)));
                float distanceToB = (float) (Math.sqrt(Math.pow((x - lines[i].getX2()),2) +
                        Math.pow((y - lines[i].getY2()),2)));

                if(distanceToA > lines[i].getLength() || distanceToB > lines[i].getLength()){

                    if(distanceToA < ball.getRadius()){
                        distanceToBall = distanceToA;
                    }else if(distanceToB < ball.getRadius()){
                        distanceToBall = distanceToB;
                    }else{ //In this case the lot point isn't hitting the line Segment
                        continue;
                    }
                }

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
