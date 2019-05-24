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
            if(Line2D.linesIntersect(ball.getPositionX(),ball.getPositionY(),
                    ball.getFuturePositionX(), ball.getFuturePositionY(),
                    lines[i].getX1(), lines[i].getY1(),
                    lines[i].getX2(), lines[i].getY2())){ //This checks if the lineSegments intersect

                float mBall = (ball.getFuturePositionY() - ball.getPositionY()) / (ball.getFuturePositionX() - ball.getPositionX());
                float bBall = ball.getPositionY() - mBall * ball.getPositionX();
                float mLine = (lines[i].getY2() - lines[i].getY1()) / (lines[i].getX2() - lines[i].getX1());
                float bLine = lines[i].getY1() - mLine * lines[i].getX1();

                float x = (bLine - bBall) / (mBall - mLine);
                float y = mBall * x + mBall;

                float distancePosCollision = (float) Math.sqrt((x-ball.getPositionX()) * (x-ball.getPositionX()) + (y-ball.getPositionY()) * (y-ball.getPositionY()));

                if(distancePosCollision < minDistance){
                    minDistance = distancePosCollision;
                    collisionPositionX = x;
                    collisionPositionY = y;
                }

                collisionDetected = true;
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
