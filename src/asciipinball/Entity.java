package asciipinball;

public abstract class Entity {
    protected int positionX;
    protected int positionY;

    public abstract void interactWithBall(Ball ball);
    public abstract void drawToCanvas(GameView gameView, int offset , int gameTableHight);

}
