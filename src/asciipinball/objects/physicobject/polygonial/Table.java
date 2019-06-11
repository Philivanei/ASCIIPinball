package asciipinball.objects.physicobject.polygonial;

import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.Coordinate;
import asciipinball.shapes.Line;

/**
 * Der Tisch der die Grenzen des Spielbereiches darstellt
 */
public class Table extends PolygonEntity {

    /**
     * Erstellt einen Tisch
     * @param playerManager playerManager des Spiels
     * @param width Breite des Tisches
     * @param height Höhe des Tisches
     */
    public Table(PlayerManager playerManager, float width, float height) {
        super(playerManager);

        //calculate the coordinates that are necessary for the frame
        Coordinate topLeft = new Coordinate(0,height);
        Coordinate topRight = new Coordinate(width, height);
        Coordinate bottomLeft = new Coordinate(0,0);
        Coordinate bottomRight = new Coordinate(width,0);

        //generate the frame
        lines = new Line[3];
        lines[0] = new Line(topLeft,topRight);
        lines[1] = new Line(topRight, bottomRight);
        lines[2] = new Line(bottomLeft, topLeft);

    }


    @Override
    public char getColor() {
        return 'B';
    }
}
