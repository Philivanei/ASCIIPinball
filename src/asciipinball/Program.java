package asciipinball;

import asciipinball.frontend.Menu;
import asciipinball.logic.PinballGame;

import java.util.concurrent.locks.LockSupport;

public class Program {
    public static void main (String[] args){

        PinballGame pinballGame = new PinballGame();
        long startTime = System.currentTimeMillis();
        pinballGame.printAll();
        long calculationIterator = 0;
        long maxCalculation = 80;

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