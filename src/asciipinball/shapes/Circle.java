package asciipinball.shapes;

import asciipinball.Coordinate;

public class Circle extends Shapes {
    private float radius;
    private float x;
    private float y;

    public Circle(float x, float y, float radius) {
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    public Circle(Coordinate cor, float radius){
        this(cor.getX(),cor.getY(),radius);
    }

    public float getRadius() {
        return radius;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
