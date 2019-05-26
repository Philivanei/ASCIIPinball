package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.interfaces.Drawable;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Line;

public abstract class FlipperFinger extends PolygonEntity {

    //flipperFingerDirection is true when moving up and false when moving down
    protected boolean flipperFingerDirection;
    //how fast the flipperfingers should move in ms
    protected final int TIMEFORSTOPANGLE = 250;
    protected float stopAngle;
    protected long startTime;
    protected long stopTime;
    protected long abortTimeStop;
    protected long abortTimeStart;
    protected float startAngle;
    protected float length;
    protected float adjacentSide;
    protected float oppositeSide;
    protected float x;
    protected float y;

    public abstract Line calculateLine(float x, float y, float startAngle, float length);

    public abstract void updateFlipperfinger(Ball ball, GameView gameView);

    //startangle initializes the angle in wich the flipperfingers are created
    //stopangle where the flipperfingers should stop
    public FlipperFinger(float x, float y, float startAngle, float stopAngle, float length) {
        // this.startAngle = startAngle;
        this.stopAngle = stopAngle;
        this.startAngle = startAngle;
        this.length = length;
        lines = new Line[1];
        lines[0] = calculateLine(x, y, startAngle, length);
    }

    //moves the line of the flipperfingers up
    public float calculateAngleUp(long timeSinceStart, float startAngle) {
        float result = (stopAngle - startAngle) / TIMEFORSTOPANGLE * timeSinceStart + startAngle;
        if (result > stopAngle) {
            result = stopAngle;
        }
        return result;
    }

    //moves the line of the flippersfingers down
    public float calculateAngleDown(long timeSinceStop, float startAngle) {
        float result = -(stopAngle - startAngle) / TIMEFORSTOPANGLE * timeSinceStop + stopAngle;
        if (result < startAngle) {
            result = startAngle;
            return result;
        }
        return result;
    }

    @Override
    protected Ball interactWitBall(Ball ball) {

        Ball standardBall = super.interactWitBall(ball);
        return new Ball(standardBall.getPositionX(), standardBall.getPositionY(), standardBall.getRadius() , standardBall.getDirection(), (standardBall.getVelocity() + 0.03f));
    }

    @Override
    public void addToCanvas(GameView gameView) {
        lines[0].addToCanvas(gameView);
    }
}
