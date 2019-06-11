package asciipinball.objects.physicobject.polygonial;

import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.Coordinate;
import asciipinball.shapes.Line;

public class Table extends PolygonEntity {

    public Table(PlayerManager playerManager, float width, float height, float holeWidth) {
        super(playerManager);

        //calculate the coordinates that are necessary for the frame
        Coordinate topLeft = new Coordinate(0,height);
        Coordinate topRight = new Coordinate(width, height);
        Coordinate bottomLeft = new Coordinate(0,0);
        Coordinate bottomRight = new Coordinate(width,0);
        //Coordinate holeLeft = new Coordinate(width/2 - holeWidth/2,1);
        //Coordinate holeRight = new Coordinate(width/2 + holeWidth/2,1);

        //generate the frame
        lines = new Line[3];
        lines[0] = new Line(topLeft,topRight);
        lines[1] = new Line(topRight, bottomRight);
        //lines[2] = new Line(holeRight, bottomRight);
        //lines[3] = new Line(holeLeft, bottomLeft);
        lines[2] = new Line(bottomLeft, topLeft);

    }


    @Override
    public char getColor() {
        return 'B';
    }
}
