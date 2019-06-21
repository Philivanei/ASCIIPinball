package asciipinball;

/**
 * Speichert Kollisions daten
 */
public class CollisionData<T> implements Comparable<CollisionData> {
    private T collisionShape;
    private float collisionX;
    private float collisionY;
    private float distanceToBall;

    /**
     * Erstellt einen neuen KollisionsDaten Block
     * @param collisionX X Koordinate der Kollision
     * @param collisionY Y Koordinate der Kollision
     * @param collisionShape Entity mit der Kollidiert wurde
     * @param distanceToBall Abstand vom Kollisionspunkt zum Ball
     */
    public CollisionData(float collisionX, float collisionY, T collisionShape, float distanceToBall) {
        this.collisionShape = collisionShape;
        this.collisionX = collisionX;
        this.collisionY = collisionY;
        this.distanceToBall = distanceToBall;
    }

    /**
     * Vergleicht die Abstände zwischen Ball und Objekt der Objekte
     * @param o objekt mit dem verglichen wird
     * @return größer/kleiner/gleich codiert als int (1/-1/0)
     */
    @Override
    public int compareTo(CollisionData o) {
        return Float.compare(distanceToBall, o.distanceToBall);
    }

    /**
     * Gibt die Kollision Shape zurück
     * @return Shape mit der Kolliert wurde
     */
    public T getCollisionShape() {
        return collisionShape;
    }

    /**
     * Gibt die X Koordinate des Kollisionspunktes zurück
     * @return X Koordinate des Kollisionspunktes
     */
    public float getCollisionX() {
        return collisionX;
    }

    /**
     * Gibt die Y Koordinate des Kollisionspunktes zurück
     * @return Y Koordinate des Kollisionspunktes
     */
    public float getCollisionY() {
        return collisionY;
    }

    /**
     * Gibt den Abstand zwischen Ball und Kollisionspunkt zurück
     * @return Abstand zwischen Balll und Kollisionspunkt
     */
    public float getDistanceToBall() {
        return distanceToBall;
    }
}
