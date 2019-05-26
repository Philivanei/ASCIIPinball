package asciipinball.shapes;

import asciipinball.GameView;
import asciipinball.interfaces.Drawable;
import asciipinball.logic.PinballGame;

public class Line implements Drawable {
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Line(Coordinate p1, Coordinate p2){
        x1 = p1.getX();
        y1 = p1.getY();
        x2 = p2.getX();
        y2 = p2.getY();
    }

    public float getX1() {
        return x1;
    }

    public float getY1() { return y1; }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public float getLength() {return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));}

    // y = m*x+b -> y = getM()*x+getB()
    public float getM(){
        if(x2 == x1){
            return Float.POSITIVE_INFINITY;
        }
        return ((y2 - y1) / (x2 - x1));
    } //Slope

    public float getB(){return (y1 - getM() * x1);} //Shift

    @Override
    public void addToCanvas(GameView gameView) {

        char canvasSegment[][];

        int deltaX = Math.round(Math.abs(x2-x1));
        int deltaY = Math.round(Math.abs(y2-y1));

        canvasSegment = new char[deltaY>0 ? deltaY : 1][deltaX>0 ? deltaX : 1]; //[ROW][COLUMN]

        for (int  column = 0; column < canvasSegment[0].length; column++){
            for (int row = 0; row < canvasSegment.length; row++){
                if(Float.isFinite(getM())){
                    if(getM() > 0){
                        if(Math.abs(((canvasSegment.length - 1) - row) - (getM() * column)) < ((Math.abs(getM()) <= 1) ? 1 : (Math.abs(getM()) / 2))){
                        //if(Math.abs(((canvasSegment.length - 1) - row) - (getM() * column)) < (Math.abs(getM()) / 2)){
                            canvasSegment[row][column] = 'B';
                        }else{
                            canvasSegment[row][column] = ' ';
                        }
                    }else{
                        if(Math.abs(-row - (getM() * column)) < ((Math.abs(getM()) <= 1) ? 1 : (Math.abs(getM()) / 2))){
                            canvasSegment[row][column] = 'B';
                        }
                        else{
                            canvasSegment[row][column] = ' ';
                        }
                    }
                    /*if(Math.abs(((getM() > 0) ? ((canvasSegment.length - 1) - row) : -row) - (getM() * column)) < ((Math.abs(getM()) <= 1) ? 1 : (Math.abs(getM()) / 2))){

                        canvasSegment[row][column] = 'B';
                    }
                    else{
                        canvasSegment[row][column] = ' ';
                    }*/
                }else{
                    canvasSegment[row][column] = 'B';
                }
            }
        }

        int canvasColumn = Math.round(x1 < x2 ? x1 : x2) + PinballGame.OFFSET; //Column = X
        int canvasRow = Math.abs(Math.round((y1 > y2 ? y1 : y2) - (PinballGame.HEIGHT)));
        gameView.addToCanvas(canvasSegment,canvasRow,canvasColumn);
    }
}
