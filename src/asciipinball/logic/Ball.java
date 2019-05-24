package asciipinball.logic;

import asciipinball.GameView;

/**
 * This class creates a ball and simulates gravitation physics
 */
public class Ball {

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

    public Ball(float x, float y) {
        positionX = x;
        positionY = y;
        direction = 0; //0° = right -> 90° = up -> 180° = left -> -90°/270° = down
        velocity = 0;
    }

    public Ball(float x, float y, float direction, float velocity) {
        positionX = x;
        positionY = y;
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
     * simulateTick takes a float gravitation value and simulates the behaviour of the ball per tick(call).
     *
     * @param gravitationPerTick
     */
    public void calculateFuture(float gravitationPerTick) {

        float xSpeed = getXSpeed();
        float ySpeed = getYSpeed();
        System.out.println("XSpeed: " + xSpeed + " YSpeed: " + ySpeed);

        calculateFuturePosition(xSpeed, ySpeed);

        //Simulate gravitation
        ySpeed -= gravitationPerTick;

        calculateFutureDirection(xSpeed, ySpeed);
        calculateFutureVelocity(xSpeed, ySpeed);

    }

    public void updateBall() {
        positionX = futurePositionX;
        positionY = futurePositionY;
    }

    public void updateBall(Ball ball) {
        this.positionX = ball.getPositionX();
        this.positionY = ball.getPositionY();
        this.direction = ball.getDirection();
        this.velocity = ball.getVelocity();
    }

    public void updateBall(Ball ball1, Ball ball2) {
        // calculate average if a ball hits a top or a corner
        // Philivanei was here
        float twoBallsDirection;
        twoBallsDirection = ((((ball1.getDirection() + 360) % 360) + ((ball2.getDirection() + 360) % 360)) / 2);
    }


}
