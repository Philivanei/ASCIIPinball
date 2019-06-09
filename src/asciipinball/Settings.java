package asciipinball;

import java.awt.*;
import java.util.HashMap;

public abstract class Settings {
    public static final float GRAVITATION = 0.00001f;
    public static final int OFFSET = 70;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 135;
    public static final float HOLE_WIDTH = 30;
    public static final float FRICTION = 0.003f;
    public static final float MIN_SPEED = 0.001f;
    public static final float MAX_SPEED = 0.25f;
    public static final float TIME_FOR_JUMP = 200;
    public static final float TILT_Y_OFFSET = 5;
    public static final float BALL_RADIUS = 2.5f;

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
