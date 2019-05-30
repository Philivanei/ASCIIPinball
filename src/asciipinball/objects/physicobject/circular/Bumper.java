package asciipinball.objects.physicobject.circular;

import asciipinball.GameView;
import asciipinball.Settings;
import asciipinball.logic.Ball;
import asciipinball.shapes.Circle;

public class Bumper extends CircleEntity {

    public Bumper(float x, float y, float radius){
        score = 1;
        circles = new Circle[1];
        circles[0] = new Circle(x,y,radius);
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        Ball unmodifiedBall = super.interactWithBall(ball);
        return new Ball(unmodifiedBall.getPositionX(),unmodifiedBall.getPositionY(), unmodifiedBall.getRadius(), unmodifiedBall.getDirection() ,unmodifiedBall.getVelocity() + 0.025f);
    }

    @Override
    public void addToCanvas(GameView gameView) {

        float radius = circles[0].getRadius();
        float x = circles[0].getX();
        float y = circles[0].getY();

        int diameter = Math.round(radius * 2);

        char[][] canvasSegment = new char[diameter][diameter];

        for(int column = 0; column < canvasSegment[0].length; column++){
            for(int row = 0; row < canvasSegment.length; row++) {
                float distanceCanvasMiddle = (float) Math.sqrt(Math.pow(((radius - 0.5) - column),2) + Math.pow(((radius - 0.5) - row),2));
                //canvasSegment[row][column] = (distanceCanvasMiddle < radius) ? 'B' : ' ';
                if(distanceCanvasMiddle <= radius){
                    canvasSegment[row][column] = 'R';
                }
                else{
                    canvasSegment[row][column] = ' ';
                }
            }
        }

        int canvasRow = Settings.HEIGHT - Math.round(y + radius);
        int canvasColumn = Math.round(x - radius) + Settings.OFFSET;

        gameView.addColorStringToCanvas(canvasSegment,canvasRow,canvasColumn);
    }
}
