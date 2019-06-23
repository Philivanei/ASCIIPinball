package asciipinball.shapes;

import asciipinball.Coordinate;

/**
 * Eine Linie
 */
public class Line {
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    /**
     * Erstellt eine Linie
     *
     * @param x1 x-Koordinate des ersten Punkts
     * @param y1 y-Koordinate des ersten Punkts
     * @param x2 x-Koordinate des zweiten Punkts
     * @param y2 y-Koordinate des zweiten Punkts
     */
    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Erstellt eine Linie aus zwei Koordinaten
     *
     * @param p1 Koordinate 1
     * @param p2 Koordinate 2
     */
    public Line(Coordinate p1, Coordinate p2) {
        this(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     * Gibt die x-Koordinate des ersten Punkts zurück
     *
     * @return x-Koordinate des ersten Punkts
     */
    public float getX1() {
        return x1;
    }

    /**
     * Gibt die y-Koordinate des ersten Punkts zurück
     *
     * @return y-Koordinate des ersten Punkts
     */
    public float getY1() {
        return y1;
    }

    /**
     * Gibt die x-Koordinate des zweiten Punkts zurück
     *
     * @return x-Koordinate des zweiten Punkts
     */
    public float getX2() {
        return x2;
    }

    /**
     * Gibt die y-Koordinate des zweiten Punkts zurück
     *
     * @return y-Koordinate des zweiten Punkts
     */
    public float getY2() {
        return y2;
    }

    /**
     * Gibt die Länge der Linie zurück
     *
     * @return Länge der Linie
     */
    public float getLength() {
        return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    //formula:
    // y = m*x+b -> y = getM()*x+getB()

    /**
     * Gibt die Steigung der Linie zurück.
     *
     * @return Steigung der Linie
     */
    public float getM() {
        if (x2 == x1) {
            return Float.POSITIVE_INFINITY;
        }
        return ((y2 - y1) / (x2 - x1));
    } //Slope

    /**
     * Gibt die Verschiebung entlang der y-Achse der Geraden der Linie zurück.
     *
     * @return Verschiebung entlang der y-Achse der Geraden der Linie
     */
    public float getB() {
        return (y1 - getM() * x1);
    } //Shift

}
