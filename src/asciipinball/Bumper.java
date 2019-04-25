package asciipinball;

public class Bumper extends VelocityManipulatingEntity {

    protected int radius;
    protected char[][] asciiString;

    public Bumper(int x, int y, int radius){
        positionX = x;
        positionY = y;
        this.radius = radius;
        asciiString = new char[((radius * 2) + 1)][((radius * 2) + 1)];
        generateAsciiString();
    }


    @Override
    public void interactWithBall(Ball ball) {

    }

    @Override
    public void drawToCanvas(GameView gameView, int gameTableHight) {
        gameView.addToCanvas(asciiString, gameTableHight - (positionY + radius) - 1, (positionX - radius));
    }

    private void generateAsciiString(){
        for(int i = 0; i < asciiString.length; i++){
            for(int j = 0; j < asciiString[0].length; j++){

                //calculates the distance between point in char[][] and middlepoint of char[][]
                float dist = (float) Math.sqrt(Math.pow((i - radius), 2) + Math.pow((radius - j), 2));

                if(dist <= radius){
                    asciiString[i][j] = 'O';
                } else {
                    asciiString[i][j]= ' ';
                }
            }
        }
    }
}
