package asciipinball.objects.nophysicsobject;

import asciipinball.interfaces.Drawable;
import asciipinball.objects.Ball;
import asciipinball.shapes.Circle;
import asciipinball.sounds.Aui;

/**
 * Abstrakte Oberklasse für Entities die über keine Kollisionsabfrage verfügen
 */
public abstract class NonPhysicEntity implements Drawable {

    protected Circle[] circles;

    /**
     * Updated das Entity
     * @param ball Ball mit dem interagiert werden soll
     * @param aui Audio Inteface des Spiels
     */
    public abstract void updateEntity(Ball ball, Aui aui);

    /**
     * Gibt alle Kreise zurück aus der die Entity besteht
     * @return Alle Kreise aus der die Entity besteht
     */
    public Circle[] getCircles(){
        return circles;
    }

}
