package asciipinball;

import view.GameView;

import java.awt.*;
import java.util.HashMap;

/**
 * Einstellungen nach Immutable Designpattern
 */
public final class Settings {
    /**
     * Gravitation die pro Tick wirken soll
     */
    public static final float GRAVITATION = 0.00001f;
    /**
     * Abstand in Spalten zwischen linken Bildschirmrand und Anfang des Spielbereiches
     */
    public static final int OFFSET = 70;
    /**
     * Breite des Spieltisches
     */
    public static final int WIDTH = 100;
    /**
     * Breite des Fensters
     */
    public static final int GAME_VIEW_WIDTH = 240;
    /**
     * Höhe des Spieltisches & des Fensters
     */
    public static final int HEIGHT = 135;
    /**
     * Abstand der Flipperfinger
     */
    public static final float HOLE_WIDTH = 30;
    /**
     * Reibung die pro Tick wirken soll (Geschwindigkeit die pro tick vom Ball abgezogen wird)
     */
    public static final float FRICTION = 0.003f;
    /**
     * Mindest Geschwindigkeit des Balls
     */
    public static final float MIN_SPEED = 0.001f;
    /**
     * Maximal Geschwindigkeit des Balls
     */
    public static final float MAX_SPEED = 0.25f;
    /**
     * Minimalgeschwindigkeit um einen Kollisionssound abzuspielen
     */
    public static final float MUTE_SPEED = 0.002f;
    /**
     * Zeit während welcher nach einem Teleport nicht noch ein Teleport durgeführt werden kann
     */
    public static final long TELEPORTER_DEAD_TIME = 200;
    /**
     * Zeit die die Abschussrampe benötigt, um nach oben zu Fahren
     */
    public static final float TIME_FOR_JUMP = 200;
    /**
     * Radius des Balls
     */
    public static final float BALL_RADIUS = 2.5f;
    /**
     * Zeit in Millisekunden, die vergehen muss bis ein neue Sound abgespielt werden kann
     */
    public static final float DEAF_TIME = 75;
    /**
     * Zeit in Millisekunden, die den maximalen Abstoß in LaunchControl liefert
     */
    public static final float MAX_LAUNCH_PRESS_TIME = 1000;
    /**
     * Minimalgeschwindigkeit, die der Ball am Anfang beim Abstoß bekommen kann
     */
    public static final float MIN_LAUNCH_VELOCITY = 0.025f;
    /**
     * Maximalgeschwindigkeit, die der Ball am Anfang beim Abstoß bekommen kann
     */
    public static final float MAX_LAUNCH_VELOCITY = 0.09f;


    /**
     * Initialisiert die ColorMap der gameView mit Custom color Codes
     *
     * @param gameView
     */
    public static void initColorMap(GameView gameView) {
        HashMap<Character, Color> colormap = new HashMap<>();
        colormap.put('R', Color.RED);
        colormap.put('r', Color.RED.brighter());
        colormap.put('G', Color.GREEN);
        colormap.put('g', Color.GREEN.brighter());
        colormap.put('B', Color.BLUE);
        colormap.put('b', new Color(102,51,0));
        colormap.put('Y', Color.YELLOW);
        colormap.put('P', Color.PINK);
        colormap.put('C', Color.CYAN);
        colormap.put('M', Color.MAGENTA);
        colormap.put('O', Color.ORANGE);
        colormap.put('W', Color.WHITE);
        colormap.put('S', Color.LIGHT_GRAY);

        gameView.setColormap(colormap);
    }
}
