package asciipinball;

public class Program {
    public static void main (String[] args){

        PinballGame pinballGame = new PinballGame(29, 29);
        pinballGame.drawAll();

        while(true){
            pinballGame.simulateTick();
            pinballGame.drawAll();
            try {
                Thread.sleep(200);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}