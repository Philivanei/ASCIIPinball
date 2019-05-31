package asciipinball.logic.flipperfinger;

import asciipinball.logic.Ball;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Line;

public abstract class FlipperFinger extends PolygonEntity {

    protected float x;
    protected float y;
    protected float minAngle;
    protected float maxAngle;
    protected float length;
    protected static final float TIME_FOR_FLIP = 250;

    public FlipperFinger(float x, float y, float length , float minAngle, float maxAngle){
        lines = new Line[1];
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
        this.length = length;
        this.x = x;
        this.y = y;
        generateLine(minAngle);
    }


    protected abstract void generateLine(float angle);


    public float calculateAngleUp(long startTime, long timeSinceDown){

        float startShift = (TIME_FOR_FLIP - timeSinceDown);
        if(startShift < 0){
            startShift = 0;
        }

        float time = startShift + (System.currentTimeMillis() - startTime);

        float result = ((maxAngle - minAngle) / TIME_FOR_FLIP) * time + minAngle;
        if (result > maxAngle) {
            result = maxAngle;
        }
        return result;
    }
    public float calculateAngleDown(long startTime, long timeSinceUp){

        float startShift = (TIME_FOR_FLIP - timeSinceUp);
        if(startShift < 0){
            startShift = 0;
        }

        float time = startShift + (System.currentTimeMillis() - startTime);

        float result = (minAngle - maxAngle) / TIME_FOR_FLIP * time + maxAngle;
        if (result < minAngle) {
            result = minAngle;
            return result;
        }
        return result;
    }

    @Override
    public Ball updateEntity(Ball ball) {
        Ball unmodifiedBall = super.updateEntity(ball);
        if(unmodifiedBall != null){
            unmodifiedBall.addVelocity(0.03f);
            System.out.println("COLLISION WITH FINGERS DETECTED: " +  ball.getDirection() + " -> " + unmodifiedBall.getDirection());
            unmodifiedBall.preventBug();
        }
        return unmodifiedBall;
    }
}
