package asciipinball.objects.physicobject;

import asciipinball.interfaces.Drawable;
import asciipinball.logic.Ball;
import asciipinball.logic.Player;

public abstract class PhysicEntity implements Drawable {

    protected float positionX;
    protected float positionY;
    protected float collisionPositionX;
    protected float collisionPositionY;
    protected int score = 0;

    public Ball updateEntity(Ball ball){
        if(isCollided(ball)){
            System.out.println("Collision DETECTED");
            return interactWitBall(ball);
        } else {
            return null;
        }
    }

    protected abstract boolean isCollided(Ball ball);
    protected abstract Ball interactWitBall(Ball ball);
    public void setScore(Player player){
        if(score != 0){
            player.addToScore(score);
        }
    }

}
