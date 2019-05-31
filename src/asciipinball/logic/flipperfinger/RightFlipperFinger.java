package asciipinball.logic.flipperfinger;

import asciipinball.GameView;
import asciipinball.shapes.Line;

public class RightFlipperFinger extends FlipperFinger {

    public RightFlipperFinger(float x, float y,  float length, float startAngle, float stopAngle) {
        super(x, y, length , startAngle, stopAngle);
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
    public void addToCanvas(GameView gameView) {
        lines[0].addToCanvas(gameView);
    }
}
