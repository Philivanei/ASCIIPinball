package asciipinball;

import asciipinball.corelogic.Player;

public class PlayerManager {
    Player[] players;

    public PlayerManager(byte numberOfPlayers) throws Exception {
        players = new Player[4];
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
}
