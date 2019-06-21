package asciipinball.objects.nophysicsobject.pointdoor;

import asciipinball.Coordinate;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.interfaces.Drawable;
import asciipinball.objects.Ball;
import asciipinball.objects.nophysicsobject.NonPhysicEntity;
import asciipinball.shapes.Circle;
import asciipinball.sounds.Aui;

import java.util.ArrayList;

/**
 * Eine PointDoor
 */
public class PointDoor extends NonPhysicEntity implements Drawable {

    private ArrayList<PointDoor> linkedPointDoors;
    private boolean[] status;
    private PlayerManager playerManager;
    private int score;
    private long deadTimer = 0;

    /**
     * Erstellt eine PointDoor
     * @param playerManager playerManager des Spiels
     * @param coordinate Koordinate der PointDoor
     * @param radius Radius der PointDoor
     * @param scoreIfToggled Punkte die dem Spieler gutgeschrieben werden, wenn er alle Pointdoors aktiviert
     */
    //Package private so only the PointDoorFactory can instantiate a new PointDoor
    PointDoor(PlayerManager playerManager, Coordinate coordinate, float radius, int scoreIfToggled){
        linkedPointDoors = new ArrayList<>();
        circles = new Circle[1];
        circles[0] = new Circle(coordinate.getX(), coordinate.getY(), radius);
        this.playerManager = playerManager;
        status = new boolean[4];
        status[0] = false;
        status[1] = false;
        status[2] = false;
        status[3] = false;
        score = scoreIfToggled;
    }

    /**
     * Gibt den Status der PointDoor abhängig vom aktuellem Spieler zurück
     * @return Status der Pointdoor
     */
    public boolean getStatus(){
        return status[playerManager.getCurrentPlayerId()];
    }

    /**
     * Fügt einen Beziehung zu einer anderen pointDoor hinzu.
     * @param pointDoor pointDoor zu der eine Beziehung aufgebaut werden soll
     */
    //Package private so only the PointDoorFactory can add a Link
    void addLink(PointDoor pointDoor){
        linkedPointDoors.add(pointDoor);
    }

    /**
     * Überprüft ob alle in Beziehungstehenden pointDoors inklusive sich selbst an sind.
     * @return boolscher ausdruck über den status aller pointdoors
     */
    private boolean areAllOn(){
        boolean areAllOn = status[playerManager.getCurrentPlayerId()];
        for (PointDoor pointDoor : linkedPointDoors) {
            //if only one is off then areAllOn will be false
            areAllOn &= pointDoor.getStatus();
        }
        return areAllOn;
    }

    /**
     * Setzt eigenen Status und den aller in beziehung stehender pointDoors auf false
     */
    private void resetAll(){
        for (PointDoor pointDoor : linkedPointDoors) {
            pointDoor.reset();
        }
        reset();
    }

    /**
     * Setzt den status auf false
     */
    public void reset(){
        deadTimer = System.currentTimeMillis();
        status[playerManager.getCurrentPlayerId()] = false;
    }

    /**
     * Überprüft ob der Ball über die pointDoor rollt. Wenn ja wird der Status der pointDoor auf true gesetzt.
     * Überprüft ob alle Pointdoors an sind. Wenn ja werden scoreIfToggled dem aktuellen Spieler gutgeschrieben und alle pointDoors Status auf false gestellt
     * @param ball Ball mit dem interagiert werden soll
     * @param aui
     */
    @Override
    public void updateEntity(Ball ball, Aui aui) {

        if(System.currentTimeMillis() - deadTimer > 200) {

            float distancePointBall = (float) Math.sqrt(Math.pow((ball.getX() - circles[0].getX()), 2) + Math.pow((ball.getY() - circles[0].getY()), 2));

            if (distancePointBall < circles[0].getRadius() + ball.getRadius()) {
                if(!status[playerManager.getCurrentPlayerId()]){
                    aui.playSound(5,false);
                    status[playerManager.getCurrentPlayerId()] = true;
                }
            }

            if (areAllOn()) {
                resetAll();
                aui.playSound(2, true);
                playerManager.getCurrentPlayer().addToScore(score);
            }
        }

    }

    /**
     * Gibt abhängig des Status die Farbe Orange oder Grau zurück
     * @return Farbe der Pointdoor
     */
    @Override
    public char getColor() {
        if(status[playerManager.getCurrentPlayerId()]){
            return 'O';
        }
        else{
            return 'S';
        }
    }
}
