package asciipinball.corelogic;

import asciipinball.GameView;
import asciipinball.objects.launchcontrol.LaunchControl;
import asciipinball.graphics.GameOverScreen;
import asciipinball.graphics.StartScreen;
import asciipinball.objects.flipperfinger.*;

import java.awt.event.KeyEvent;

/**
 * Verwaltet die Tasteneingaben des Nutzers
 */
public class Control {

    private LaunchControl launchControl;
    private FlipperFingerControl flipperFingerControl;
    private StartScreen startScreen;
    private GameOverScreen gameOverScreen;
    private GameView gameView;
    private PinballGame pinballGame;
    private boolean skipRequested;

    /**
     * Erstellt ein neues Control Object.
     * @param gameView GameView des Spiels
     * @param flipperFingerControl FlipperfingerControl des Spiels
     * @param launchControl LaunchControl des Spiels
     * @param startScreen StartScreen des Spiels
     * @param gameOverScreen GameOverScreen des Spiels
     * @param pinballGame referenz auf Hauptlogik des Spiels
     */
    public Control(GameView gameView, FlipperFingerControl flipperFingerControl, LaunchControl launchControl,
                   StartScreen startScreen, GameOverScreen gameOverScreen, PinballGame pinballGame) {

        this.flipperFingerControl = flipperFingerControl;
        this.launchControl = launchControl;
        this.startScreen = startScreen;
        this.gameView = gameView;
        this.pinballGame = pinballGame;
        this.gameOverScreen = gameOverScreen;
        skipRequested = false;
    }

    /**
     * Such nach neuen Tastenbefehlen und ruft abhängig der Taste die jeweilige(n) Methode(n) auf
     */
    public void updateControls() {

        KeyEvent[] keyEvents = gameView.getKeyEvents();

        if (keyEvents.length != 0) {

            for (KeyEvent keyEvent : keyEvents) {

                if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {

                    //keys for FlipperFingers and GameOverScreen
                    if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {

                        flipperFingerControl.onLeftDown();
                        gameOverScreen.arrowLeftPressed();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {

                        flipperFingerControl.onRightDown();
                        gameOverScreen.arrowRightPressed();


                        //key for launchControl
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {

                        launchControl.onSpaceDown();

                        //keys for StartScreen and GameOverScreen
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {

                        startScreen.arrowDownPressed();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {

                        startScreen.arrowUpPressed();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {

                        startScreen.enterDownPressed();
                        gameOverScreen.enterDownPressed();

                    } else if(keyEvent.getKeyCode() == KeyEvent.VK_R){
                        if(!skipRequested){
                            skipRequested = true;
                            pinballGame.skipRound();
                        }
                    }


                } else if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {

                    //keys for FlipperFingers
                    if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {

                        flipperFingerControl.onLeftUp();

                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {

                        flipperFingerControl.onRightUp();

                        //key for launchControl
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {

                        launchControl.onSpaceUp();
                    } else if(keyEvent.getKeyCode() == KeyEvent.VK_R){
                        skipRequested = false;
                    }
                }
            }
        }
    }
}
