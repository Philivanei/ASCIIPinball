package asciipinball.logic.launchcontrol;


import asciipinball.GameView;
import asciipinball.logic.flipperfinger.Direction;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Line;

import java.awt.event.KeyEvent;

public class LaunchControl extends PolygonEntity {

    private final float TIME_FOR_JUMP = 100;
    private final float maxHeight = 10;
    private Direction direction;
    private long upStartTime;
    private long downStartTime;
    private float x1;
    private float y1;


    public LaunchControl(float x1, float y1) {
        direction = Direction.DOWN;
        lines = new Line[1];
        this.x1 = x1;
        this.y1 = y1;
    }

    public void onSpaceDown() {
        if (direction == Direction.DOWN) {
            upStartTime = System.currentTimeMillis();
        }
        direction = Direction.UP;
    }

    public void onSpaceUp() {
        if (direction == Direction.UP) {
            downStartTime = System.currentTimeMillis();
        }
        direction = Direction.DOWN;

        //generates smooth lines upwards
        if (direction == Direction.UP) {
            long timeSinceDown = System.currentTimeMillis() - downStartTime;
            //TODO generate lines
        }
        long timeSinceUp = System.currentTimeMillis() - upStartTime;
        //TODO generate lines
    }

    @Override
    public void addToCanvas(GameView gameView) {
        lines[0].addToCanvas(gameView);
    }
}