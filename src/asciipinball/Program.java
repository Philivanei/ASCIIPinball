package asciipinball;

import asciipinball.frontend.Menu;
import asciipinball.logic.PinballGame;

import java.util.concurrent.locks.LockSupport;

public class Program {
    public static void main (String[] args){
        PinballGame pinballGame = new PinballGame();
        pinballGame.printAll();

        loop(pinballGame);
    }

    public static void loop(PinballGame pinballGame){
        long startTime = System.currentTimeMillis();
        long calculationIterator = 0;
        long maxCalculation = 75;

        while (true) {

            if ((System.currentTimeMillis() - startTime) > 17) {
                startTime = System.currentTimeMillis();
                pinballGame.printAll();
                calculationIterator = 0;
            }
            if(calculationIterator < maxCalculation){
                pinballGame.simulateTick();
                calculationIterator++;
            }
        }
    }
}