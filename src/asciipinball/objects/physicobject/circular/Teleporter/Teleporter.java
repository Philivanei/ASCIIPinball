package asciipinball.objects.physicobject.circular.Teleporter;

import asciipinball.Coordinate;
import asciipinball.Settings;
import asciipinball.objects.Ball;
import asciipinball.objects.physicobject.circular.CircleEntity;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.shapes.Circle;

public class Teleporter extends CircleEntity {

    private float x;
    private float y;
    private Teleporter destinationTeleporter;
    private static long deadTimeStart = 0; //Has to be static so all instances share the same timer

    //package private so only TeleporterFactory can instantiate a new Teleporter
    Teleporter(PlayerManager playerManager, Coordinate coordinate){
        super(playerManager);
        x = coordinate.getX();
        y = coordinate.getY();
        circles = new Circle[1];
        circles[0] = new Circle(coordinate, 2.5f);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    //Package private so only the TeleporterFactory can addLinks
    void addLink(Teleporter teleporter){
        destinationTeleporter = teleporter;
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        if((System.currentTimeMillis() - deadTimeStart) > Settings.TELEPORTER_DEAD_TIME){
            deadTimeStart = System.currentTimeMillis();
            return new Ball(destinationTeleporter.getX(), destinationTeleporter.getY(), ball.getRadius(), ball.getDirection(), ball.getVelocity());
        }
        return null;
    }

    @Override
    public char getColor() {
        return 'C';
    }
}
