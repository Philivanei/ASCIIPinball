package asciipinball.shapes;

public class Rectangle {

    private Line[] lines;

    //angle always in radians!!!
    //ground point coordinates are x1 and y1 and of the upper edge x2 and y2

    //creates a rectangle with defined angle
    public Rectangle(float angle, float x1, float y1, float x2, float y2) {

        lines = new Line[4];

        if (angle != 0) {
            initializeLines(angle, x1, y1, x2, y2);
        } else {
            //coordinates for the case that the rectangle is horizontal (angle = 0)
            lines[0] = new Line(x1, y1, x2, y1);
            lines[1] = new Line(x2, y1, x2, y2);
            lines[2] = new Line(x2, y2, x1, y2);
            lines[3] = new Line(x1, y2, x1, y1);
        }
    }

    //returns the hypotenus of the triangle in the Rectangle
    public float calculateHypotenuse(float x1, float y1, float x2, float y2) {
        float angleInRectanglePlusAngle = ((float) Math.atan((y2 - y1) / (x2 - x1)));
        return ((x2 - x1) / ((float) Math.cos(angleInRectanglePlusAngle)));
    }

    //returns the lower angle from the triangle in the rectangle
    public float calculateAngleInRectangle(float angle, float x1, float y1, float x2, float y2) {
        return (((float) Math.atan((y2 - y1) / (x2 - x1))) - angle);
    }

    //returns the length of the rectangle
    public float calculateLength(float angle, float x1, float y1, float x2, float y2) {
        return (((float) Math.cos(calculateAngleInRectangle(angle, x1, y1, x2, y2)))
                * calculateHypotenuse(x1, y1, x2, y2));
    }

    //returns the width of the rectangle
    public float calculateWidth(float angle, float x1, float y1, float x2, float y2) {
        return (((float) Math.sin(calculateAngleInRectangle(angle, x1, y1, x2, y2)))
                * calculateHypotenuse(x1, y1, x2, y2));
    }

    //calculates the unknown coordinates of the rectangle and creates the lines
    public void initializeLines(float angle, float x1, float y1, float x2, float y2) {

        //calculating the x3 coordinate
        //lengthPointRight is the basic length of the right triangle beneath the rectangle
        float lengthPointRight = (((float) Math.cos(angle)) * calculateLength(angle, x1, y1, x2, y2));
        float x3 = x1 + lengthPointRight;

        //calculating the y3 coordinate
        //heightPointRight is the height of the right triangle beneath the rectangle
        float heightPointRight = (((float) Math.sin(angle)) * calculateLength(angle, x1, y1, x2, y2));
        float y3 = y1 + heightPointRight;

        //oppositeAngle is the angle on the left side from the rectangle and it
        //calculates the angle from the bottom to the "width angle" of the triangle
        float oppositeAngle = ((float) Math.toRadians(180)) - (((float) Math.toRadians(90)) + angle);

        //calculating the x4 coordinate
        //lengthPointLeft is the basic length of the left triangle beneath the rectangle
        float lengthPointLeft = (((float) Math.cos(oppositeAngle)) * calculateWidth(angle, x1, y1, x2, y2));
        float x4 = x1 - lengthPointLeft;

        //calculating the y4 coordinate
        //heightPointLeft is the height of the left triangle beneath the rectangle
        float heightPointLeft = (((float) Math.sin(oppositeAngle)) * calculateWidth(angle, x1, y1, x2, y2));
        float y4 = y1 + heightPointLeft;

        //creating lines
        lines[0] = new Line(x1, y1, x3, y3);
        lines[1] = new Line(x3, y3, x2, y2);
        lines[2] = new Line(x2, y2, x4, y4);
        lines[3] = new Line(x4, y4, x1, y1);
    }
}
