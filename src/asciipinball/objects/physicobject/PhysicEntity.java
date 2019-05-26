package asciipinball.objects.physicobject;

import asciipinball.interfaces.Drawable;
import asciipinball.logic.Ball;

public abstract class PhysicEntity implements Drawable {

    protected float positionX;
    protected float positionY;
    protected float collisionPositionX;
    protected float collisionPositionY;
    protected int score;

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
    public int getScore(){
        return score;
    }

}
