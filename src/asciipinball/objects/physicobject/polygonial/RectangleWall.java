package asciipinball.objects.physicobject.polygonial;

import asciipinball.GameView;
import asciipinball.shapes.Rectangle;

public class RectangleWall extends PolygonEntity {
    Rectangle rectangle;

    public RectangleWall(float x1, float y1, float x2, float y2, float angle){
        rectangle = new Rectangle(x1,y1,x2,y2,angle);
    }
    @Override
    public void addToCanvas(GameView gameView) {
        rectangle.addToCanvas(gameView);
    }
}
