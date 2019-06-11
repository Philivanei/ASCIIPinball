package asciipinball.objects.flipperfinger;

import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.shapes.Line;

public class LeftFlipperFinger extends FlipperFinger {


    public LeftFlipperFinger(PlayerManager playerManager, float x, float y, float length, float minAngle, float maxAngle) {
        super(playerManager, x, y, length , minAngle, maxAngle);
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
