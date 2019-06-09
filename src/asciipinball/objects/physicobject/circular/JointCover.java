package asciipinball.objects.physicobject.circular;

import asciipinball.corelogic.players.PlayerManager;
import asciipinball.exceptions.NotConnectedLinesException;
import asciipinball.objects.Ball;
import asciipinball.shapes.Circle;
import asciipinball.shapes.Line;

public class JointCover extends CircleEntity {

    public JointCover(PlayerManager playerManager, float x, float y) {
        super(playerManager);
        circles = new Circle[1];
        circles[0] = new Circle(x,y,1f);
    }

    public JointCover(PlayerManager playerManager, Line line1, Line line2) throws NotConnectedLinesException {
        super(playerManager);

        circles = new Circle[1];

        if((line1.getX1() == line2.getX1() && line1.getY1() == line2.getY1()) ||
                (line1.getX1() == line2.getX2() && line1.getY1() == line2.getY2()) ){
            circles[0] = new Circle(line1.getX1(),line1.getY1(),0.251f);
        }else if((line1.getX2() == line2.getX1() && line1.getY2() == line2.getY1()) ||
                (line1.getX2() == line2.getX2() && line1.getY2() == line2.getY2())){
            circles[0] = new Circle(line1.getX2(),line1.getY2(),0.25f);
        }else{
            circles[0] = new Circle(-10,0,0.0f);
            throw new NotConnectedLinesException();
        }
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        Ball returnBall = super.interactWithBall(ball);
        returnBall.addVelocity(0.01f);
        return returnBall;
    }


    @Override
    public char getColor() {
        return ' ';
    }
}