package asciipinball.logic.flipperfinger;

import asciipinball.GameView;
import asciipinball.interfaces.Drawable;
import asciipinball.logic.Ball;

import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class FlipperFingerControl implements Drawable {

    private LeftFlipperFinger leftFlipperFinger;
    private RightFlipperFinger rightFlipperFinger;
    private Direction leftDirection;
    private Direction rightDirection;
    private long upStartTimeLeft;
    private long downStartTimeLeft;
    private long upStartTimeRight;
    private long downStartTimeRight;


    public FlipperFingerControl(float xLeftFlipperFinger, float yLeftFlipperFinger, float xRightFlipperFinger,
                                float yRightFlipperFinger, float length, float minAngle, float maxAngle){
        leftFlipperFinger = new LeftFlipperFinger(xLeftFlipperFinger, yLeftFlipperFinger, length, minAngle, maxAngle);
        rightFlipperFinger = new RightFlipperFinger(xRightFlipperFinger, yRightFlipperFinger, length, minAngle, maxAngle);
        leftDirection = Direction.DOWN;
        rightDirection = Direction.DOWN;
        upStartTimeRight = System.currentTimeMillis();
        downStartTimeRight = System.currentTimeMillis();
        upStartTimeLeft = System.currentTimeMillis();
        downStartTimeLeft = System.currentTimeMillis();

    }

    public void updateFlipperFinger(){

        if(leftDirection == Direction.UP){
            long timeSinceDown = System.currentTimeMillis() - downStartTimeLeft;
            //System.out.println(leftFlipperFinger.calculateAngleUp(upStartTimeLeft, timeSinceDown));
            leftFlipperFinger.generateLine(leftFlipperFinger.calculateAngleUp(upStartTimeLeft, timeSinceDown));
        }else{
            long timeSinceUp = System.currentTimeMillis() - upStartTimeLeft;
            leftFlipperFinger.generateLine(leftFlipperFinger.calculateAngleDown(downStartTimeLeft, timeSinceUp));
        }

        if(rightDirection == Direction.UP){
            long timeSinceDown = System.currentTimeMillis() - downStartTimeRight;
            rightFlipperFinger.generateLine(rightFlipperFinger.calculateAngleUp(upStartTimeRight, timeSinceDown));
        }else{
            long timeSinceUp = System.currentTimeMillis() - upStartTimeRight;
            rightFlipperFinger.generateLine(rightFlipperFinger.calculateAngleDown(downStartTimeRight, timeSinceUp));
        }

    }


    public void onLeftDown(){
        if(leftDirection == Direction.DOWN){
            upStartTimeLeft = System.currentTimeMillis();
        }
        leftDirection = Direction.UP;
    }

    public void onLeftUp(){
        if(leftDirection == Direction.UP){
            downStartTimeLeft = System.currentTimeMillis();
        }
        leftDirection = Direction.DOWN;
    }

    public void onRightDown(){
        if(rightDirection == Direction.DOWN){
            upStartTimeRight = System.currentTimeMillis();
        }
        rightDirection = Direction.UP;
    }

    public void onRightUp(){
        if(rightDirection == Direction.UP){
            downStartTimeRight = System.currentTimeMillis();
        }
        rightDirection = Direction.DOWN;
    }


    public Ball updatePhysics(Ball ball){

        ArrayList<Ball> flipperBalls = new ArrayList<Ball>();

        Ball leftBall = leftFlipperFinger.updateEntity(ball);
        Ball rightBall = rightFlipperFinger.updateEntity(ball);

        if(leftBall != null){
            flipperBalls.add(leftBall);
        }
        if(rightBall != null){
            flipperBalls.add(rightBall);
        }

        if(flipperBalls.isEmpty()){
            return null;
        }
        return ball.joinBalls(flipperBalls);
    }

    public LeftFlipperFinger getLeftFlipperFinger() {
        return leftFlipperFinger;
    }

    public RightFlipperFinger getRightFlipperFinger() {
        return rightFlipperFinger;
    }

    @Override
    public char getColor() {
        return 'B';
    }
}
