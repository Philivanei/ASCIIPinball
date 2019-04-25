package asciipinball;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Menu {
    private boolean isMenuActive;

    public Menu() {
        isMenuActive = true;
        //GameView Setting
        GameView gameView = new GameView(15, 25, "Menu");
        gameView.setWindowsSize(GameView.WINDOWSIZE_LARGE);
        gameView.show();
        //GameView Setting end
        mainMenu(gameView);
        //playAnzahl = menu.start(gameView);
    }

    public int mainMenu(GameView gameView) {

        int playerNumber = 0;
        int[] row = {1, 4, 6, 8, 10, 4};
        int[] column = {2, 4, 4, 4, 4, 1};

        gameView.addToCanvas("Menu", row[0], column[0]);
        gameView.addToCanvas("1 Player", row[1], column[1]);
        gameView.addToCanvas("2 Player", row[2], column[2]);
        gameView.addToCanvas("3 Player", row[3], column[3]);
        gameView.addToCanvas("4 Player", row[4], column[4]);
        gameView.addToCanvas("->", row[5], column[5]);
        gameView.printCanvas();


        while (playerNumber == 0) {
            LinkedList<KeyEvent> keyEvent = gameView.getKeyEvents();
            try {
                System.out.println(keyEvent.getLast().getKeyCode());
                System.out.println(KeyEvent.KEY_PRESSED);
                System.out.println(keyEvent.getLast().getID());
                //moving "->" down
                if ((keyEvent.getLast().getKeyCode() == KeyEvent.VK_DOWN) && (keyEvent.getLast().getID() == KeyEvent.KEY_PRESSED)) {
                    gameView.clearCanvas();
                    gameView.addToCanvas("Menu", row[0], column[0]);
                    gameView.addToCanvas("1 Player", row[1], column[1]);
                    gameView.addToCanvas("2 Player", row[2], column[2]);
                    gameView.addToCanvas("3 Player", row[3], column[3]);
                    gameView.addToCanvas("4 Player", row[4], column[4]);
                    row[5] = row[5] + 2;
                    if (row[5] > 10) {
                        row[5] = 4;
                    }
                    gameView.addToCanvas("->", row[5], column[5]);
                    gameView.printCanvas();

                }
                //moving "->" up
                if ((keyEvent.getLast().getKeyCode() == KeyEvent.VK_UP) && (keyEvent.getLast().getID() == KeyEvent.KEY_PRESSED)) {
                    gameView.clearCanvas();
                    gameView.addToCanvas("Menu", row[0], column[0]);
                    gameView.addToCanvas("1 Player", row[1], column[1]);
                    gameView.addToCanvas("2 Player", row[2], column[2]);
                    gameView.addToCanvas("3 Player", row[3], column[3]);
                    gameView.addToCanvas("4 Player", row[4], column[4]);
                    row[5] = row[5] - 2;
                    if (row[5] < 4) {
                        row[5] = 10;
                    }
                    gameView.addToCanvas("->", row[5], column[5]);
                    gameView.printCanvas();

                }


                Thread.sleep(20);


            } catch (Exception e) {
            }
        }

        return 3;
    }
}