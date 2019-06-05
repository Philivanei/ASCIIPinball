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
    protected MoveStatus moveStatus = MoveStatus.STOP;

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
            moveStatus = MoveStatus.STOP;
            result = maxAngle;
        }else{
            moveStatus = MoveStatus.UP;
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
            moveStatus = MoveStatus.STOP;
            result = minAngle;
        }else{
            moveStatus = MoveStatus.DOWN;
        }
        return result;
    }

    @Override
    public Ball updateEntity(Ball ball) {
        Ball unmodifiedBall = super.updateEntity(ball);
        if(unmodifiedBall != null) {
            if (moveStatus == MoveStatus.UP) {
                unmodifiedBall.addVelocity(0.03f);
            }else if(moveStatus == MoveStatus.DOWN){
                unmodifiedBall.addVelocity(-0.006f);
            }
            unmodifiedBall.jumpToFuture(75);

        }
        return unmodifiedBall;
    }
}
