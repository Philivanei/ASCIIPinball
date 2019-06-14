package asciipinball.playersandscore;

import asciipinball.exceptions.NotSupportedNumberOfPlayersException;
import asciipinball.sounds.Aui;

/**
 * Verwaltung von biszu 4 Spielern
 */
public class PlayerManager {
    private Player[] players;
    private byte playerCount;
    private byte currentPlayerId;
    private boolean isInitialized;

    /**
     * Erstellt einen PlayerManager
     */
    public PlayerManager(){
        players = new Player[4];
        currentPlayerId = 0;
        isInitialized = false;
    }

    /**
     * Gibt den status der Initialisierung zurück
     * @return status der Initialisierung
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Initialisisert den Spielermanager indem eine gegebene Anzahl an Spielern erstellt wird
     * @param numberOfPlayers Anzahl an Spielern
     * @throws NotSupportedNumberOfPlayersException Wird geworfen wenn zuviele Spieler übergeben werden
     */
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
                throw new NotSupportedNumberOfPlayersException("Only 1 - 4 Players are Supported");
        }
        isInitialized = true;
    }

    /**
     * Gibt den Aktuellen Spieler (Player) zurück
     * @return Aktueller Player
     */
    public Player getCurrentPlayer(){
        return players[currentPlayerId];
    }

    /**
     * Gibt die aktuelle ID des Spielers zurück
     * @return ID des aktuellen Spielers
     */
    public int getCurrentPlayerId(){
        return currentPlayerId;
    }

    /**
     * Gibt die Spieler Nummer des aktuellen Spielers zurück
     * @return Aktuelle Spieler Nummer
     */
    private int getCurrentPlayerNumber(){
        return currentPlayerId + 1;
    }

    /**
     * Gibt die Spieler Nummer des aktuellen führenden Spielers zurück
     * @return Spieler Nummer des aktuell führenden Spielers
     */
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

    /**
     * Gibt den aktuellen Highscore zurück
     * @return
     */
    public long getHighScore(){
        return new HighScoreManager().getHighScore();
    }

    /**
     * Gibt den Score des Gewinners zurück und Speichert ihn wenn es sich um einen neuen HighScore handelt
     * @return Score des Gewinners
     */
    public int getWinningScore(Aui aui){

        int winningPlayerId = getWinningPlayerNumber() - 1;

        if(players[winningPlayerId].getScore() > new HighScoreManager().getHighScore()){
            aui.playSound(4, true);
            new HighScoreManager().setHighScore(players[winningPlayerId], "");
        }
        return players[winningPlayerId].getScore();
    }

    /**
     * Gibt alle Spieler zurück
     * @return alle Spieler
     */
    public Player[] getAllPlayer(){

        Player[] returnPlayers = new Player[playerCount];

        for(int i = 0; i < playerCount; i++){
            returnPlayers[i] = players[i];
        }

        return returnPlayers;
    }

    /**
     * Zieht dem aktuellen Spieler ein Leben/Ball ab
     */
    public void currentPlayerLoseRound(){
        players[currentPlayerId].loseBall();
    }

    /**
     * Wechselt zum nächsten Spieler
     */
    public void nextPlayer(){
        currentPlayerId++;
        if(currentPlayerId >= playerCount){
            currentPlayerId = 0;
        }
    }

    /**
     * Gibt zurück ob ein Spieler noch Bälle übrig hat
     * @return Bälle noch übrig?
     */
    public boolean isBallLeft(){
        boolean isBallLeft = false;
        for (Player player : players) {
            if(player != null && !isBallLeft){
                isBallLeft = player.getBallLifes() >= 0;
            }
        }
        return isBallLeft;
    }

    /**
     * Gibt einen Status string über den aktuellen Spieler zurück
     * @return String über den Status des aktuellen Spielers
     */
    public String getCurrentPlayerScoreString(){

        return "player " + getCurrentPlayerNumber() + "\n " + getCurrentPlayer().getScore();
    }
}
