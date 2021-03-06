package asciipinball.shapes;

import asciipinball.Coordinate;

/**
 * Ein Kreis
 */
public class Circle {
    private float radius;
    private float x;
    private float y;

    /**
     * Erstellt einen Kreis
     *
     * @param x      x-Koordinate
     * @param y      y-Koordinate
     * @param radius Radius
     */
    public Circle(float x, float y, float radius) {
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    /**
     * Erstellt einen Kreis
     *
     * @param cor    Koordinate
     * @param radius Radius
     */
    public Circle(Coordinate cor, float radius) {
        this(cor.getX(), cor.getY(), radius);
    }

    /**
     * Gibt den Radius des Kreises zurück
     *
     * @return Radius des Kreises
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Gibt die x-Koordinate des Kreises zurück
     *
     * @return x-Koordinate des Kreises
     */
    public float getX() {
        return x;
    }

    /**
     * Gibt die y-Koordinate des Kreises zurück
     *
     * @return y-Koordinate des Kreises
     */
    public float getY() {
        return y;
    }
}
