package asciipinball.objects.flipperfinger;

import asciipinball.playersandscore.PlayerManager;
import asciipinball.shapes.Line;

/**
 * Rechter Flipperfinger
 */
public class RightFlipperFinger extends FlipperFinger {

    /**
     * Erzeugt einen Flipperfinger auf der rechten Seite
     *
     * @param playerManager PlayerManager des Spiels
     * @param x             x-Koordinate des rechten Flipperfingers
     * @param y             y-Koordinate des rechten Flipperfingers
     * @param length        Länge des rechten Flipperfingers
     * @param minAngle      Legt die Minimalhöhe des rechten Flipperfinger fest
     * @param maxAngle      Legt die Maximalhöhe des rechten Flipperfinger fest
     */
    public RightFlipperFinger(PlayerManager playerManager, float x, float y, float length, float minAngle, float maxAngle) {
        super(playerManager, x, y, length, minAngle, maxAngle);
    }

    @Override
    public void generateLine(float angle) {
        float adjacentSide = ((float) Math.cos(Math.toRadians(angle))) * length;
        float oppositeSide = ((float) Math.sin(Math.toRadians(angle))) * length;
        float xRes = x - oppositeSide;
        float yRes = y - adjacentSide;
        lines[0] = new Line(x, y, xRes, yRes);
    }

    @Override
    public char getColor() {
        return 'B';
    }
}
