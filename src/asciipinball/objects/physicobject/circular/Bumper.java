package asciipinball.objects.physicobject.circular;

import asciipinball.Coordinate;
import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.objects.Ball;
import asciipinball.shapes.Circle;

public class Bumper extends CircleEntity {

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
    public char getColor() {
        return 'R';
    }
}
