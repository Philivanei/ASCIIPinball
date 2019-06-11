package asciipinball.objects.flipperfinger;

import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.shapes.Line;

/**
 * Linker Flipper Finger
 */
public class LeftFlipperFinger extends FlipperFinger {

    /**
     * Erzeugt einen FliperFinger auf der linken Seite
     *
     * @param playerManager PlayerManager des Spiels
     * @param x             x-Koordinate des linken Flipper Fingers
     * @param y             y-Koordinate des linken Flipper Fingers
     * @param length        Länge des linken Flipper Fingers
     * @param minAngle      Legt die MinimalHöhe der FlipperFinger fest.
     * @param maxAngle      Legt die MaximalHöhe der FlipperFinger fest.
     */
    public LeftFlipperFinger(PlayerManager playerManager, float x, float y, float length, float minAngle, float maxAngle) {
        super(playerManager, x, y, length, minAngle, maxAngle);
    }


    @Override
    public void generateLine(float angle) {
        float adjacentSide = ((float) Math.cos(Math.toRadians(angle))) * length;
        float oppositeSide = ((float) Math.sin(Math.toRadians(angle))) * length;
        float xRes = oppositeSide + x;
        float yRes = y - adjacentSide;
        lines[0] = new Line(x, y, xRes, yRes);
    }

    @Override
    public char getColor() {
        return 'B';
    }
}
