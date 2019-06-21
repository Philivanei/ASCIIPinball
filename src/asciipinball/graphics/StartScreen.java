package asciipinball.graphics;


import asciipinball.Settings;
import asciipinball.fonts.FontBig;

/**
 * Ein Fenster, das den Startbildschrim des Spiels ausgibt.
 */
public class StartScreen {
    //sets the distance from one printed player to another
    private final int playerDistance = 10;
    //sets the Y position of the printed players
    //if you want to change the Y position just change player1PositionY
    private final int player1PositionY = 50;
    private final int player2PositionY = player1PositionY + playerDistance;
    private final int player3PositionY = player2PositionY + playerDistance;
    private final int player4PositionY = player3PositionY + playerDistance;

    private int countArrowRows;
    private byte playerNumber;

    /**
     * Erzeugt einen StartScreen.
     */
    public StartScreen() {

        playerNumber = 0;
        countArrowRows = player1PositionY;
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
        if (countArrowRows == player1PositionY) {
            playerNumber = 1;

        } else if (countArrowRows == player2PositionY) {
            playerNumber = 2;

        } else if (countArrowRows == player3PositionY) {
            playerNumber = 3;

        } else if (countArrowRows == player4PositionY) {
            playerNumber = 4;
        }
    }

    /**
     * Wird aufgerufen, wenn die Pfeil nach unten Taste betätigt wurde und bewegt den Pfeil nach unten.
     */
    //moving an arrow up/ down to choose a player number
    public void arrowDownPressed() {
        countArrowRows += playerDistance;
        if (countArrowRows > player4PositionY) {
            countArrowRows = player1PositionY;
        }
    }

    /**
     * Wird aufgerufen, wenn die Pfeil nach oben Taste betätigt wurde und bewegt den Pfeil nach oben.
     */
    public void arrowUpPressed() {
        countArrowRows -= playerDistance;
        if (countArrowRows < player1PositionY) {
            countArrowRows = player4PositionY;
        }

    }


    /**
     * Gibt die verschiedenen Elemente an der gesetzten Position auf dem Startbildschirm aus.
     *
     * @param gui Referenz auf das GUI Objekt des Spiels
     */
    //StartScreen appearance
    public void printStartScreen(Gui gui) {

        gui.addAsciiStringToCanvas("ASCII PINBALL", Settings.HEIGHT / 2 - 40, Settings.GAME_VIEW_WIDTH / 2, new FontBig(), false);

        //sets the X position of the printed players
        final int playerPositionX = Settings.GAME_VIEW_WIDTH / 2;

        gui.addAsciiStringToCanvas("Player 1", player1PositionY, playerPositionX, new FontBig(), false);
        gui.addAsciiStringToCanvas("Player 2", player2PositionY, playerPositionX, new FontBig(), false);
        gui.addAsciiStringToCanvas("Player 3", player3PositionY, playerPositionX, new FontBig(),false);
        gui.addAsciiStringToCanvas("Player 4", player4PositionY, playerPositionX, new FontBig(),false);
        gui.addAsciiStringToCanvas("-", countArrowRows, playerPositionX - 50, new FontBig(),false);
    }
}