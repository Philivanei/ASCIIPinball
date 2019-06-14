package asciipinball.objects.launchcontrol;

import asciipinball.Settings;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.interfaces.Drawable;
import asciipinball.objects.Ball;
import asciipinball.objects.flipperfinger.MoveStatus;
import asciipinball.objects.physicobject.polygonial.PolygonEntity;
import asciipinball.shapes.Line;

/**
 * Eine Abschussvorrichtung
 */
public class LaunchControl extends PolygonEntity implements Drawable {

    private float width;
    private float maxHeight;
    private float minHeight;
    private MoveStatus moveStatus;
    private long upStartTime;
    private float x;
    private boolean isClicked;
    private long pressTime;
    private long pressStartTime;
    private boolean isFinished;
    private boolean isSpaceBlocked;
    private float tiltOffset;

    /**
     * Erzeugt eine Abschussvorrichtung für den Start des Balls
     *
     * @param playerManager Playermanager des Spiels
     * @param x             x-Koordinate der Abschussvorrichtung
     * @param minHeight     Minimalhöhe der Abschussvorrichtung (Ausgangsposition)
     * @param maxHeight     Maximalhöhe der Abschussvorrichtung (Endposition)
     * @param width         Breite der Abschussvorrichtung
     * @param tiltOffset    Zusatzhöhe der rechten Seite zum Schrägstellen der Abschussvorrichtung.
     */
    public LaunchControl(PlayerManager playerManager, float x, float minHeight, float maxHeight, float width, float tiltOffset) {
        super(playerManager);
        this.tiltOffset = tiltOffset;
        lines = new Line[1];
        this.width = width;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.x = x;
        isClicked = false;
        isFinished = false;
        isSpaceBlocked = false;
        moveStatus = MoveStatus.STOP;
        generateLines(minHeight);
    }

    /**
     * Setzt die Abschussvorrichtung zurück
     */
    //new game/ ball gets lost --> reset everything
    public void reset() {
        isClicked = false;
        isFinished = false;
        isSpaceBlocked = false;
        moveStatus = MoveStatus.STOP;
        generateLines(minHeight);
    }

    /**
     * Bewegt die Abschussvorrichtung nach oben.
     */
    //key reaction
    public void onSpaceDown() {
        if (!isSpaceBlocked) {
            pressStartTime = System.currentTimeMillis();
        }
        isSpaceBlocked = true;
    }

    /**
     * Stellt fest, wie lange die Taste gedrückt wurde, um den Ball je nach Dauer schwach oder stark abzustoßen.
     */
    //key reaction to get velocity dependent to time
    public void onSpaceUp() {
        if (!isClicked) {
            pressTime = System.currentTimeMillis() - pressStartTime;
            upStartTime = System.currentTimeMillis();
            isClicked = true;
        }
    }

    /**
     * Zeichnet die Abschussvorrichtung.
     *
     * @param y y-Koordinate der Abschussvorrichtung
     */
    //creates the lines for animating them
    private void generateLines(float y) {

        if (y >= maxHeight) {
            //moving the launcher diagonally
            lines[0] = new Line(x, maxHeight, x + width, y);
        } else {
            //moving the launcher up (horizontal)
            lines[0] = new Line(x, y, x + width, y);
        }
    }

    /**
     * Animiert die Bewegung nach oben
     *
     * @param timeSinceStart Zeit seit Aktivierung der Abschussvorrichtung
     * @return Dynamische y-Koordinate der Abschussvorrichtung
     */
    //calculating the animation for moving the launcher up
    private float calculateHeightUp(long timeSinceStart) {

        float result = (maxHeight - minHeight) / Settings.TIME_FOR_JUMP * timeSinceStart + minHeight;
        if (result > (maxHeight + tiltOffset)) {
            //change moveStatus
            result = maxHeight + tiltOffset;
            moveStatus = MoveStatus.STOP;
            isFinished = true;
        } else {
            moveStatus = MoveStatus.UP;
        }
        return result;
    }

    /**
     * Berechnet die Abschussvorrichtung anhand der dynamisch y-Koordinate
     */
    //sets the lines to the wanted positions
    public void updateLaunchControl() {

        if (isClicked) {
            if (!isFinished) {
                moveStatus = MoveStatus.UP;
            }
            long timeSinceUpStart = System.currentTimeMillis() - upStartTime;
            generateLines(calculateHeightUp(timeSinceUpStart));
        }
    }

    /**
     * Berechnet die Stärke des Abschusses
     *
     * @return Geschwindigkeitsschub beim Abschuss
     */
    //the longer you hold space the stronger is the velocity
    private float calculateVelocityBoost() {

        float velocityBoost = (Settings.MAX_LAUNCH_VELOCITY * (pressTime / Settings.MAX_LAUNCH_PRESS_TIME));

        if (velocityBoost > Settings.MAX_LAUNCH_VELOCITY) {
            return Settings.MAX_LAUNCH_VELOCITY;

        } else if (velocityBoost < Settings.MIN_LAUNCH_VELOCITY) {
            return Settings.MIN_LAUNCH_VELOCITY;

        } else {
            return velocityBoost;
        }
    }

    @Override
    protected Ball interactWithBall(Ball ball) {
        Ball returnBall = super.interactWithBall(ball);
        if (moveStatus != MoveStatus.STOP) {
            returnBall = new Ball(returnBall.getX(), returnBall.getY(), returnBall.getRadius(),
                    90, calculateVelocityBoost());
        }

        returnBall.jumpToFuture(75);
        return returnBall;
    }

    @Override
    public char getColor() {
        return 'B';
    }
}