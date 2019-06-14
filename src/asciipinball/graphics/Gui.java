package asciipinball.graphics;

import asciipinball.GameView;
import asciipinball.Settings;
import asciipinball.corelogic.AsciiStringBuilder.AsciiStringBuilder;
import asciipinball.exceptions.ClassNotSupportedException;
import asciipinball.fonts.AsciiStringContainer;
import asciipinball.objects.Ball;
import asciipinball.objects.flipperfinger.FlipperFingerControl;
import asciipinball.objects.nophysicsobject.NonPhysicEntity;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.objects.physicobject.circular.CircleEntity;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Circle;
import asciipinball.shapes.Line;

/**
 * Verwaltung der Ausgaben auf dem GameView Fenster
 */
public class Gui {

    private GameView gameView;

    /**
     * Erstellt eine neue GameView
     * @param gameView
     */
    public Gui(GameView gameView){
        this.gameView = gameView;
    }

    /**
     * Konvertiert einen String zu einem AsciiArtString und fügt in in einer Zeile und Spalte ein
     * @param string String der Angezeigt werden soll
     * @param row Zeile
     * @param column Spalte
     * @param font Schriftart des AsciiStrings
     */
    public void addAsciiStringToCanvas(String string, int row, int column, AsciiStringContainer font){
        char[][] charArray = new AsciiStringBuilder(font).buildAsciiString(string);
        int newRow = (row - (charArray.length/2));
        int newColumn = (column - (charArray[0].length/2));
        gameView.addToCanvas(charArray,newRow,newColumn);
    }

    /**
     * Fügt einen Klassischen String in einer Zeile und Spalte ein
     * @param string Sting der eingefügt werden soll
     * @param row Zeile
     * @param column Spalte
     */
    public void addStringToCanvas(String string, int row, int column){
        gameView.addToCanvas(string, row, column);
    }

    /**
     * Fügt ein char-Array ein
     * @param array Array das eingefügt werden soll
     * @param row Zeile
     * @param column Spalte
     */
    public void addArrayToCanvas(char[][] array, int row, int column){
        gameView.addToCanvas(array,row,column);
    }

    /**
     * Zeichnet die Flippefinger ein
     * @param flipperFingerControl Flipperfinger
     */
    public void addToCanvas(FlipperFingerControl flipperFingerControl){
        addToCanvas(flipperFingerControl.getLeftFlipperFinger());
        addToCanvas(flipperFingerControl.getRightFlipperFinger());
    }

    /**
     * Zeichnet eine PhysicEntity ein
     * @param entity PhysicEntity
     * @throws ClassNotSupportedException Wird geworfen wenn eine unbekannte Klasse übergeben wird
     */
    public void addToCanvas(PhysicEntity entity) throws ClassNotSupportedException {
        if(entity instanceof PolygonEntity){
            addToCanvas((PolygonEntity) entity);
        }else if (entity instanceof CircleEntity){
            addToCanvas((CircleEntity) entity);
        }
        else {
            throw new ClassNotSupportedException("Gui can't prin't " + entity.toString());
        }
    }

    /**
     * Fügt einer PolygonEntity hinzu
     * @param entity PolygonEntity
     */
    public void addToCanvas(PolygonEntity entity){
        Line[] lines = entity.getLines();
        if(lines.length != 0){
            for(Line line : lines){
                addLineToCanvas(line, entity.getColor());
            }
        }
    }

    /**
     * Fügt eine NonPhysicEntity hinzu
     * @param entity NonPhysicEntity
     */
    public void addToCanvas(NonPhysicEntity entity){
        addToCanvas(entity.getCircles(), entity.getColor());
    }

    /**
     * Fügt eine CircleEntity hinzu
     * @param entity CircleEntity
     */
    public void addToCanvas(CircleEntity entity){
        addToCanvas(entity.getCircles(), entity.getColor());
    }

    /**
     * Fügt alle Circles des Circle Arrays hinzu
     * @param circles Array an Kreisen
     * @param color Farbe
     */
    public void addToCanvas(Circle[] circles, char color){
        if(circles.length != 0){
            for(Circle circle : circles){
                addCircleToCanvas(circle, color);
            }
        }
    }

    /**
     * Fügt den bal hinzu
     * @param ball Ball der gezeichnet werden soll
     */
    public void addToCanvas(Ball ball){
        int diameter = Math.round(ball.getRadius() * 2);

        char[][] canvasSegment = new char[diameter][diameter];

        for(int column = 0; column < canvasSegment[0].length; column++){
            for(int row = 0; row < canvasSegment.length; row++) {
                float distanceCanvasMiddle = (float) Math.sqrt(Math.pow(((ball.getRadius() - 0.5) - column),2) + Math.pow(((ball.getRadius() - 0.5) - row),2));
                //canvasSegment[row][column] = (distanceCanvasMiddle < radius) ? 'B' : ' ';
                if(distanceCanvasMiddle <= ball.getRadius()){
                    canvasSegment[row][column] = 'Y';
                }
                else{
                    canvasSegment[row][column] = ' ';
                }
            }
        }

        int canvasRow = Settings.HEIGHT - Math.round(ball.getY() + ball.getRadius());
        int canvasColumn = Math.round(ball.getX() - ball.getRadius()) + Settings.OFFSET;

        gameView.addColorStringToCanvas(canvasSegment,canvasRow,canvasColumn);
    }

    private void addLineToCanvas(Line line, char color){
        char[][] canvasSegment;

        int deltaX = Math.round(Math.abs(line.getX2()-line.getX1()));
        int deltaY = Math.round(Math.abs(line.getY2()-line.getY1()));

        canvasSegment = new char[deltaY>0 ? deltaY : 1][deltaX>0 ? deltaX : 1]; //[ROW][COLUMN]

        for (int  column = 0; column < canvasSegment[0].length; column++){
            for (int row = 0; row < canvasSegment.length; row++){
                if(Float.isFinite(line.getM())){
                    if(line.getM() > 0){
                        if(Math.abs(((canvasSegment.length - 1) - row) - (line.getM() * column)) < ((Math.abs(line.getM()) <= 1) ? 1 : (Math.abs(line.getM()) / 2))){
                            //if(Math.abs(((canvasSegment.length - 1) - row) - (getM() * column)) < (Math.abs(getM()) / 2)){
                            canvasSegment[row][column] = color;
                        }else{
                            canvasSegment[row][column] = ' ';
                        }
                    }else{
                        if(Math.abs(-row - (line.getM() * column)) < ((Math.abs(line.getM()) <= 1) ? 1 : (Math.abs(line.getM()) / 2))){
                            canvasSegment[row][column] = color;
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
                    canvasSegment[row][column] = color;
                }
            }
        }

        int canvasColumn = Math.round(line.getX1() < line.getX2() ? line.getX1() : line.getX2()) + Settings.OFFSET; //Column = X
        int canvasRow = Math.abs(Math.round((line.getY1() > line.getY2() ? line.getY1() : line.getY2()) - (Settings.HEIGHT)));
        gameView.addColorStringToCanvas(canvasSegment,canvasRow,canvasColumn);
    }

    private void addCircleToCanvas(Circle circle, char color){
        float radius = circle.getRadius();
        float x = circle.getX();
        float y = circle.getY();

        int diameter = Math.round(radius * 2);

        char[][] canvasSegment = new char[diameter][diameter];

        for(int column = 0; column < canvasSegment[0].length; column++){
            for(int row = 0; row < canvasSegment.length; row++) {
                float distanceCanvasMiddle = (float) Math.sqrt(Math.pow(((radius - 0.5) - column),2) + Math.pow(((radius - 0.5) - row),2));
                //canvasSegment[row][column] = (distanceCanvasMiddle < radius) ? 'B' : ' ';
                if(distanceCanvasMiddle <= radius){
                    canvasSegment[row][column] = color;
                }
                else{
                    canvasSegment[row][column] = ' ';
                }
            }
        }

        int canvasRow = Settings.HEIGHT - Math.round(y + radius);
        int canvasColumn = Math.round(x - radius) + Settings.OFFSET;

        gameView.addColorStringToCanvas(canvasSegment,canvasRow,canvasColumn);
    }

}
