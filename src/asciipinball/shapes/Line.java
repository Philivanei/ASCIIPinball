package asciipinball.shapes;

public class Line {
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public float getX1() {
        return x1;
    }

    public float getY1() { return y1; }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public float getLength() {return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));}

    // y = m*x+b -> y = getM()*x+getB()
    public float getM(){
        if(x2 == x1){
            return Float.POSITIVE_INFINITY;
        }
        return ((y2 - y1) / (x2 - x1));
    } //Slope

    public float getB(){return (y1 - getM() * x1);} //Shift
}
