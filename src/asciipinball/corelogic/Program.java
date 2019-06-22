package asciipinball.corelogic;

/**
 * Ein Pinball-Spiel im Ascii-Art-Style
 */
public class Program {

    /**
     * Aufzurufende Main Methode - Startet das Spiel
     * @param args argumente
     */
    public static void main (String[] args){
        PinballGame pinballGame = new PinballGame();
        pinballGame.printAll();

        loop(pinballGame);
    }

    /**
     * Unendlicher Loop der das Spiel am leben hält
     * @param pinballGame Das spiel das Laufen soll
     */
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

                long timeToWait = 16 - (System.currentTimeMillis() - startTime);

                if(timeToWait > 0){
                    try {
                        Thread.sleep(timeToWait);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                pinballGame.simulateTick();
                calculationIterator++;
            }
        }
    }
}