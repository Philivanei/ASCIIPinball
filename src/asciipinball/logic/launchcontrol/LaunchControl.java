package asciipinball.logic.launchcontrol;


import asciipinball.GameView;
import asciipinball.interfaces.Drawable;
import asciipinball.logic.Ball;
import asciipinball.logic.flipperfinger.Direction;
import asciipinball.logic.flipperfinger.MoveStatus;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Line;

public class LaunchControl extends PolygonEntity implements Drawable {

    private final float TIME_FOR_JUMP = 100;
    private float maxHeight;
    private float minHeight;
    private Direction direction;
    private MoveStatus moveStatus = MoveStatus.STOP;
    private long upStartTime;
    private long downStartTime;
    private float x;
    private float y;


    public LaunchControl(float x, float y, float minHeight, float maxHeight) {
        direction = Direction.DOWN;
        lines = new Line[1];
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.x = x;
        this.y = y;
        generateLines(minHeight);
    }

    //key reaction
    public void onSpaceDown() {
        if (direction == Direction.DOWN) {
            upStartTime = System.currentTimeMillis();
        }
        direction = Direction.UP;
    }

    //key reaction
    public void onSpaceUp() {
        if (direction == Direction.UP) {
            downStartTime = System.currentTimeMillis();
        }
        direction = Direction.DOWN;
    }


    public void generateLines(float y) {

        lines[0] = new Line(x, y, /*groesse des balls einfuegen*/x+3, y);
    }

    //calculating the animation for moving the launcher up
    private float calculateHeightUp(long timeSinceStart) {
        System.out.println(timeSinceStart);
        float result = (maxHeight - minHeight) / TIME_FOR_JUMP * timeSinceStart + minHeight;
        if (result > maxHeight) {
            moveStatus = MoveStatus.STOP;
            return maxHeight;
        } else {
            moveStatus = MoveStatus.UP;
        }
        return result;
    }


    //calculating the animation for moving the launcher down
    private float calculateHeightDown(long timeSinceStart) {
        System.out.println(timeSinceStart);
        float result = (minHeight - maxHeight) / TIME_FOR_JUMP * timeSinceStart + maxHeight;
        if (result < minHeight) {
            moveStatus = MoveStatus.STOP;
            return minHeight;
        } else {
            moveStatus = MoveStatus.DOWN;
        }
        return result;
    }

    public void updateLaunchControl(){
        if (direction == Direction.UP){
            long timeSinceUpStart = System.currentTimeMillis() - upStartTime;
            generateLines(calculateHeightUp(timeSinceUpStart));
        }else{
            long timeSinceDownStart = System.currentTimeMillis() - downStartTime;
            generateLines(calculateHeightDown(timeSinceDownStart));
        }
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        Ball returnBall = super.interactWithBall(ball);
        returnBall.addVelocity(0.1f);
        return returnBall;
    }

    @Override
    public void addToCanvas(GameView gameView) {
        lines[0].addToCanvas(gameView);
    }
}