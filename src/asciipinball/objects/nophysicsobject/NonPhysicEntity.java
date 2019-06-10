package asciipinball.objects.nophysicsobject;

import asciipinball.interfaces.Drawable;
import asciipinball.objects.Ball;
import asciipinball.shapes.Circle;

public abstract class NonPhysicEntity implements Drawable {

    protected Circle[] circles;

    public abstract void updateEntity(Ball ball);
    public Circle[] getCircles(){
        return circles;
    }

}
