package asciipinball.objects.nophysicsobject.pointdoor;

import asciipinball.Coordinate;
import asciipinball.corelogic.players.PlayerManager;
import asciipinball.interfaces.Drawable;
import asciipinball.objects.Ball;
import asciipinball.objects.nophysicsobject.NonPhysicEntity;
import asciipinball.shapes.Circle;

import java.util.ArrayList;

public class PointDoor extends NonPhysicEntity implements Drawable {

    private ArrayList<PointDoor> linkedPointDoors;
    private boolean status;
    private PlayerManager playerManager;
    private int score;
    private long deadTimer = 0;

    public PointDoor(PlayerManager playerManager, Coordinate coordinate, float radius, int scoreIfToggled){
        linkedPointDoors = new ArrayList<>();
        circles = new Circle[1];
        circles[0] = new Circle(coordinate.getX(), coordinate.getY(), radius);
        this.playerManager = playerManager;
        status = false;
        score = scoreIfToggled;
    }

    public boolean getStatus(){
        return status;
    }

    public void addLink(PointDoor pointDoor){
        linkedPointDoors.add(pointDoor);
    }

    private boolean areAllOn(){
        boolean areAllOn = status;
        for (PointDoor pointDoor : linkedPointDoors) {
            //if only one is off then areAllOn will be false
            areAllOn &= pointDoor.getStatus();
        }
        return areAllOn;
    }

    private void resetAll(){
        for (PointDoor pointDoor : linkedPointDoors) {
            pointDoor.reset();
        }
        reset();
    }

    public void reset(){
        deadTimer = System.currentTimeMillis();
        status = false;
    }

    @Override
    public void updateEntity(Ball ball) {

        if(System.currentTimeMillis() - deadTimer > 200) {

            float distancePointBall = (float) Math.sqrt(Math.pow((ball.getPositionX() - circles[0].getX()), 2) + Math.pow((ball.getPositionY() - circles[0].getY()), 2));

            if (distancePointBall < circles[0].getRadius() + ball.getRadius()) {
                status = true;
            }

            if (areAllOn()) {
                resetAll();
                playerManager.getCurrentPlayer().addToScore(score);
            }
        }

    }


    @Override
    public char getColor() {
        if(status){
            return 'O';
        }
        else{
            return 'S';
        }
    }
}
