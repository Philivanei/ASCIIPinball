package asciipinball.shapes;

public class Rectangle {

    private Line[] lines;

    //angle always in radians!!!
    //ground point coordinates are x1 and y1
    //creates a rectangle with defined angle
    public Rectangle(float angle, float x1, float y1, float x2, float y2) {

        lines = new Line[4];

        if (angle != 0) {
            createLines(angle, x1, y1, x2, y2);
        } else {
            //coordinates for the case that the rectangle is horizontal (angle = 0)
            lines[0] = new Line(x1, y1, x2, y1);
            lines[1] = new Line(x2, y1, x2, y2);
            lines[2] = new Line(x2, y2, x1, y2);
            lines[3] = new Line(x1, y2, x1, y1);
        }
    }

    //if case für rechteck wenn der winkel 0 ist !!! dann ist rechteck waagrecht

    //returns the length of the rectangle
    public float calculateLength(float angle, float x1, float y1, float x2, float y2) {

        float angleInRectangle = ((float) Math.atan((y2 - y1) / (x2 - x1))) - angle;
        float hypotenuse = (x2 - x1) / ((float) Math.cos(angleInRectangle + angle));
        return ((float) Math.cos(angleInRectangle)) * hypotenuse;
    }

    //returns the width of the rectangle
    public float calculateWidth(float angle, float x1, float y1, float x2, float y2) {

        float angleInRectangle = ((float) Math.atan((y2 - y1) / (x2 - x1))) - angle;
        float hypotenuse = (x2 - x1) / ((float) Math.cos(angleInRectangle + angle));
        return ((float) Math.sin(angleInRectangle)) * hypotenuse;
    }

    //calculates the unknown coordinates of the rectangle and creates the lines
    public void createLines(float angle, float x1, float y1, float x2, float y2) {
        //repetitiven code entfernen !!! und die funktionen oben benutzen!!!
        float angleInRectangle = ((float) Math.atan((y2 - y1) / (x2 - x1))) - angle;
        float hypotenuse = (x2 - x1) / ((float) Math.cos(angleInRectangle + angle));
        float lengthToCalculatePoint = ((float) Math.cos(angleInRectangle + angle) * hypotenuse);
        float x3 = x1 + lengthToCalculatePoint;

        float widthToCalculatePoint = ((float) Math.sin(angleInRectangle + angle) * hypotenuse);
        float y3 = y1 + widthToCalculatePoint;

        float angleToCalculatePoint;
        angleToCalculatePoint = ((float) Math.toRadians(180)) - ((((float) Math.toRadians(90))
                - angleInRectangle) + angle);
        lengthToCalculatePoint = ((float) Math.cos(angleToCalculatePoint) * calculateWidth(angle, x1, y1, x2, y2));
        widthToCalculatePoint = ((float) Math.sin(angleToCalculatePoint) * calculateWidth(angle, x1, y1, x2, y2));
        float x4 = x1 - lengthToCalculatePoint;
        float y4 = y1 + widthToCalculatePoint;

        lines[0] = new Line(x1, y1, x3, y3);
        lines[1] = new Line(x3, y3, x2, y2);
        lines[2] = new Line(x2, y2, x4, y4);
        lines[3] = new Line(x4, y4, x1, y1);
    }
}
