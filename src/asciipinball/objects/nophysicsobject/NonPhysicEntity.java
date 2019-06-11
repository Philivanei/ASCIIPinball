package asciipinball.objects.nophysicsobject;

import asciipinball.interfaces.Drawable;
import asciipinball.objects.Ball;
import asciipinball.shapes.Circle;

/**
 * Abstrakte Oberklasse für Entities die über keine Kollisionsabfrage verfügen
 */
public abstract class NonPhysicEntity implements Drawable {

    protected Circle[] circles;

    /**
     *
     * @param ball Ball mit dem interagiert werden soll
     */
    public abstract void updateEntity(Ball ball);

    /**
     * Gibt alle Kreise zurück aus der die Entity besteht
     * @return Alle Kreise aus der die Entity besteht
     */
    public Circle[] getCircles(){
        return circles;
    }

}
