package asciipinball.objects.physicobject.circular;

import asciipinball.logic.Ball;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.shapes.Circle;

public abstract class CircleEntity extends PhysicEntity {

    protected Circle[] circles;

    @Override
    protected boolean isCollided(Ball ball) {
        //TODO
        return false;
    }

    @Override
    protected Ball interactWitBall(Ball ball) {
        //TODO
        return null;
    }
}
