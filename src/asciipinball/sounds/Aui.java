package asciipinball.sounds;

import asciipinball.Settings;
import view.GameView;

/**
 * Verwaltung der Audioausgaben
 */
public class Aui {
    private long lastSoundTimeStamp;
    private GameView gameView;

    public Aui (GameView gameView){
        this.gameView = gameView;
    }

    public void playSound(int soundId, boolean isHighPriority){

        if((System.currentTimeMillis() - lastSoundTimeStamp > Settings.DEAF_TIME)||isHighPriority)
        {
            lastSoundTimeStamp = System.currentTimeMillis();
            switch (soundId){
                case 0:
                    gameView.playSound("standartBounce.wav",0);
                    break;
                case 1:
                    gameView.playSound("bumperBounce.wav",0);
                    break;
                case 2:
                    gameView.playSound("pointDoorsScored.wav", 0);
                    break;
                case 3:
                    gameView.playSound("gameOver.wav",0);
                    break;
                case 4:
                    gameView.playSound("newHighscore.wav", 2500);
                    break;
                case 5:
                    gameView.playSound("pointDoorSwitch.wav",0);
                    break;
                default:
                    break;
            }
        }
    }
}
