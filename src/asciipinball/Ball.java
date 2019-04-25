package asciipinball;

/**
 * This class creates a ball and simulates gravitation physics
 */
public class Ball {

    //This coordinate system has it's origin in the bottom left - it gets converted for the printing to canvas process in addToCanvas()
    float positionX;
    float positionY;
    private float direction;
    float velocity;

    public Ball(int x, int y) {
        positionX = x;
        positionY = y;
        direction = 0; //0° = right -> 90° = up -> 180° = left -> -90°/270° = down
        velocity = 0;
    }

    public Ball(int x, int y, float direction, float velocity){
        positionX = x;
        positionY = y;
        setDirection(direction);
        this.velocity = velocity;
    }

    public Ball(float xSpeed, float ySpeed, int x, int y){
        positionX = x;
        positionY = y;
        updateDirection(xSpeed, ySpeed);
        updateVelocity(xSpeed, ySpeed);
    }

    private float getXSpeed(){

        return (float) Math.cos(Math.toRadians(direction)) * velocity;

    }

    private float getYSpeed(){

        return (float) Math.sin(Math.toRadians(direction)) * velocity;

    }

    private void updateDirection(float xSpeed, float ySpeed){

        if(xSpeed >= 0){
            setDirection((float) Math.toDegrees(Math.atan(ySpeed/xSpeed)));
        }else if(xSpeed < 0 && ySpeed >= 0){
            setDirection((float) (180 - Math.abs(Math.toDegrees(Math.atan(ySpeed/xSpeed)))));
        }else if(xSpeed < 0 && ySpeed < 0){
            setDirection((float) (Math.abs(Math.toDegrees(Math.atan(ySpeed/xSpeed)))) - 180 );
        }


    }

    private void updateVelocity(float xSpeed, float ySpeed){

        velocity = (float) Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);

    }

    private void updatePosition(float xSpeed, float ySpeed){

        positionX += xSpeed;
        positionY += ySpeed;

    }


    public void drawToCanvas(GameView gameView,int gameTableHight){

        gameView.addToCanvas("O", gameTableHight - Math.round(positionY) - 1, Math.round(positionX));

    }


    /**
     * simulateTick takes a float gravitation value and simulates the behaviour of the ball per tick(call).
     * @param gravitationPerTick
     */
    public void simulateTick(float gravitationPerTick){

        float xSpeed = getXSpeed();
        float ySpeed = getYSpeed();
        System.out.println("XSpeed: " + xSpeed + " YSpeed: " + ySpeed);

        updatePosition(xSpeed,ySpeed);

        //Simulate gravitation
        ySpeed -= gravitationPerTick;

        updateDirection(xSpeed,ySpeed);
        updateVelocity(xSpeed,ySpeed);


    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getXCor(){
        return positionX;
    }


    public float getYCor(){
        return positionY;
    }

    public float getDirection() {
        return direction;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setDirection(float direction){

        if(direction > -180 && direction <= 180){
            this.direction = direction;
        }
        else{

            float clippedDirection = 0;

            clippedDirection = direction % 360;

            if(clippedDirection > 180){
                this.direction = clippedDirection - 360;
            }
            else{
                this.direction = clippedDirection;
            }

        }

    }
}
