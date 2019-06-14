package asciipinball.graphics;

import asciipinball.Settings;
import asciipinball.corelogic.PinballGame;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.fonts.FontBig;
import asciipinball.fonts.FontElectronic;

/**
 * Ein Fenster, das den Endbildschrim des Spiels ausgibt.
 */
public class GameOverScreen {

    private PlayerManager playerManager;
    private PinballGame pinballGame;
    private int countArrowColumns;
    private boolean isActive;
    private long visibleHighScore;
    private int visibleWinnningScore;

    /**
     * Erstellt einen GameOverScreen.
     *
     * @param playerManager PlayerManager des Spiels
     * @param pinballGame   Referenz auf Hauptlogik des Spiels
     */
    public GameOverScreen(PlayerManager playerManager, PinballGame pinballGame) {
        isActive = false;
        this.playerManager = playerManager;
        this.pinballGame = pinballGame;
        countArrowColumns = Settings.GAME_OVER_OPTIONS_POSITION_X;
        visibleHighScore = -1;
        visibleWinnningScore = -1;
    }

    /**
     * Bewegt den Pfeil nach links.
     */
    public void arrowLeftPressed() {
        if (isActive) {
            countArrowColumns -= Settings.GAME_OVER_OPTIONS_DISTANCE;
            if (countArrowColumns < Settings.GAME_OVER_OPTIONS_POSITION_X) {
                countArrowColumns = Settings.GAME_OVER_OPTIONS_POSITION_X + Settings.GAME_OVER_OPTIONS_DISTANCE;
            }
        }
    }

    /**
     * Bewegt den Pfeil nach rechts.
     */
    public void arrowRightPressed() {
        if (isActive) {
            countArrowColumns += Settings.GAME_OVER_OPTIONS_DISTANCE;
            if (countArrowColumns > Settings.GAME_OVER_OPTIONS_POSITION_X + Settings.GAME_OVER_OPTIONS_DISTANCE) {
                countArrowColumns = Settings.GAME_OVER_OPTIONS_POSITION_X;
            }
        }
    }


    /**
     * Schließt oder startet das Spiel neu, abhängig von der aktuellen Cursor Position
     */
    //closes the program in the GameOverScreen when Quit is selected
    //restarts the game when Retry is selected
    public void enterDownPressed() {
        if (isActive) {
            if (countArrowColumns == Settings.GAME_OVER_OPTIONS_POSITION_X + Settings.GAME_OVER_OPTIONS_DISTANCE) {
                System.exit(0);

            } else if (countArrowColumns == Settings.GAME_OVER_OPTIONS_POSITION_X) {
                pinballGame.init();
            }
        }
    }

    private long getHighScore(){
        if(visibleHighScore < 0){
            visibleHighScore = playerManager.getHighScore();
        }
        return visibleHighScore;
    }

    private int getScoreOfWinner(){
        if(visibleWinnningScore < 0){
            visibleWinnningScore = playerManager.getWinningScore();
        }
        return visibleWinnningScore;
    }

    /**
     * Aktiviert den GameOverScreen.
     *
     * @param isActive Sichtbarkeit des GamOverScreens
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }


    /**
     * Gibt die verschiedenen Elemente an der gesetzten Position auf dem GameOverScreen aus.
     * @param gui Referenz auf das GUI Objekt des Spiels
     */
    public void printGameOverScreen(Gui gui) {
        gui.addAsciiStringToCanvas("GAME OVER", Settings.HEIGHT / 2 - 10, Settings.GAME_VIEW_WIDTH / 2, new FontElectronic());
        gui.addAsciiStringToCanvas("Winner\nPlayer " + playerManager.getWinningPlayerNumber() + "\n\n" + getScoreOfWinner(), Settings.HEIGHT / 2 + 10, Settings.GAME_VIEW_WIDTH / 2, new FontBig());
        gui.addAsciiStringToCanvas("Quit", Settings.GAME_OVER_OPTIONS_POSITION_Y, Settings.GAME_OVER_OPTIONS_POSITION_X + Settings.GAME_OVER_OPTIONS_DISTANCE, new FontBig());
        gui.addAsciiStringToCanvas("Retry", Settings.GAME_OVER_OPTIONS_POSITION_Y, Settings.GAME_OVER_OPTIONS_POSITION_X, new FontBig());
        gui.addAsciiStringToCanvas("-", Settings.GAME_OVER_OPTIONS_POSITION_Y, countArrowColumns - 35, new FontBig());
        gui.addAsciiStringToCanvas(Long.toString(getHighScore()), 30, Settings.GAME_VIEW_WIDTH / 2, new FontBig());
        gui.addAsciiStringToCanvas("HighScore", 20, Settings.GAME_VIEW_WIDTH / 2, new FontBig());

    }
}
