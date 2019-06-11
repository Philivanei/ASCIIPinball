package asciipinball.objects.physicobject.polygonial;

import asciipinball.Coordinate;
import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.objects.Ball;
import asciipinball.shapes.Line;

public class RotatingCross extends PolygonEntity {

    private float x;
    private float y;
    private float speed;
    private float radius;
    private float step;
    private boolean turnClockWise;

    public RotatingCross(PlayerManager playerManager, float x, float y, float radius , float speed, boolean turnClockWise) {
        super(playerManager);
        lines = new Line[2];
        generateLines();
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
        this.turnClockWise = turnClockWise;
        step = 0;
    }

    private void generateLines(){
        byte factor = -1;
        if(turnClockWise){
            factor = 1;
        }
        Coordinate line1A = new Coordinate((float) (x + Math.sin(step) * radius), (float) (y + factor * Math.cos(step) * radius));
        Coordinate line1B = new Coordinate((float) (x - Math.sin(step) * radius), (float) (y - factor * Math.cos(step) * radius));

        Coordinate line2A = new Coordinate((float) (x - Math.cos(step) * radius), (float) (y + factor * Math.sin(step) * radius));
        Coordinate line2B = new Coordinate((float) (x + Math.cos(step) * radius), (float) (y - factor * Math.sin(step) * radius));

        lines[0] = new Line(line1A, line1B);
        lines[1] = new Line(line2A, line2B);

    }

    private void updateRotation(){
        step += speed;
        generateLines();
    }

    @Override
    public Ball updateEntity(Ball ball) {
        updateRotation();
        return super.updateEntity(ball);
    }

    @Override
    public char getColor() {
        return 'G';
    }
}
