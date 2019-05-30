package asciipinball.objects.physicobject.polygonial;

import asciipinball.CollisionData;
import asciipinball.logic.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Line;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;

public abstract class PolygonEntity extends PhysicEntity {

    protected Line[] lines;

    @Override
    protected boolean isCollided(Ball ball) {

        boolean collisionDetected = false;

        for(int i = 0; i < lines.length; i++){
            float distanceToBall;
            float distanceToA;
            float distanceToB;
            float x;
            float y;

            float mLine = lines[i].getM();
            if(Float.isFinite(mLine)){ //If The line isn't 90° straight
                float bLine = lines[i].getB();
                float mVertical = -1/mLine;
                if(Float.isFinite(mVertical)){
                    float bVertical = ball.getFuturePositionY() - mVertical * ball.getFuturePositionX();

                    x = (bLine - bVertical) / (mVertical - mLine);
                    y = mVertical * x + bVertical;
                    //calc distance Ball to lotfußpunkt
                    distanceToBall = (float) Math.sqrt(Math.pow((x - ball.getFuturePositionX()),2) + Math.pow((y - ball.getFuturePositionY()),2));
                }else{ //if Line is 0° horizontal
                    x = ball.getFuturePositionX();
                    y = lines[i].getY1();

                    distanceToBall = Math.abs(y - ball.getFuturePositionY());
                }


            }else{ // If the line is 90° straight (up)

                x = lines[i].getX1();
                y = ball.getFuturePositionY();

                distanceToBall = Math.abs(ball.getFuturePositionX() - lines[i].getX1());
            }

            //System.out.println(distanceToBall + " " + Boolean.toString(Float.isInfinite(mLine)));

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
                collisionList.add(new CollisionData(x, y, lines[i], distanceToBall));
            }
        }

        cleanupCollisionList();

        return collisionDetected;
    }


    @Override
    protected Ball interactWithBall(Ball ball){

        ArrayList<Ball> ballList = new ArrayList<Ball>();

        while(!collisionList.isEmpty()){

            CollisionData collisionData = collisionList.get(0);
            Line collisionLine = (Line) collisionData.getCollisionShape();
            collisionList.remove(0);

            //Checks if the collisionPoint is on the line segment
            float collisionPointDistanceA = (float) (Math.sqrt(Math.pow((collisionData.getCollisionX() - collisionLine.getX1()),2) +
                    Math.pow((collisionData.getCollisionY() - collisionLine.getY1()),2)));
            float collisionPointDistanceB = (float) (Math.sqrt(Math.pow((collisionData.getCollisionX() - collisionLine.getX2()),2) +
                    Math.pow((collisionData.getCollisionY() - collisionLine.getY2()),2)));

            float finalAngle;

            if(collisionPointDistanceA >= collisionLine.getLength() || collisionPointDistanceB >= collisionLine.getLength()){ //If this statement is true the ball is bumping into the side of the Line
                finalAngle = ball.convertDirection(-ball.getDirection());
            }else{ //In this case the Ball hits the Line "normaly"
                float angleToLine;

                if(Float.isFinite(collisionLine.getM())){

                    if(Float.isFinite(1/collisionLine.getM())){
                        angleToLine = (float) Math.toDegrees(Math.atan(1/collisionLine.getM()));
                    }else{
                        angleToLine = 0;
                    }
                }else{
                    angleToLine = 90;
                }

                System.out.println(angleToLine);

                if(angleToLine < 0){
                    angleToLine = Math.abs(angleToLine) + 90;
                }

                finalAngle = ball.convertDirection(-(ball.getDirection() - angleToLine) + angleToLine);
                //float finalAngle = ball.convertDirection((-ball.convertDirection((ball.getDirection() + 90 - angleToLine)))  - (90 - angleToLine)); //double conversion is necessary if -ball.convertDirection results in -180°
                System.out.println(ball.getDirection() + " -> " + finalAngle);
            }

            ballList.add(new Ball(ball.getPositionX(),ball.getPositionY(), ball.getRadius(),finalAngle,ball.getVelocity()));

        }

        return ball.joinBalls(ballList);

    }
}
