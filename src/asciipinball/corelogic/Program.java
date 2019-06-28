package asciipinball.corelogic;

/**
 * Ein Pinball-Spiel im Ascii-Art-Style
 */
public class Program {

    private Program(){

    }

    /**
     * Aufzurufende Main Methode - Startet das Spiel
     * @param args Argumente
     */
    public static void main (String[] args){
        PinballGame pinballGame = new PinballGame();
        pinballGame.printAll();

        loop(pinballGame);
    }

    /**
     * Unendlicher Loop der das Spiel am Laufen hält
     * @param pinballGame Das Spiel das Laufen soll
     */
    public static void loop(PinballGame pinballGame){
        long startTime = System.currentTimeMillis();
        long calculationIterator = 0;
        long maxCalculation = 75;

        while (true) {

            //Limiting FPS to ~60 FPS (1 Frame every 17 ms)
            if ((System.currentTimeMillis() - startTime) > 17) {
                startTime = System.currentTimeMillis();
                pinballGame.printAll();
                calculationIterator = 0;
            }
            //Calculating max 75 ticks every Frame - when the computer can't keep up ticks start to drop instead of
            //Frames (Game gets slower not laggier!)
            if(calculationIterator < maxCalculation){
                pinballGame.simulateTick();
                calculationIterator++;
            }else{
                long timeToWait = 16 - (System.currentTimeMillis() - startTime);

                if(timeToWait > 0){
                    try {
                        Thread.sleep(timeToWait);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}