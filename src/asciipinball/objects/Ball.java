package asciipinball.objects;

import asciipinball.Settings;

import java.util.ArrayList;

/**
 * Ein Ball
 */
public class Ball {

    //This coordinate system has it's origin in the bottom left - it gets converted for the printing to canvas process in addToCanvas()
    private float x;
    private float y;
    private float futureX;
    private float futureY;
    private float futureDirection;
    private float futureVelocity;
    private float direction;
    private float velocity;
    private float radius;
    private boolean isInfluencedByPhysics;

    /**
     * Erstellt ein abbild eines Balles.
     * @param ball Ball der Kopiert werden soll
     */
    public Ball(Ball ball) {
        x = ball.getX();
        y = ball.getY();
        futureX = ball.futureX;
        futureY = ball.futureY;
        futureDirection = ball.futureDirection;
        futureVelocity = ball.futureVelocity;
        direction = ball.direction;
        velocity = ball.getVelocity();
        radius = ball.getRadius();
        isInfluencedByPhysics = ball.isInfluencedByPhysics();
    }

    /**
     * Erstellt einen stillstehenden Ball.
     * @param x X Koordinate des Balls
     * @param y Y Koordinate des Balls
     * @param radius radius des Balls
     */
    public Ball(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        direction = 0; //0° = right -> 90° = up -> 180° = left -> -90°/270° = down
        velocity = 0;
        isInfluencedByPhysics = false;
    }

    /**
     * Erstellt einen Ball mit einer Geschwindikeit und Richtung
     * @param x X Koordinate des Balls
     * @param y Y Koordinate des Balls
     * @param radius Radius des Balls
     * @param direction Richtung des Balls
     * @param velocity Geschwindigkeit des Balls
     */
    public Ball(float x, float y, float radius, float direction, float velocity) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.direction = convertDirection(direction);
        this.velocity = velocity;
        isInfluencedByPhysics = velocity != 0;
    }

    /**
     * Gibt isInfluencedByPhysics zurück
     * @return isInfluencedByPhysics
     */
    private boolean isInfluencedByPhysics() {
        return isInfluencedByPhysics;
    }

    /**
     * Gibt den Radius zurück
     * @return Radius des Balls
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Gibt die X Koordinate zurück
     * @return X Koordinate des Balls
     */
    public float getX() {
        return x;
    }

    /**
     * Gibt die Y Koordinate zurücl
     * @return Y Koordinate des Balls
     */
    public float getY() {
        return y;
    }

    /**
     * Gibt die errechnete zulünftige X Koordinate aus
     * @return zukünftige X Koordinate des Balls
     */
    public float getFutureX() {
        return futureX;
    }

    /*'
     * Gibt die errechnete zulünftige Y Koordinate aus
     * @return zukünftige Y Koordinate des Balls
     */
    public float getFutureY() {
        return futureY;
    }

    /**
     * Gibt die Richtung zurück
     * @return Richtung des Balls
     */
    public float getDirection() {
        return direction;
    }

    /**
     * Gibt die Geschwindigkeit zurück
     * @return Geschwindigkeit des Balls
     */
    public float getVelocity() {
        return velocity;
    }

    /**
     * Berechnet die Geschwindigkeit in x-Richtung
     * @return Geschwindikeit in x-Richtung
     */
    private float getXSpeed() {
        return (float) Math.cos(Math.toRadians(direction)) * velocity;
    }

    /**
     * Berechnet die Geschwindigkeit in y-Richtung
     *@return Geschwindikeit in y-Richtung
     */
    private float getYSpeed() {
        return (float) Math.sin(Math.toRadians(direction)) * velocity;
    }

    /**
     * Rechnet eine übergebene Richtung um auf einen Bereich von -180° - 180°
     * @param direction zu umrechnende Richtung
     * @return umgerechnete Richtung
     */
    public float convertDirection(float direction) {
        float result;

        if (direction > -180 && direction <= 180) {
            result = direction;
        } else {

            float clippedDirection = direction % 360;

            if (clippedDirection > 180) {
                result = clippedDirection - 360;
            } else {
                result = clippedDirection;
            }
        }
        return result;
    }

    /**
     * Berechnet die zukünftige Richtung des Balls
     * @param xSpeed Geschwindigkeit in x Richtung
     * @param ySpeed Geschwindigkeit in y Richtung
     */
    private void calculateFutureDirection(float xSpeed, float ySpeed) {
        if (xSpeed >= 0) {
            futureDirection = convertDirection((float) Math.toDegrees(Math.atan(ySpeed / xSpeed)));
        } else if (xSpeed < 0 && ySpeed >= 0) {
            futureDirection = convertDirection((float) (180 - Math.abs(Math.toDegrees(Math.atan(ySpeed / xSpeed)))));
        } else if (xSpeed < 0 && ySpeed < 0) {
            futureDirection = convertDirection((float) (Math.abs(Math.toDegrees(Math.atan(ySpeed / xSpeed)))) - 180);
        }
    }

    /**
     * Berechnet die zukünftige (gesamt) Geschwindigkeit
     * @param xSpeed Geschwindigkeit in x-Richtung
     * @param ySpeed Geschwindigkeit in y-Richtung
     */
    private void calculateFutureVelocity(float xSpeed, float ySpeed) {
        futureVelocity = (float) Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);
    }

    /**
     * Berechnet die zukünftige Position des Balls
     * @param xSpeed Geschwindigkeit in x-Richtung
     * @param ySpeed Geschwindigkeit in y-Richtung
     */
    private void calculateFuturePosition(float xSpeed, float ySpeed) {
        futureX = x + xSpeed;
        futureY = y + ySpeed;
    }


    //calculate Future takes a float gravitation value and simulates the behaviour of the ball per tick(call).

    /**
     * Berechnet den zukünftigen Zustand des Balles unter Berücksichtigung von Gravitation und Reibung.
     * @param gravitationPerTick Gravitation die pro Berechnungsschritt wirken soll
     */
    public void calculateFuture(float gravitationPerTick) {

        float xSpeed = getXSpeed();
        float ySpeed = getYSpeed();

        calculateFuturePosition(xSpeed, ySpeed);

        if (isInfluencedByPhysics) {
            //Simulate gravitation
            ySpeed -= gravitationPerTick;
            //System.out.println("XSpeed: " + xSpeed + " YSpeed: " + ySpeed);

            addVelocity(-Settings.FRICTION);

            calculateFutureDirection(xSpeed, ySpeed);
            calculateFutureVelocity(xSpeed, ySpeed);
        } else {
            isInfluencedByPhysics = velocity != 0;
        }


    }

    /**
     * Setzt die aktuelle Position auf den zukunfts Positions wert
     */
    public void updateBall() {
        x = futureX;
        y = futureY;
        velocity = futureVelocity;
        direction = futureDirection;
    }

    /**
     * Setzt die aktuelle Position auf den zukunfts Positions wert des übergebenen Balls (Ball nach Abprall)
     * @param ball Ball nach Abprall
     */
    public void updateBall(Ball ball) {

        ball.calculateFuture(Settings.GRAVITATION);

        this.x = ball.getFutureX();
        this.y = ball.getFutureY();
        this.direction = ball.getDirection();
        this.velocity = ball.getVelocity();
    }

    /**
     * Springt numberOfSteps Schritte in die Zukunft ohne Kollisions abfrage
     * @param numberOfSteps anzahl der Schritte in die Zukunft
     */
    public void jumpToFuture(int numberOfSteps) {
        for (int i = 0; i < numberOfSteps; i++) {
            calculateFuture(Settings.GRAVITATION);
            x = futureX;
            y = futureY;
            velocity = futureVelocity;
            direction = futureDirection;
        }
    }

    /**
     * Führt eine Liste aus Bällen zu einem Ball zusammen
     * @param balls Liste an Bällen
     * @return zusammengeführter Ball
     */
    public Ball joinBalls(ArrayList<Ball> balls) {
        // calculate average if a ball hits a top or a corner

        int arrayLength = balls.size();

        if (balls.isEmpty()) {
            return null;
        } else if (arrayLength == 1) {
            return balls.get(0);
        } else if (arrayLength == 2) {
            Ball ball1 = balls.get(0);
            Ball ball2 = balls.get(1);
            float newDirection = ball1.convertDirection((((ball1.getDirection() + 360) % 360) + ((ball2.getDirection() + 360) % 360)) / 2);
            return new Ball(ball1.x, ball1.y, ball1.radius, newDirection, ball1.getVelocity());

        } else {
            Ball ball1 = balls.get(0);
            Ball ball2 = balls.get(1);
            float newDirection = ball1.convertDirection((((ball1.getDirection() + 360) % 360) + ((ball2.getDirection() + 360) % 360)) / 2);
            return new Ball(ball1.x, ball1.y, ball1.radius, newDirection, ball1.getVelocity());
        }
    }

    /**
     * Fügt/zieht dem Ball eine Velocity bis zu einem minimal bzw maximal Wert zu/ab.
     * @param velocityAdd Geschwindigkeit die aufaddiert werden soll
     */
    public void addVelocity(float velocityAdd) {
        float newVelocity = velocity + velocityAdd;
        if (newVelocity > Settings.MIN_SPEED && newVelocity < Settings.MAX_SPEED) {
            velocity = newVelocity;
        } else if (newVelocity > Settings.MAX_SPEED) {
            velocity = Settings.MAX_SPEED;
        } else if (newVelocity < Settings.MIN_SPEED) {
            velocity = Settings.MIN_SPEED;
        }
    }

}
