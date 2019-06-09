package asciipinball.corelogic.players;

public class PlayerManager {
    Player[] players;
    byte playerCount;
    byte currentPlayerId;

    public PlayerManager(byte numberOfPlayers) throws Exception {
        players = new Player[4];
        playerCount = numberOfPlayers;
        currentPlayerId = 0;
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
    }

    public Player getCurrentPlayer(){
        return players[currentPlayerId];
    }

    public int getCurrentPlayerNumber(){
        return currentPlayerId + 1;
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
