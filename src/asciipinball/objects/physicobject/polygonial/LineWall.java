package asciipinball.objects.physicobject.polygonial;

import asciipinball.Coordinate;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.interfaces.Coverable;
import asciipinball.shapes.Line;

/**
 * Eine wand die lediglich aus einer Linie besteht
 */
public class LineWall extends PolygonEntity implements Coverable {

    /**
     * Erstellt eine Linien Wand
     * @param playerManager playerManager des Spiels
     * @param a Koordinaten des ersten Punktes der die Linie beschreibt
     * @param b Koordinaten des zweiten Punktes der die Linie beschreibt
     */
    public LineWall(PlayerManager playerManager, Coordinate a, Coordinate b){
        super(playerManager);
        score = 0;
        lines = new Line[1];
        lines[0] = new Line(a,b);
    }

    @Override
    public char getColor() {
        return 'B';
    }

    @Override
    public Line[] getAllLines() {
        return lines;
    }
}
