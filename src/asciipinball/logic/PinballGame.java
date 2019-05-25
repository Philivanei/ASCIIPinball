package asciipinball.logic;

import asciipinball.GameView;

public class PinballGame {
    public static final float GRAVITATION = (float) 0.3;
    public static final float OFFSET = 0;

    private int width;
    private int hight;

    private GameView gameView;
    private Ball[] balls;
    private Player[] players;
    private Table table;

    public PinballGame(int width, int hight) {

        /** Init asciipinball.asciipinball.asciipinball.GameView **/
        gameView = new GameView(30, 30, "Pinball");
        gameView.setWindowsSize(GameView.WINDOWSIZE_LARGE);
        gameView.show();


        /**Init Arrays and Values**/
        balls = new Ball[4];
        players = new Player[4];


        this.width = width;
        this.hight = hight;
    }

}
