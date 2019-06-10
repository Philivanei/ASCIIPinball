package asciipinball.graphics;

import asciipinball.Settings;
import asciipinball.corelogic.players.PlayerManager;
import asciipinball.fonts.FontElectronic;

public class GameOverScreen {

    PlayerManager playerManager;

    public GameOverScreen(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    public void printGameOverScreen(Gui gui){
        gui.addAsciiStringToCanvas("GAME OVER", Settings.HEIGHT/2,Settings.GAMEVIEW_WIDTH/2, new FontElectronic());

    }
}
