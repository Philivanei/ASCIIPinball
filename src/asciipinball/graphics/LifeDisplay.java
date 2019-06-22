package asciipinball.graphics;

import asciipinball.Coordinate;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.interfaces.Drawable;
import asciipinball.shapes.Circle;

/**
 * Eine Anzeige wie viele Bälle ein Spieler noch übrig hat
 */
public class LifeDisplay implements Drawable {
    private PlayerManager playerManager;
    private float x,y,radius;

    /**
     * Erstellt ein neues LifeDisplay
     * @param playerManager PlayerManager des Spiels
     * @param coordinate Koordinate an der das LifeDisplay ausgegeben werden soll
     * @param radius Radius der Lebensanzeigen-Bälle
     */
    public LifeDisplay(PlayerManager playerManager, Coordinate coordinate, float radius){
        this.playerManager = playerManager;
        x = coordinate.getX();
        y = coordinate.getY();
        this.radius = radius;
    }

    /**
     * Generiert Kreise abhängig von den verbleibenden Leben/Bällen des aktuellen Spielers
     * @return Lebensindikationskreise
     */
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
