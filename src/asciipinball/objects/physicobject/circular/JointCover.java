package asciipinball.objects.physicobject.circular;

import asciipinball.Coordinate;
import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.exceptions.NotConnectedLinesException;
import asciipinball.objects.Ball;
import asciipinball.shapes.Circle;
import asciipinball.shapes.Line;

/**
 * Abdeckung für die Enden von Linien
 */
public class JointCover extends CircleEntity {

    /**
     * Erstellt ein JointCover
     * @param playerManager playerManager des Spiels
     * @param coordinate Koordinate des JointCovers
     */
    public JointCover(PlayerManager playerManager, Coordinate coordinate) {
        super(playerManager);
        circles = new Circle[1];
        circles[0] = new Circle(coordinate,1f);
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        Ball returnBall = super.interactWithBall(ball);
        returnBall.addVelocity(0.01f);
        return returnBall;
    }

    @Override
    public char getColor() {
        return ' ';
    }
}
