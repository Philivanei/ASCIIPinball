package asciipinball;

public class GameTable {

    public static final float GRAVITATION = (float) 0.3;

    private int width;
    private int hight;
    private GameView gameView;
    private Ball[] balls;
    private Player[] players;
    private Entity[] entities;
    private char[][] asciiString;

    //Generates GameTable with widht and hight (Koordinates: X: 0 - width, Y: 0 - hight)
    public GameTable(int width, int hight) {

        /** Init asciipinball.asciipinball.asciipinball.GameView **/
        gameView = new GameView(30,30,"Pinball");
        gameView.setWindowsSize(GameView.WINDOWSIZE_LARGE);
        gameView.show();


        /**Init Arrays and Values**/
        balls = new Ball[4];
        players = new Player[4];
        entities = new Entity[500];
        asciiString = new char[width][hight];
        this.width = width;
        this.hight = hight;
        generateAsciiString();



        /** Test Stuff - Remove bevor final commit!**/
        balls[0] = new Ball(14, 1, (float) 120, (float) 2);

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


    public void simulateTick(){

        for(int i = 0; i < balls.length; i++) {
            if (balls[i] != null) {
                balls[i].simulateTick(GRAVITATION);
                interactWithGameTable(balls[i]);
            }
        }

    }

    public void interactWithEntities(Ball ball){

        for(int i = 0; i < entities.length; i++){
            entities[i].interactWithBall(ball);
        }

    }

    public void interactWithGameTable(Ball ball){

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
            ball.simulateTick(GRAVITATION);
        }
        //colision bottom
        if((ball.positionY <= 0) && ((ball.getDirection()) <= 0 && (ball.getDirection()) >= -180)){
            System.out.println("The ball is coliding with bottom");
            ball.setDirection(-ball.getDirection());
            ball.simulateTick(GRAVITATION);
        }

    }

    public void drawToCanvas(){
        gameView.addToCanvas(asciiString, 0,0);
    }

    public void drawAll(){

        gameView.clearCanvas();
        drawToCanvas();

        for(int i = 0; i < balls.length; i++) {
            if(balls[i] != null) {
                balls[i].drawToCanvas(gameView, hight);
            }
        }

        for(int i = 0; i < entities.length; i++){
            if(entities[i] != null){
                entities[i].drawToCanvas(gameView, hight);
            }
        }


        gameView.printCanvas();
    }


}
