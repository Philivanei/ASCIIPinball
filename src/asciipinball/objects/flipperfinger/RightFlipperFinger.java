package asciipinball.objects.flipperfinger;

import asciipinball.corelogic.players.PlayerManager;
import asciipinball.shapes.Line;

public class RightFlipperFinger extends FlipperFinger {

    public RightFlipperFinger(PlayerManager playerManager, float x, float y, float length, float startAngle, float stopAngle) {
        super(playerManager, x, y, length , startAngle, stopAngle);
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
