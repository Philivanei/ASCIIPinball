package asciipinball.objects.physicobject.polygonial;

import asciipinball.Coordinate;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.objects.Ball;
import asciipinball.shapes.Line;
import asciipinball.sounds.Aui;

/**
 * Ein rotierendes Kreuz
 */
public class RotatingCross extends PolygonEntity {

    private float x;
    private float y;
    private float speed;
    private float radius;
    private float step;
    private boolean turnClockWise;

    /**
     * Erstellt ein rotierendes Kreuz
     * @param playerManager playerManager des Spiels
     * @param coordinate Koordinate des Mittelpunktes des Kreuzes
     * @param radius Länge der Rotoren
     * @param speed Rotationsgeschwindigkeit
     * @param turnClockWise Drehrichtung
     */
    public RotatingCross(PlayerManager playerManager, Coordinate coordinate, float radius , float speed, boolean turnClockWise) {
        super(playerManager);
        lines = new Line[2];
        generateLines();
        this.x = coordinate.getX();
        this.y = coordinate.getY();
        this.speed = speed;
        this.radius = radius;
        this.turnClockWise = turnClockWise;
        step = 0;
    }

    /**
     * Erstellt 2 Linien aus denen das Kreuz besteht
     */
    private void generateLines(){
        byte factor = -1;
        if(turnClockWise){
            factor = 1;
        }
        Coordinate line1A = new Coordinate((float) (x + Math.sin(step) * radius), (float) (y + factor * Math.cos(step) * radius));
        Coordinate line1B = new Coordinate((float) (x - Math.sin(step) * radius), (float) (y - factor * Math.cos(step) * radius));

        Coordinate line2A = new Coordinate((float) (x - Math.cos(step) * radius), (float) (y + factor * Math.sin(step) * radius));
        Coordinate line2B = new Coordinate((float) (x + Math.cos(step) * radius), (float) (y - factor * Math.sin(step) * radius));

        lines[0] = new Line(line1A, line1B);
        lines[1] = new Line(line2A, line2B);

    }

    /**
     * Dreht das Kreuz
     */
    private void updateRotation(){
        step += speed;
        generateLines();
    }

    /**
     * Sucht nach Kollisionen mit dem Ball und gibt im Falle einer Kollision interactWithBall() (Ball nach Kollision),
     * bei keiner Kollision null zurück. Fügt bei Kollision dem aktuellen Spieler einen Entity spezifischen Score hinzu.
     * Ruft die Methode zur Kreuz rotation auf.
     * @param ball Ball auf den eine kollisionsabfrage durchgeführt werden soll.
     * @param aui
     * @return Ball nach Aufprall
     */
    @Override
    public Ball updateEntity(Ball ball, Aui aui) {
        updateRotation();
        return super.updateEntity(ball, aui);
    }

    @Override
    public char getColor() {
        return 'G';
    }
}
