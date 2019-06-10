package asciipinball.graphics;


import asciipinball.Settings;
import asciipinball.fonts.FontBig;

public class StartScreen {
    private int countRows;
    private byte playerNumber;

    public StartScreen() {

        playerNumber = 0;
        countRows = Settings.PLAYER1_POSITION_Y;
    }


    //recognization which player has been selected
    public byte getPlayerNumber() {
        return playerNumber;
    }

    public boolean isPlayerNumberSelected() {
        return playerNumber != 0;
    }


    //controls for the StartScreen
    public void enterDownPressed() {
        //logic which player has been selected
        if (countRows == Settings.PLAYER1_POSITION_Y) {
            playerNumber = 1;

        } else if (countRows == Settings.PLAYER2_POSITION_Y) {
            playerNumber = 2;

        } else if (countRows == Settings.PLAYER3_POSITION_Y) {
            playerNumber = 3;

        } else if (countRows == Settings.PLAYER4_POSITION_Y) {
            playerNumber = 4;
        }
    }


    //moving an arrow up/ down to choose a player number
    public void arrowDownPressed() {
        countRows += Settings.PLAYER_DISTANCE;
        if (countRows > Settings.PLAYER4_POSITION_Y) {
            countRows = Settings.PLAYER1_POSITION_Y;
        }
    }

    public void arrowUpPressed() {
        countRows -= Settings.PLAYER_DISTANCE;
        if (countRows < Settings.PLAYER1_POSITION_Y) {
            countRows = Settings.PLAYER4_POSITION_Y;
        }

    }


    //StartScreen appeareance
    public void printStartScreen(Gui gui) {

        gui.addAsciiStringToCanvas("ASCII PINBALL", Settings.HEIGHT / 2 - 40, Settings.GAMEVIEW_WIDTH / 2, new FontBig());
        gui.addAsciiStringToCanvas("Player 1", Settings.PLAYER1_POSITION_Y, Settings.PLAYER_POSITION_X, new FontBig());
        gui.addAsciiStringToCanvas("Player 2", Settings.PLAYER2_POSITION_Y, Settings.PLAYER_POSITION_X, new FontBig());
        gui.addAsciiStringToCanvas("Player 3", Settings.PLAYER3_POSITION_Y, Settings.PLAYER_POSITION_X, new FontBig());
        gui.addAsciiStringToCanvas("Player 4", Settings.PLAYER4_POSITION_Y, Settings.PLAYER_POSITION_X, new FontBig());
        gui.addAsciiStringToCanvas("-", countRows, Settings.PLAYER_POSITION_X - 50, new FontBig());
    }
}