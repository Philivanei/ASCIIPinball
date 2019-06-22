package asciipinball.playersandscore;

/**
 * Ein Spieler
 */
public class Player {

    private int score;
    private int ballLifes;

    /**
     * Erstellt einen Spieler
     */
    public Player() {
        score = 0;
        ballLifes = 2;
    }

    /**
     * Gibt den Score des Spielers zurück
     *
     * @return Score des Spielers
     */
    public int getScore() {
        return score;
    }

    /**
     * Addiert auf den Score des Spielers eine übergebene Anzahl an Punkten
     *
     * @param score Punkte, die aufaddiert werden sollen
     */
    public void addToScore(int score) {
        this.score += score;
    }

    /**
     * Gibt die verbleibenden Bälle des Spielers zurück
     *
     * @return verbleibende Bälle des Spielers
     */
    public int getBallLifes() {
        return ballLifes;
    }


    /**
     * Zieht dem Spieler ein Leben ab
     */
    public void loseBall() {
        ballLifes -= 1;
        if (ballLifes < -1) {
            ballLifes = -1;
        }
    }
}
