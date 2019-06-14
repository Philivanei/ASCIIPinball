package asciipinball.objects.physicobject.circular;

import asciipinball.Coordinate;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.objects.Ball;
import asciipinball.shapes.Circle;

/**
 * Ein Bumper der den Ball von sich abstößt
 */
public class Bumper extends CircleEntity {

    /**
     * Erstellt einen Bumper
     * @param playerManager playerManager des Spiels
     * @param coordinate Koordinate des Mittelpunkts des Bumpers
     * @param radius Radius des Bumpers
     */
    public Bumper(PlayerManager playerManager, Coordinate coordinate, float radius){
        super(playerManager);
        score = 150;
        circles = new Circle[1];
        circles[0] = new Circle(coordinate,radius);
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        Ball unmodifiedBall = super.interactWithBall(ball);
        unmodifiedBall.addVelocity(0.025f);
        return unmodifiedBall;
    }

    @Override
    protected int getCollisionSoundId() {
        return 1;
    }

    @Override
    public char getColor() {
        return 'R';
    }
}
