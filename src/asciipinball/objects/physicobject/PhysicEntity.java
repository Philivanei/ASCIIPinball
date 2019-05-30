package asciipinball.objects.physicobject;

import asciipinball.CollisionData;
import asciipinball.interfaces.Drawable;
import asciipinball.logic.Ball;
import asciipinball.logic.Player;

import java.util.ArrayList;

public abstract class PhysicEntity implements Drawable {

    protected ArrayList<CollisionData> collisionList = new ArrayList<CollisionData>();
    protected int score = 0;

    public Ball updateEntity(Ball ball){
        if(isCollided(ball)){
            System.out.println("Collision DETECTED");
            return interactWithBall(ball);
        } else {
            return null;
        }
    }

    //Deletes all Collisions except the nearest
    protected void cleanupCollisionList(){

        if(!collisionList.isEmpty() && collisionList.size() > 1){
            for(int i = 1; i < collisionList.size() ;i++){
                if(collisionList.get(i) != collisionList.get(i-1)){
                    int iterator = i;
                    while (iterator < collisionList.size()){
                        collisionList.remove(iterator);
                        iterator++;
                    }
                    break;
                }
            }
        }

    }

    protected float calculateBallAngleFromGradient(Ball ball, float gradient){
        float angleToLine;

        if(Float.isFinite(gradient)){

            if(Float.isFinite(1/gradient)){
                angleToLine = (float) Math.toDegrees(Math.atan(1/gradient));
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

        return ball.convertDirection(-(ball.getDirection() - angleToLine) + angleToLine);
        //float finalAngle = ball.convertDirection((-ball.convertDirection((ball.getDirection() + 90 - angleToLine)))  - (90 - angleToLine)); //double conversion is necessary if -ball.convertDirection results in -180°
    }

    protected abstract boolean isCollided(Ball ball);
    protected abstract Ball interactWithBall(Ball ball);
    public void setScore(Player player){
        if(score != 0){
            player.addToScore(score);
        }
    }

}
