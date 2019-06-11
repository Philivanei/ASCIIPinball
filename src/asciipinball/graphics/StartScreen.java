package asciipinball.graphics;


import asciipinball.Settings;
import asciipinball.fonts.FontBig;

/**
 * Ein Fenster, das den Startbildschrim des Spiels ausgibt.
 */
public class StartScreen {
    private int countArrowRows;
    private byte playerNumber;

    /**
     * Erzeugt einen StartScreen.
     */
    public StartScreen() {

        playerNumber = 0;
        countArrowRows = Settings.PLAYER1_POSITION_Y;
    }

    /**
     * Gibt die Anzahl der gewünschten Spieler zurück.
     *
     * @return Anzahl der Spieler
     */
    //recognization which player has been selected
    public byte getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Gibt zurück, ob eine Spieleranzahl ausgewählt wurde.
     *
     * @return Boolscher Ausdruck, ob Spieler ausgewählt wurden.
     */
    public boolean isPlayerNumberSelected() {
        return playerNumber != 0;
    }

    /**
     * Erkennt, an welcher Stelle die Enter-Taste gedrückt wurde und setzt dementsprechend die Spieleranzahl.
     */
    //controls for the StartScreen
    public void enterDownPressed() {
        //logic which player has been selected
        if (countArrowRows == Settings.PLAYER1_POSITION_Y) {
            playerNumber = 1;

        } else if (countArrowRows == Settings.PLAYER2_POSITION_Y) {
            playerNumber = 2;

        } else if (countArrowRows == Settings.PLAYER3_POSITION_Y) {
            playerNumber = 3;

        } else if (countArrowRows == Settings.PLAYER4_POSITION_Y) {
            playerNumber = 4;
        }
    }

    /**
     * Wird aufgerufen, wenn die Pfeil nach unten Taste betätigt wurde und bewegt den Pfeil nach unten.
     */
    //moving an arrow up/ down to choose a player number
    public void arrowDownPressed() {
        countArrowRows += Settings.PLAYER_DISTANCE;
        if (countArrowRows > Settings.PLAYER4_POSITION_Y) {
            countArrowRows = Settings.PLAYER1_POSITION_Y;
        }
    }

    /**
     * Wird aufgerufen, wenn die Pfeil nach oben Taste betätigt wurde und bewegt den Pfeil nach oben.
     */
    public void arrowUpPressed() {
        countArrowRows -= Settings.PLAYER_DISTANCE;
        if (countArrowRows < Settings.PLAYER1_POSITION_Y) {
            countArrowRows = Settings.PLAYER4_POSITION_Y;
        }

    }


    /**
     * Gibt die verschiedenen Elemente an der gesetzten Position auf dem Startbildschirm aus.
     * @param gui Referenz auf das GUI Objekt des Spiels
     */
    //StartScreen appearance
    public void printStartScreen(Gui gui) {

        gui.addAsciiStringToCanvas("ASCII PINBALL", Settings.HEIGHT / 2 - 40, Settings.GAME_VIEW_WIDTH / 2, new FontBig());
        gui.addAsciiStringToCanvas("Player 1", Settings.PLAYER1_POSITION_Y, Settings.PLAYER_POSITION_X, new FontBig());
        gui.addAsciiStringToCanvas("Player 2", Settings.PLAYER2_POSITION_Y, Settings.PLAYER_POSITION_X, new FontBig());
        gui.addAsciiStringToCanvas("Player 3", Settings.PLAYER3_POSITION_Y, Settings.PLAYER_POSITION_X, new FontBig());
        gui.addAsciiStringToCanvas("Player 4", Settings.PLAYER4_POSITION_Y, Settings.PLAYER_POSITION_X, new FontBig());
        gui.addAsciiStringToCanvas("-", countArrowRows, Settings.PLAYER_POSITION_X - 50, new FontBig());
    }
}