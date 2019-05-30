package asciipinball;

import asciipinball.shapes.Shapes;

public class CollisionData implements Comparable<CollisionData>{
    Shapes collisionShape;
    float collisionX;
    float collisionY;
    float distanceToBall;

    public CollisionData(float collisionX, float collisionY, Shapes collisionShape, float distanceToBall) {
        this.collisionShape = collisionShape;
        this.collisionX = collisionX;
        this.collisionY = collisionY;
        this.distanceToBall = distanceToBall;
    }

    public CollisionData(float collisionX, float collisionY, Shapes collisionShape, float distanceToBall, float distanceToLinePointA, float distanceToLinePointB) {
        this.collisionShape = collisionShape;
        this.collisionX = collisionX;
        this.collisionY = collisionY;
        this.distanceToBall = distanceToBall;
    }

    @Override
    public int compareTo(CollisionData o) {
        return Float.compare(distanceToBall, o.distanceToBall);
    }

    public Shapes getCollisionShape() {
        return collisionShape;
    }

    public float getCollisionX() {
        return collisionX;
    }

    public float getCollisionY() {
        return collisionY;
    }

    public float getDistanceToBall() {
        return distanceToBall;
    }
}
