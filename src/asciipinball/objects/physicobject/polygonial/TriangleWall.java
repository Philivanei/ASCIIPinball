package asciipinball.objects.physicobject.polygonial;

import asciipinball.Coordinate;
import asciipinball.GameView;
import asciipinball.shapes.Line;

public class TriangleWall extends PolygonEntity {

    public TriangleWall(float x1, float y1,float x2, float y2,float x3, float y3){

        lines = new Line[3];

        Coordinate a = new Coordinate(x1,y1);
        Coordinate b = new Coordinate(x2,y2);
        Coordinate c = new Coordinate(x3,y3);

        lines[0] = new Line(a,b);
        lines[1] = new Line(b,c);
        lines[2] = new Line(c,a);
    }

    @Override
    public char getColor() {
        return 'B';
    }
}
