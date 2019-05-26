package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.shapes.Line;

public class PinballGame {
    public static final float GRAVITATION = (float) 0.3;
    public static final int OFFSET = 10;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 135;

    public static GameView gameView;

    //TODO IMPORTANT: Max Koordinate is WIDTH/HEIGHT -1


    private Ball[] balls;
    private Player[] players;
    private Table table;

    public PinballGame() {

        /** Init asciipinball.asciipinball.asciipinball.GameView **/
        gameView = new GameView(HEIGHT, 240, "Pinball");
        gameView.setWindowsSize(GameView.WINDOWSIZE_LARGE);
        gameView.show();


        /**Init Arrays and Values**/
        balls = new Ball[4];
        players = new Player[4];
        table = new Table(WIDTH, HEIGHT);

        //TODO
        /**DEBUG STUFF REMOVE BEFORE RELEASE!**/
        Line testLine = new Line(0,0,20,150);
        //Line testLine = new Line(20,3,33,122);
        //Line testLine = new Line(20,122,25,3);
        testLine.addToCanvas(gameView);
        gameView.printCanvas();



    }
}
