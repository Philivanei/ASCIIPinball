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

    protected abstract boolean isCollided(Ball ball);
    protected abstract Ball interactWithBall(Ball ball);
    public void setScore(Player player){
        if(score != 0){
            player.addToScore(score);
        }
    }

}
