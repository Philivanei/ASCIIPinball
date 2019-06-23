package asciipinball;

/**
 * Eine Koordinate
 */
public class Coordinate {
    private float x;
    private float y;

    /**
     * Erzeugt eine Koordinate
     *
     * @param x x-Koordinate
     * @param y y-Koordinate
     */
    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gibt die x-Koordinate zurück
     *
     * @return x-Koordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Gibt die y-Koordinate zurück
     *
     * @return y-Koordinate
     */
    public float getY() {
        return y;
    }
}
