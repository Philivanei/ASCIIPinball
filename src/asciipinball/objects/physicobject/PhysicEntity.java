package asciipinball.objects.physicobject;

import asciipinball.CollisionData;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.objects.Ball;
import asciipinball.sounds.Aui;

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
     * @param aui Audio Interface des Spiels
     * @return Ball nach Aufprall
     */
    public Ball updateEntity(Ball ball, Aui aui){
        if(isCollided(ball)){
            if(score != 0){
                playerManager.getCurrentPlayer().addToScore(score);
            }
            aui.playSound(getCollisionSoundId(), false);
            return interactWithBall(ball);
        } else {
            return null;
        }
    }

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


    protected float calculateBallAngleFromGradient(Ball ball, float gradient) {
        float angleToLine;

        if (Float.isFinite(gradient)) {

            //angle to line is Angle to x-axis
            angleToLine = (float) Math.toDegrees(Math.atan(gradient));
        } else {
            angleToLine = 90;
        }

        return ball.convertDirection(-(ball.getDirection() - angleToLine) + angleToLine);
        //float finalAngle = ball.convertDirection((-ball.convertDirection((ball.getDirection() + 90 - angleToLine)))  - (90 - angleToLine)); //double conversion is necessary if -ball.convertDirection results in -180°
    }

    protected int getCollisionSoundId(){
        return 0;
    }

    protected abstract boolean isCollided(Ball ball);
    protected abstract Ball interactWithBall(Ball ball);

}
