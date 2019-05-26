package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.Settings;
import asciipinball.interfaces.Drawable;

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
        this.positionX = ball.getPositionX();
        this.positionY = ball.getPositionY();
        this.direction = ball.getDirection();
        this.velocity = ball.getVelocity();
    }

    public void updateBall(Ball ball1, Ball ball2) {
        // calculate average if a ball hits a top or a corner
        // Philivanei was here
        //TODO
        float twoBallsDirection;
        twoBallsDirection = ((((ball1.getDirection() + 360) % 360) + ((ball2.getDirection() + 360) % 360)) / 2);
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
                    canvasSegment[row][column] = 'B';
                }
                else{
                    canvasSegment[row][column] = ' ';
                }
            }
        }

        int canvasRow = Settings.HEIGHT - Math.round(positionY + radius);
        int canvasColumn = Math.round(positionX - radius) + Settings.OFFSET;

        gameView.addToCanvas(canvasSegment,canvasRow,canvasColumn);

    }
}
