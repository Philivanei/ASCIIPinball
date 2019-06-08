package asciipinball.objects.physicobject.polygonial;

import asciipinball.GameView;
import asciipinball.interfaces.Drawable;
import asciipinball.Coordinate;
import asciipinball.shapes.Line;

public class Table extends PolygonEntity {

    public Table(float width, float height, float holeWidth) {

        Coordinate topLeft = new Coordinate(0,height);
        Coordinate topRight = new Coordinate(width, height);
        Coordinate bottomLeft = new Coordinate(0,1);
        Coordinate bottomRight = new Coordinate(width,1);
        Coordinate holeLeft = new Coordinate(width/2 - holeWidth/2,1);
        Coordinate holeRight = new Coordinate(width/2 + holeWidth/2,1);

        lines = new Line[5];
        lines[0] = new Line(topLeft,topRight);
        lines[1] = new Line(topRight, bottomRight);
        lines[2] = new Line(holeRight, bottomRight);
        lines[3] = new Line(holeLeft, bottomLeft);
        lines[4] = new Line(bottomLeft, topLeft);

    }


    @Override
    public char getColor() {
        return 'B';
    }
}
