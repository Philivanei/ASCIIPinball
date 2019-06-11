package asciipinball.objects.flipperfinger;

import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.shapes.Line;

/**
 * Rechter Flipper Finger
 */
public class RightFlipperFinger extends FlipperFinger {

    /**
     * Erzeugt einen FliperFinger auf der rechten Seite
     *
     * @param playerManager PlayerManager des Spiels
     * @param x             x-Koordinate des rechten Flipper Fingers
     * @param y             y-Koordinate des rechten Flipper Fingers
     * @param length        Länge des rechten Flipper Fingers
     * @param minAngle      Legt die MinimalHöhe des rechten FlipperFinger fest.
     * @param maxAngle      Legt die MaximalHöhe des rechten FlipperFinger fest.
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
