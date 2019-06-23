package asciipinball;

/**
 * Speichert Kollisionsdaten
 */
public class CollisionData<T> implements Comparable<CollisionData> {
    private T collisionShape;
    private float collisionX;
    private float collisionY;
    private float distanceToBall;

    /**
     * Erstellt einen neuen Kollisionsdatenblock
     *
     * @param collisionX     x-Koordinate der Kollision
     * @param collisionY     y-Koordinate der Kollision
     * @param collisionShape Entity mit der kollidiert wurde
     * @param distanceToBall Abstand vom Kollisionspunkt zum Ball
     */
    public CollisionData(float collisionX, float collisionY, T collisionShape, float distanceToBall) {
        this.collisionShape = collisionShape;
        this.collisionX = collisionX;
        this.collisionY = collisionY;
        this.distanceToBall = distanceToBall;
    }

    /**
     * Vergleicht die Abstände zwischen Ball und Objekt
     *
     * @param o objekt mit dem verglichen wird
     * @return größer/kleiner/gleich kodiert als int (1/-1/0)
     */
    @Override
    public int compareTo(CollisionData o) {
        return Float.compare(distanceToBall, o.distanceToBall);
    }

    /**
     * Gibt die Form (Shape) des Objektes mit dem kollidiert wurde zurück
     *
     * @return Shape mit der kollidiert wurde
     */
    public T getCollisionShape() {
        return collisionShape;
    }

    /**
     * Gibt die x-Koordinate des Kollisionspunkts zurück
     *
     * @return x-Koordinate des Kollisionspunkts
     */
    public float getCollisionX() {
        return collisionX;
    }

    /**
     * Gibt die y-Koordinate des Kollisionspunkts zurück
     *
     * @return y-Koordinate des Kollisionspunkts
     */
    public float getCollisionY() {
        return collisionY;
    }

    /**
     * Gibt den Abstand zwischen Ball und Kollisionspunkt zurück
     *
     * @return Abstand zwischen Ball und Kollisionspunkt
     */
    public float getDistanceToBall() {
        return distanceToBall;
    }
}
