package asciipinball.graphics;

import asciipinball.Settings;
import asciipinball.corelogic.players.PlayerManager;
import asciipinball.fonts.FontBig;
import asciipinball.fonts.FontElectronic;
import asciipinball.fonts.FontStraight;

public class GameOverScreen {

    private PlayerManager playerManager;

    public GameOverScreen(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    public void printGameOverScreen(Gui gui){
        gui.addAsciiStringToCanvas("GAME OVER", Settings.HEIGHT/2 - 10,Settings.GAMEVIEW_WIDTH/2, new FontElectronic());
        gui.addAsciiStringToCanvas("Winner\nPlayer "+playerManager.getWinningPlayerNumber()+"\n\n"+playerManager.getWinningScore(),Settings.HEIGHT/2+10, Settings.GAMEVIEW_WIDTH/2, new FontBig());
    }
}
