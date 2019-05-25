package asciipinball.logic;

import asciipinball.GameView;
import asciipinball.interfaces.Drawable;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Coordinate;
import asciipinball.shapes.Line;

public class Table extends PolygonEntity implements Drawable {

    private Line lines[];

    public Table(float width, float hight) {

        Coordinate topLeft = new Coordinate(0,hight);
        Coordinate topRight = new Coordinate(width, hight);
        Coordinate bottomLeft = new Coordinate(0,0);
        Coordinate bottomRight = new Coordinate(width,0);

        lines = new Line[4];
        lines[0] = new Line(topLeft,topRight);
        lines[1] = new Line(topRight, bottomRight);
        lines[2] = new Line(bottomLeft, bottomRight);
        lines[3] = new Line(topLeft, bottomLeft);

    }

    @Override
    public void addToCanvas(GameView gameView) {

    }

}
