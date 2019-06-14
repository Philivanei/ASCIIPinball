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

    public void playSound(int soundId){

        if(System.currentTimeMillis() - lastSoundTimeStamp > Settings.DEAF_TIME)
        {
            lastSoundTimeStamp = System.currentTimeMillis();
            switch (soundId){
                case 0:
                    gameView.playSound("standartBounce.wav",0);
                    break;
            }
        }
    }

}
