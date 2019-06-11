package asciipinball.graphics;

import asciipinball.Settings;
import asciipinball.corelogic.PinballGame;
import asciipinball.corelogic.players.PlayerManager;
import asciipinball.fonts.FontBig;
import asciipinball.fonts.FontElectronic;

public class GameOverScreen {

    private PlayerManager playerManager;
    private PinballGame pinballGame;
    private int countArrowColumns;
    private boolean isActive;

    public GameOverScreen(PlayerManager playerManager, PinballGame pinballGame) {
        isActive = false;
        this.playerManager = playerManager;
        this.pinballGame = pinballGame;
        countArrowColumns = Settings.GAME_OVER_OPTIONS_POSITION_X;
    }

    public void arrowLeftPressed() {
        if (isActive) {
            countArrowColumns -= Settings.GAME_OVER_OPTIONS_DISTANCE;
            if (countArrowColumns < Settings.GAME_OVER_OPTIONS_POSITION_X) {
                countArrowColumns = Settings.GAME_OVER_OPTIONS_POSITION_X + Settings.GAME_OVER_OPTIONS_DISTANCE;
            }
        }
    }

    public void arrowRightPressed() {
        if (isActive) {
            countArrowColumns += Settings.GAME_OVER_OPTIONS_DISTANCE;
            if (countArrowColumns > Settings.GAME_OVER_OPTIONS_POSITION_X + Settings.GAME_OVER_OPTIONS_DISTANCE) {
                countArrowColumns = Settings.GAME_OVER_OPTIONS_POSITION_X;
            }
        }
    }

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

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void printGameOverScreen(Gui gui) {
        gui.addAsciiStringToCanvas("GAME OVER", Settings.HEIGHT / 2 - 10, Settings.GAME_VIEW_WIDTH / 2, new FontElectronic());
        gui.addAsciiStringToCanvas("Winner\nPlayer " + playerManager.getWinningPlayerNumber() + "\n\n" + playerManager.getWinningScore(), Settings.HEIGHT / 2 + 10, Settings.GAME_VIEW_WIDTH / 2, new FontBig());
        gui.addAsciiStringToCanvas("Quit", Settings.GAME_OVER_OPTIONS_POSITION_Y, Settings.GAME_OVER_OPTIONS_POSITION_X + Settings.GAME_OVER_OPTIONS_DISTANCE, new FontBig());
        gui.addAsciiStringToCanvas("Retry", Settings.GAME_OVER_OPTIONS_POSITION_Y, Settings.GAME_OVER_OPTIONS_POSITION_X, new FontBig());
        gui.addAsciiStringToCanvas("-", Settings.GAME_OVER_OPTIONS_POSITION_Y, countArrowColumns - 35, new FontBig());
    }
}
