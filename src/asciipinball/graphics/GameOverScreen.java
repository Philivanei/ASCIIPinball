package asciipinball.graphics;

import asciipinball.Settings;
import asciipinball.corelogic.PinballGame;
import asciipinball.playersandscore.PlayerManager;
import asciipinball.fonts.FontBig;
import asciipinball.fonts.FontElectronic;
import asciipinball.sounds.Aui;

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
    private Aui aui;
    private final int gameOverOptionsPositionX = 45;
    private final int gameOverOptionsDistance = 150;

    /**
     * Erstellt einen GameOverScreen.
     *
     * @param playerManager PlayerManager des Spiels
     * @param pinballGame   Referenz auf Hauptlogik des Spiels
     */
    public GameOverScreen(PlayerManager playerManager, PinballGame pinballGame, Aui aui) {
        isActive = false;
        this.playerManager = playerManager;
        this.pinballGame = pinballGame;
        this.aui = aui;
        countArrowColumns = gameOverOptionsPositionX;
        visibleHighScore = -1;
        visibleWinnningScore = -1;
    }


    public boolean isActive() {
        return isActive;
    }

    /**
     * Bewegt den Pfeil nach links.
     */
    public void arrowLeftPressed() {
        if (isActive) {
            countArrowColumns -= gameOverOptionsDistance;
            if (countArrowColumns < gameOverOptionsPositionX) {
                countArrowColumns = gameOverOptionsPositionX + gameOverOptionsDistance;
            }
        }
    }

    /**
     * Bewegt den Pfeil nach rechts.
     */
    public void arrowRightPressed() {
        if (isActive) {
            countArrowColumns += gameOverOptionsDistance;
            if (countArrowColumns > gameOverOptionsPositionX + gameOverOptionsDistance) {
                countArrowColumns = gameOverOptionsPositionX;
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
            if (countArrowColumns == gameOverOptionsPositionX + gameOverOptionsDistance) {
                System.exit(0);

            } else if (countArrowColumns == gameOverOptionsPositionX) {
                pinballGame.init();
            }
        }
    }

    private long getHighScore() {
        if (visibleHighScore < 0) {
            visibleHighScore = playerManager.getHighScore();
        }
        return visibleHighScore;
    }

    private int getScoreOfWinner() {
        if (visibleWinnningScore < 0) {
            visibleWinnningScore = playerManager.getWinningScore(aui);
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
     *
     * @param gui Referenz auf das GUI Objekt des Spiels
     */
    public void printGameOverScreen(Gui gui) {

        gui.addAsciiStringToCanvas("GAME OVER", Settings.HEIGHT / 2 - 10, Settings.GAME_VIEW_WIDTH / 2, new FontElectronic());
        gui.addAsciiStringToCanvas("Winner\nPlayer " + playerManager.getWinningPlayerNumber() + "\n\n" + getScoreOfWinner(),
                Settings.HEIGHT / 2 + 10, Settings.GAME_VIEW_WIDTH / 2, new FontBig());

        final int gameOverOptionsPositionY = 120;
        gui.addAsciiStringToCanvas("Quit", gameOverOptionsPositionY, gameOverOptionsPositionX + gameOverOptionsDistance, new FontBig());
        gui.addAsciiStringToCanvas("Retry", gameOverOptionsPositionY, gameOverOptionsPositionX, new FontBig());
        gui.addAsciiStringToCanvas("-", gameOverOptionsPositionY, countArrowColumns - 35, new FontBig());
        gui.addAsciiStringToCanvas(Long.toString(getHighScore()), 30, Settings.GAME_VIEW_WIDTH / 2, new FontBig());
        gui.addAsciiStringToCanvas("HighScore", 20, Settings.GAME_VIEW_WIDTH / 2, new FontBig());

    }
}
