package asciipinball;

import asciipinball.GameTable;

public class Program {
    public static void main (String[] args){

        GameTable gameTable = new GameTable(29, 29);
        gameTable.drawAll();

        while(true){
            gameTable.simulateTick();
            gameTable.drawAll();
            try {
                Thread.sleep(200);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}