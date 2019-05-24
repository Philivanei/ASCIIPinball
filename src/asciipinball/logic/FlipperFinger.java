package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Line;

import java.awt.event.KeyEvent;

public class FlipperFinger extends PolygonEntity {

    private boolean isFlipperFingeractive;
    //import right class because java already has a library called Line
    private Line lines[];
    private final int timeForStopAngle = 250;
    private float stopAngle;
    private float startAngle;
    private long startTime;
    private long stopTime;
    private long abortTimeStop;
    private long abortTimeStart;
    private float adjacentSide;
    private float oppositeSide;
    private float x1;
    private float y1;

    //startangle initializes the angle in wich the flipperfingers are created
    //stopangle where the flipperfingers should stop
    public FlipperFinger(GameView gameView, float x, float y, float startAngle, float stopAngle, float length) {
        this.startAngle = startAngle;
        this.stopAngle = stopAngle;
        calculateLine(x, y, startAngle, length);
    }

    public Line calculateLine(float x, float y, float startAngle, float length) {
        adjacentSide = ((float) Math.cos(Math.toRadians(startAngle))) * length;
        oppositeSide = ((float) Math.sin(Math.toRadians(startAngle))) * length;
        x1 = adjacentSide - x;
        y1 = oppositeSide - y;
        return new Line(x, y, x1, y1);
    }

    //moves the line of the flipperfingers up
    public float calculateAngleUp(long timeSinceStart) {
        float result = (stopAngle - startAngle) / timeForStopAngle * timeSinceStart + startAngle;
        if (result > stopAngle) {
            result = stopAngle;
        }
        return result;
    }

    //moves the line of the flippersfingers down
    public float calculateAngleDown(long timeSinceStop) {
        float result = -(stopAngle - startAngle) / timeForStopAngle * timeSinceStop + stopAngle;
        if (result < startAngle) {
            result = startAngle;
        }
        return result;
    }

    public void updateFlipperfinger(Ball ball, GameView gameView) {
        KeyEvent[] keyEvent;
        try {
            keyEvent = gameView.getKeyEvents();
            if (keyEvent.length != 0) {
                //moving the left flipperfinger with 'a' or 'left'
                //TODO don't forget to set is flipperfingeractive to false
                if (((keyEvent[0].getKeyCode() == KeyEvent.VK_LEFT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_A))
                        && (keyEvent[0].getID() == KeyEvent.KEY_PRESSED)) {
                    isFlipperFingeractive = true;
                    startTime = System.currentTimeMillis();
                    abortTimeStop = System.currentTimeMillis() - stopTime;

                }
                else if(((keyEvent[0].getKeyCode() == KeyEvent.VK_LEFT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_A))
                        && (keyEvent[0].getID() == KeyEvent.KEY_RELEASED)){
                    isFlipperFingeractive = false;
                    stopTime = System.currentTimeMillis();
                    abortTimeStart = System.currentTimeMillis() - startTime;
                }
            }
            //moving the right flipperfinger with 'd' or 'right
            //TODO don't forget to set is flipperfingeractive to false
            if (((keyEvent[0].getKeyCode() == KeyEvent.VK_RIGHT) || (keyEvent[0].getKeyCode() == KeyEvent.VK_D))
                    && (keyEvent[0].getID() == KeyEvent.KEY_PRESSED)) {
            }
            float angle;
            if(isFlipperFingeractive){
                angle = calculateAngleUp((timeForStopAngle - abortTimeStop + System.currentTimeMillis()));
            }else{
                angle = calculateAngleUp((timeForStopAngle - abortTimeStart + System.currentTimeMillis()));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
