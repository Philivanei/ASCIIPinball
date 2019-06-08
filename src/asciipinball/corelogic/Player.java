package asciipinball.corelogic;

public class Player {

    private int score;
    private int ballLifes;

    public Player() {
        score = 0;
        ballLifes = 3;
    }

    public int getScore() {
        return score;
    }

    public void addToScore(int score){
        this.score += score;
    }

}
