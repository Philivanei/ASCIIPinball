package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.Settings;
import asciipinball.interfaces.Drawable;

import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;

/**
 * This class creates a ball and simulates gravitation physics
 */
public class Ball implements Drawable {

    //This coordinate system has it's origin in the bottom left - it gets converted for the printing to canvas process in addToCanvas()
    private float positionX;
    private float positionY;
    private float futurePositionX;
    private float futurePositionY;
    private float futureDirection;
    private float futureVelocity;
    private float direction;
    private float velocity;
    private float radius;

    public Ball(Ball ball){
        positionX = ball.getPositionX();
        positionY = ball.getPositionY();
        futurePositionX = ball.futurePositionX;
        futurePositionY = ball.futurePositionY;
        futureDirection = ball.futureDirection;
        futureVelocity = ball.futureVelocity;
        direction = ball.direction;
        velocity = ball.getVelocity();
        radius = ball.getRadius();
    }

    public Ball(float x, float y, float radius) {
        positionX = x;
        positionY = y;
        this.radius = radius;
        direction = 0; //0° = right -> 90° = up -> 180° = left -> -90°/270° = down
        velocity = 0;
    }

    public Ball(float x, float y, float radius, float direction, float velocity) {
        positionX = x;
        positionY = y;
        this.radius = radius;
        this.direction = convertDirection(direction);
        this.velocity = velocity;
    }


    public float getRadius() {
        return radius;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getFuturePositionX() {
        return futurePositionX;
    }

    public float getFuturePositionY() {
        return futurePositionY;
    }

    public float getFutureDirection() {
        return futureDirection;
    }

    public float getFutureVelocity() {
        return futureVelocity;
    }

    public float getDirection() {
        return direction;
    }

    public float getVelocity() {
        return velocity;
    }

    private float getXSpeed() {

        return (float) Math.cos(Math.toRadians(direction)) * velocity;

    }

    private float getYSpeed() {

        return (float) Math.sin(Math.toRadians(direction)) * velocity;

    }

    public float convertDirection(float direction) {
        float result = direction;

        if (direction > -180 && direction <= 180) {
            result = direction;
        } else {

            float clippedDirection = 0;

            clippedDirection = direction % 360;

            if (clippedDirection > 180) {
                result = clippedDirection - 360;
            } else {
                result = clippedDirection;
            }
        }
        return result;
    }


    private void calculateFutureDirection(float xSpeed, float ySpeed) {

        if (xSpeed >= 0) {
            futureDirection = convertDirection((float) Math.toDegrees(Math.atan(ySpeed / xSpeed)));
        } else if (xSpeed < 0 && ySpeed >= 0) {
            futureDirection = convertDirection((float) (180 - Math.abs(Math.toDegrees(Math.atan(ySpeed / xSpeed)))));
        } else if (xSpeed < 0 && ySpeed < 0) {
            futureDirection = convertDirection((float) (Math.abs(Math.toDegrees(Math.atan(ySpeed / xSpeed)))) - 180);
        }


    }

    private void calculateFutureVelocity(float xSpeed, float ySpeed) {

        futureVelocity = (float) Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);

    }

    private void calculateFuturePosition(float xSpeed, float ySpeed) {

        futurePositionX = positionX + xSpeed;
        futurePositionY = positionY + ySpeed;

    }


    /**
     * calculate Future takes a float gravitation value and simulates the behaviour of the ball per tick(call).
     *
     * @param gravitationPerTick
     */
    public void calculateFuture(float gravitationPerTick) {

        float xSpeed = getXSpeed();
        float ySpeed = getYSpeed();


        calculateFuturePosition(xSpeed, ySpeed);

        //Simulate gravitation
        ySpeed -= gravitationPerTick;
        //System.out.println("XSpeed: " + xSpeed + " YSpeed: " + ySpeed);

        addVelocity(-Settings.FRICTION);

        calculateFutureDirection(xSpeed, ySpeed);
        calculateFutureVelocity(xSpeed, ySpeed);

    }

    public void updateBall() {
        positionX = futurePositionX;
        positionY = futurePositionY;
        velocity = futureVelocity;
        direction = futureDirection;
    }

    public void updateBall(Ball ball) {

       /* Ball ballCopy = new Ball(ball);

        for(int i = 0; i < 2; i++){
            ballCopy = new Ball(ballCopy.getFuturePositionX(), ballCopy.getFuturePositionY(), ballCopy.getRadius(), ball.getDirection(), ball.getVelocity());
            ballCopy.calculateFuture(Settings.GRAVITATION);
        }*/

        ball.calculateFuture(Settings.GRAVITATION);

        this.positionX = ball.getFuturePositionX();
        this.positionY = ball.getFuturePositionY();
        this.direction = ball.getDirection();
        this.velocity = ball.getVelocity();
    }

    public void preventBug(){
        for(int i = 0; i < 75; i++){
            calculateFuture(Settings.GRAVITATION);
            positionX = futurePositionX;
            positionY = futurePositionY;
            velocity = futureVelocity;
            direction = futureDirection;
        }

    }

    public Ball joinBalls(ArrayList<Ball> balls) {
        // calculate average if a ball hits a top or a corner
        // Philivanei was here
        //float twoBallsDirection;
        //twoBallsDirection = ((((ball1.getDirection() + 360) % 360) + ((ball2.getDirection() + 360) % 360)) / 2);

        int arrayLength = balls.size();

        if(balls.isEmpty()){
            return null;
        }else if(arrayLength == 1){
            return balls.get(0);
        }else if(arrayLength == 2){
            Ball ball1 = balls.get(0);
            Ball ball2 = balls.get(1);
            float newDirection = ball1.convertDirection((((ball1.getDirection() + 360) % 360) + ((ball2.getDirection() + 360) % 360)) / 2);
            //TODO calculate a new velocity
            System.out.println("There were two Collisions");
            return new Ball(ball1.positionX,ball1.positionY,ball1.radius,newDirection,ball1.getVelocity());

        }else{
            //TODO implement support for more than 2 Collision
            System.out.println("MORE THAN TWO COLLISIONS ARE NOT SUPPORTET AT THIS POINT");
            Ball ball1 = balls.get(0);
            Ball ball2 = balls.get(1);
            float newDirection = ball1.convertDirection((((ball1.getDirection() + 360) % 360) + ((ball2.getDirection() + 360) % 360)) / 2);
            return new Ball(ball1.positionX,ball1.positionY,ball1.radius,newDirection,ball1.getVelocity());
        }
    }

    public void addVelocity(float velocityAdd){
        float newVelocity = velocity + velocityAdd;
        if(newVelocity > Settings.MIN_SPEED && newVelocity < Settings.MAX_SPEED){
            velocity = newVelocity;
        }else if(newVelocity > Settings.MAX_SPEED){
            velocity = Settings.MAX_SPEED;
        }else if(newVelocity < Settings.MIN_SPEED){
            velocity = Settings.MIN_SPEED;
        }
    }


    @Override
    public void addToCanvas(GameView gameView) {

        int diameter = Math.round(radius * 2);

        char[][] canvasSegment = new char[diameter][diameter];

        for(int column = 0; column < canvasSegment[0].length; column++){
            for(int row = 0; row < canvasSegment.length; row++) {
                float distanceCanvasMiddle = (float) Math.sqrt(Math.pow(((radius - 0.5) - column),2) + Math.pow(((radius - 0.5) - row),2));
                //canvasSegment[row][column] = (distanceCanvasMiddle < radius) ? 'B' : ' ';
                if(distanceCanvasMiddle <= radius){
                    canvasSegment[row][column] = 'Y';
                }
                else{
                    canvasSegment[row][column] = ' ';
                }
            }
        }

        int canvasRow = Settings.HEIGHT - Math.round(positionY + radius);
        int canvasColumn = Math.round(positionX - radius) + Settings.OFFSET;

        gameView.addColorStringToCanvas(canvasSegment,canvasRow,canvasColumn);

    }
}
