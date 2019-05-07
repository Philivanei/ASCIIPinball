package asciipinball;

public class Table {

    private char[][] asciiString;

    private int width;
    private int hight;

    //Generates Table with widht and hight (Koordinates: X: 0 - width, Y: 0 - hight)
    public Table(int width, int hight) {

        asciiString = new char[width][hight];
        this.width = width;
        this.hight = hight;
        generateAsciiString();
    }


    private void generateAsciiString(){

        for(int i = 0; i < hight; i++){
            for (int j = 0; j < width; j++){
                if(i == 0 || i == (hight - 1)){
                    asciiString[i][j] = '=';
                }else if(j == 0 || j == (width - 1)){
                    asciiString[i][j] = '║';
                }else{
                    asciiString[i][j] = ' ';
                }
            }
        }
        asciiString[0][0]='╔';
        asciiString[width - 1][0]='╚';
        asciiString[0][hight - 1] = '╗';
        asciiString[width-1][hight-1] = '╝';
    }



    public void interactWithGameTable(Ball ball, float gravitation){

        //colision top and check if the ball would go out of bounds - So the ball could get in from the outside but not out from the inside
        if((ball.positionY >= hight - 1) && (ball.getDirection() >= 0 && ball.getDirection() <= 180)){
            System.out.println("The ball is coliding with top");
        }
        //colision left
        if((ball.positionX <= 0) && (ball.getDirection() >= 90 || ball.getDirection() <= -90)){
            System.out.println("The ball is coliding with left");
        }
        //colision right
        if((ball.positionX >= width - 1) && ((ball.getDirection() >= -90) || (ball.getDirection() <= 90))){
            System.out.println("The ball is coliding with right: " + ball.getDirection());
            ball.setDirection(180 - ball.getDirection());
            System.out.println("Got converted to: " + ball.getDirection());
            ball.simulateTick(gravitation);
        }
        //colision bottom
        if((ball.positionY <= 0) && ((ball.getDirection()) <= 0 && (ball.getDirection()) >= -180)){
            System.out.println("The ball is coliding with bottom");
            ball.setDirection(-ball.getDirection());
            ball.simulateTick(gravitation);
        }

    }

    public void drawToCanvas(GameView gameView, int offeset){
        gameView.addToCanvas(asciiString, 0, offeset);
    }


}
