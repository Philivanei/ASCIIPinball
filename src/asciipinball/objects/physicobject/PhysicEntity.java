package asciipinball.objects.physicobject;

import asciipinball.CollisionData;
import asciipinball.corelogic.playersandscore.PlayerManager;
import asciipinball.objects.Ball;
import asciipinball.corelogic.playersandscore.Player;

import java.util.ArrayList;


/**
 * Eine abstrakte Oberklasse für Physik Objekte.
 */
public abstract class PhysicEntity {

    protected ArrayList<CollisionData> collisionList = new ArrayList<>();
    protected int score = 0;
    protected PlayerManager playerManager;

    protected PhysicEntity(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    /**
     * Sucht nach Kollisionen mit dem Ball und gibt im Falle einer Kollision interactWithBall() (Ball nach Kollision),
     * bei keiner Kollision null zurück. Fügt bei Kollision dem aktuellen Spieler einen Entity spezifischen Score hinzu.
     * @param ball Ball auf den eine kollisionsabfrage durchgeführt werden soll
     * @return Ball nach Aufprall
     */
    public Ball updateEntity(Ball ball){
        if(isCollided(ball)){
            if(score != 0){
                playerManager.getCurrentPlayer().addToScore(score);
            }
            return interactWithBall(ball);
        } else {
            return null;
        }
    }

    /**
     * Entfernt alle erkannten kollisionen aus der Liste bis auf den nächsten (Relevant für Entities die aus mehr als einer Linie/Kreis bestehen)
     */
    protected void cleanupCollisionList(){

        if(!collisionList.isEmpty() && collisionList.size() > 1){
            for(int i = 1; i < collisionList.size() ;i++){
                if(collisionList.get(i) != collisionList.get(i-1)){
                    int iterator = i;
                    while (iterator < collisionList.size()){
                        collisionList.remove(iterator);
                        iterator++;
                    }
                    break;
                }
            }
        }

    }


    /**
     * Berechte Winkel nach Aufprall aus ball und Steigung der Abprall Linie
     * @param ball Ball der abprallt
     * @param gradient Steigung der Abprall linie
     * @return Winkel als float des Balles nach Aufprall
     */
    protected float calculateBallAngleFromGradient(Ball ball, float gradient) {
        float angleToLine;

        if (Float.isFinite(gradient)) {

            //angle to line is Angle to x-axis
            angleToLine = (float) Math.toDegrees(Math.atan(gradient));
        } else {
            angleToLine = 90;
        }

        //System.out.println(angleToLine);

        return ball.convertDirection(-(ball.getDirection() - angleToLine) + angleToLine);
        //float finalAngle = ball.convertDirection((-ball.convertDirection((ball.getDirection() + 90 - angleToLine)))  - (90 - angleToLine)); //double conversion is necessary if -ball.convertDirection results in -180°
    }

    /**
     * Prüft Kollision zwischen Ball und Entity und speichert im falle einer Kollision den Kollisions Punkt und das Kollisions shape
     * @param ball Ball auf den die Kollisionsabfrage durchgeführt werden soll
     * @return Ball nach Collision
     */
    protected abstract boolean isCollided(Ball ball);

    /**
     * Berechnet den zustand des Balls nach dem Aufprall und gibt diesen als neuen Ball zurück
     * @param ball Ball vor Abprall
     * @return Ball nach Abprall
     */
    protected abstract Ball interactWithBall(Ball ball);

}
