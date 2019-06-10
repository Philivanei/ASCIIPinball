package asciipinball;

import java.awt.*;
import java.util.HashMap;

public abstract class Settings {
    public static final float GRAVITATION = 0.00001f;
    public static final int OFFSET = 70;
    public static final int WIDTH = 100;
    public static final int GAMEVIEW_WIDTH = 240;
    public static final int HEIGHT = 135;
    public static final float HOLE_WIDTH = 30;
    public static final float FRICTION = 0.003f;
    public static final float MIN_SPEED = 0.001f;
    public static final float MAX_SPEED = 0.25f;
    public static final float TIME_FOR_JUMP = 200;
    public static final float TILT_Y_OFFSET = 5;
    public static final float BALL_RADIUS = 2.5f;
    public static final float MAX_LAUNCH_PRESS_TIME = 1000;
    public static final float MIN_LAUNCH_VELOCITY = 0.01f;
    public static final float MAX_LAUNCH_VELOCITY = 0.08f;
    public static final int PLAYER_POSITION_X = HEIGHT / 2 + 10;
    //please configure if the steps between the players have been changed
    public static final int PLAYER_DISTANCE = 10;
    public static final int PLAYER1_POSITION_Y = 60;
    public static final int PLAYER2_POSITION_Y = 70;
    public static final int PLAYER3_POSITION_Y = 80;
    public static final int PLAYER4_POSITION_Y = 90;


    public static void initColorMap(GameView gameView) {
        HashMap<Character, Color> colormap = new HashMap<>();
        colormap.put('R', Color.RED);
        colormap.put('r', Color.RED.brighter());
        colormap.put('G', Color.GREEN);
        colormap.put('g', Color.GREEN.brighter());
        colormap.put('B', Color.BLUE);
        colormap.put('b', Color.BLUE.brighter());
        colormap.put('Y', Color.YELLOW);
        colormap.put('P', Color.PINK);
        colormap.put('C', Color.CYAN);
        colormap.put('M', Color.MAGENTA);
        colormap.put('O', Color.ORANGE);
        colormap.put('W', Color.WHITE);

        gameView.setColormap(colormap);
    }
}
