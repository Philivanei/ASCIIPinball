package asciipinball.shapes;

public class Circle extends Shapes {
    private float radius;
    private float x;
    private float y;

    public Circle(float x, float y, float radius) {
        this.radius = radius;
        this.x = x;
        this.y = y;
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
