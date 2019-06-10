package asciipinball.corelogic.players;

public class PlayerManager {
    Player[] players;
    byte playerCount;
    byte currentPlayerId;
    boolean isInitialized;

    public PlayerManager(){
        players = new Player[4];
        currentPlayerId = 0;
        isInitialized = false;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void init(byte numberOfPlayers) throws Exception{
        playerCount = numberOfPlayers;
        switch (numberOfPlayers){
            //breaks are left out on purpose to avoid reoccurring code
            case 4:
                players[3] = new Player();
            case 3:
                players[2] = new Player();
            case 2:
                players[1] = new Player();
            case 1:
                players[0] = new Player();
                break;
            default:
                throw new Exception("There can't be 0 or more than 4 players");
        }
        isInitialized = true;
    }

    public Player getCurrentPlayer(){
        return players[currentPlayerId];
    }

    public int getCurrentPlayerNumber(){
        return currentPlayerId + 1;
    }

    public int getWinningPlayerNumber(){
        int maxScore = -1;
        int winningPlayerID = -1;
        for (int i = 0; i < players.length; i++) {
            if(players[i] != null){
                if(players[i].getScore() > maxScore){
                    maxScore = players[i].getScore();
                    winningPlayerID = i;
                }
            }
        }

        return  winningPlayerID+1;
    }

    public int getWinningScore(){
        return players[getWinningPlayerNumber()-1].getScore();
    }

    public Player[] getAllPlayer(){
        Player[] returnPlayers = new Player[playerCount];

        for(int i = 0; i < playerCount; i++){
            returnPlayers[i] = players[i];
        }

        return returnPlayers;
    }

    public void currentPlayerLoseRound(){
        players[currentPlayerId].loseBall();
    }

    public void nextPlayer(){
        currentPlayerId++;
        if(currentPlayerId >= playerCount){
            currentPlayerId = 0;
        }
    }

    public boolean isBallLeft(){
        boolean isBallLeft = false;
        for (Player player : players) {
            if(player != null && !isBallLeft){
                isBallLeft = player.getBallLifes() >= 0;
            }
        }
        return isBallLeft;
    }

    public String getCurrentPlayerScoreString(){

        return "player " + getCurrentPlayerNumber() + "\n " + getCurrentPlayer().getScore();
    }
}
