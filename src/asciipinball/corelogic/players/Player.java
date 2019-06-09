package asciipinball.corelogic.players;

public class Player {

    private int score;
    private int ballLifes;

    public Player() {
        score = 0;
        ballLifes = 2;
    }

    public int getScore() {
        return score;
    }

    public void addToScore(int score){
        this.score += score;
    }

    public int getBallLifes() {
        return ballLifes;
    }

    public void loseBall(){
        ballLifes -= 1;
        if(ballLifes < -1){
            ballLifes = -1;
        }
    }
}
