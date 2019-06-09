package asciipinball.objects.physicobject.polygonial;

import asciipinball.corelogic.players.PlayerManager;
import asciipinball.shapes.Line;

public class LineWall extends PolygonEntity{

    public LineWall(PlayerManager playerManager, float x1, float y1, float x2, float y2){
        super(playerManager);
        score = 1;
        lines = new Line[1];
        lines[0] = new Line(x1,y1,x2,y2);
    }


    @Override
    public char getColor() {
        return 'B';
    }
}
