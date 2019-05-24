package asciipinball.objects.physicobject.polygonial;

import asciipinball.logic.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Line;

import java.awt.geom.Line2D;

public abstract class PolygonEntity extends PhysicEntity {

    protected Line[] lines;
    private Line collisionLine;


    @Override
    protected boolean isCollided(Ball ball) {

        boolean collisionDetected = false;

        float minDistance = Float.MAX_VALUE;

        for(int i = 0; i< lines.length; i++){
            float distanceToBall;
            float distanceToA;
            float distanceToB;
            float x;
            float y;

            float mLine = lines[i].getM();
            if(Float.isFinite(mLine)){ //If The line isn't 90° straight
                float bLine = lines[i].getB();
                float mVertical = -1/mLine;
                float bVertical = ball.getPositionY() - mVertical * ball.getPositionX();

                x = (bLine - bVertical) / (bVertical - mLine);
                y = mVertical * x + bVertical;

                //calc distance Ball to lotfußpunkt
                distanceToBall = (float) Math.sqrt((x-ball.getPositionX()) * (x-ball.getPositionX()) + (y-ball.getPositionY()) * (y-ball.getPositionY()));
            }else{ // If the line is 90° straight (up)

                x = lines[i].getX1();
                y = ball.getPositionY();

                distanceToBall = Math.abs(ball.getPositionX() - lines[i].getX1());
            }

            distanceToA = (float) (Math.sqrt(Math.pow((x - lines[i].getX1()),2) +
                    Math.pow((y - lines[i].getY1()),2)));
            distanceToB = (float) (Math.sqrt(Math.pow((x - lines[i].getX2()),2) +
                    Math.pow((y - lines[i].getY2()),2)));



            if(distanceToBall < ball.getRadius()) {

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
                    collisionLine = lines[i];
                }
            }
        }

        return collisionDetected;
    }

    @Override
    protected Ball interactWitBall(Ball ball) {
        float angleToLine;
        if(Float.isFinite(collisionLine.getM())){
            angleToLine = (float) Math.atan(1/collisionLine.getM());
        }else{
            angleToLine = 90;
        }
        float finalAngle = ball.convertDirection(-ball.convertDirection((ball.getDirection() + 90 - angleToLine))); //double conversion is necessary if -ball.convertDirection results in -180°
        return new Ball(ball.getPositionX(),ball.getPositionY(),finalAngle,ball.getVelocity());
    }
}
