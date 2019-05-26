package asciipinball;

import asciipinball.frontend.Menu;
import asciipinball.logic.PinballGame;

public class Program {
    public static void main (String[] args){

        PinballGame pinballGame = new PinballGame();
        long millis = System.currentTimeMillis();
        pinballGame.printAll();

        while (true){

            if((System.currentTimeMillis() - millis) > 17){
                millis = System.currentTimeMillis();
                pinballGame.printAll();
            }
            pinballGame.simulateTick();
        }


    }
}