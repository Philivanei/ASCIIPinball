package asciipinball.graphics;

import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.interfaces.Drawable;
import asciipinball.shapes.Circle;

public class LifeDisplay implements Drawable {
    private PlayerManager playerManager;
    private float x,y,radius;

    public LifeDisplay(PlayerManager playerManager, float x, float y, float radius){
        this.playerManager = playerManager;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public Circle[] getDisplay(){

        Circle[] returnCircles = new Circle[playerManager.getCurrentPlayer().getBallLifes()];

        for (int i = 0; i < returnCircles.length; i++ ) {
            returnCircles[i] = new Circle(x + i*(2 * radius + 1) ,y,radius);
        }

        return returnCircles;
    }


    @Override
    public char getColor() {
        return 'Y';
    }
}
