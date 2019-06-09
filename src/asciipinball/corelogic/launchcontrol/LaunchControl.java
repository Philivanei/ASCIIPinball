package asciipinball.corelogic.launchcontrol;

import asciipinball.Settings;
import asciipinball.corelogic.players.PlayerManager;
import asciipinball.interfaces.Drawable;
import asciipinball.objects.Ball;
import asciipinball.objects.flipperfinger.MoveStatus;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Line;

public class LaunchControl extends PolygonEntity implements Drawable {

    private float sizeOfBall;
    private float maxHeight;
    private float minHeight;
    private MoveStatus moveStatus;
    private long upStartTime;
    private float x;
    private boolean isClicked;

    public LaunchControl(PlayerManager playerManager, float x, float minHeight, float maxHeight, float radiusOfBall) {
        super(playerManager);
        lines = new Line[1];
        //diameter of ball to calculate width of the launcherline
        sizeOfBall = radiusOfBall * 2;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.x = x;
        isClicked = false;
        moveStatus = MoveStatus.STOP;
        generateLines(minHeight);
    }


    //new game/ ball gets lost --> reset everything
    public void reset() {
        isClicked = false;
        moveStatus = MoveStatus.STOP;
        generateLines(minHeight);
    }

    //key reaction
    public void onSpaceDown() {

        if (!isClicked) {
            isClicked = true;
            upStartTime = System.currentTimeMillis();
        }
        moveStatus = MoveStatus.UP;
    }


    //creates the lines for animating them
    public void generateLines(float y) {

        if (y >= maxHeight) {
            //moving the launcher diagonally
            lines[0] = new Line(x, maxHeight, x + sizeOfBall, y);
        } else {
            //moving the launcher up (horizontal)
            lines[0] = new Line(x, y, x + sizeOfBall, y);
        }
    }


    //calculating the animation for moving the launcher up
    private float calculateHeightUp(long timeSinceStart) {

        float result = (maxHeight - minHeight) / Settings.TIME_FOR_JUMP * timeSinceStart + minHeight;
        if (result > (maxHeight + Settings.TILT_Y_OFFSET)) {
            //change movestatus
            result = maxHeight + Settings.TILT_Y_OFFSET;
            moveStatus = MoveStatus.STOP;
        } else {
            moveStatus = MoveStatus.UP;
        }
        return result;
    }

    //sets the lines to the wanted positions
    public void updateLaunchControl() {

        if (moveStatus == MoveStatus.UP) {
            long timeSinceUpStart = System.currentTimeMillis() - upStartTime;
            generateLines(calculateHeightUp(timeSinceUpStart));
        }
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        Ball returnBall = super.interactWithBall(ball);
        returnBall.addVelocity(0.1f);
        return returnBall;
    }

    @Override
    public char getColor() {
        return 'B';
    }
}