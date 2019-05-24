package asciipinball.objects.physicobject;

import asciipinball.logic.Ball;

public abstract class PhysicEntity {

    protected float positionX;
    protected float positionY;
    protected float collisionPositionX;
    protected float collisionPositionY;

    public Ball updateEntity(Ball ball){
        if(isCollided(ball)){
            return interactWitBall(ball);
        } else {
            return null;
        }
    }

    protected abstract boolean isCollided(Ball ball);
    protected abstract Ball interactWitBall(Ball ball);

}
