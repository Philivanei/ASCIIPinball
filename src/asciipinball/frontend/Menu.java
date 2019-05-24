package asciipinball.frontend;

import asciipinball.GameView;
import java.awt.event.KeyEvent;

public class Menu {
    private boolean isMenuActive;
    int playerNumber = 0;
    //initialize menu position
    int menuRow = 1, menuColumn = 2;
    int playerCount = 1;
    char[][] menuChars = {{'M', 'e', 'n', 'ü'}};
    char[][] player = {{'1', ' ', 'P', 'l', 'a', 'y', 'e', 'r'},
            {},
            {'2', ' ', 'P', 'l', 'a', 'y', 'e', 'r'},
            {},
            {'3', ' ', 'P', 'l', 'a', 'y', 'e', 'r'},
            {},
            {'4', ' ', 'P', 'l', 'a', 'y', 'e', 'r'}};
    char[][] arrow = {{'-', '>'}};
    int[] row = {1, 4, 4};
    int[] column = {2, 4, 1};

    public Menu(GameView gameView) {
        isMenuActive = true;
        //GameView Setting
        gameView.setWindowsSize(GameView.WINDOWSIZE_LARGE);
        gameView.show();
        mainMenu(gameView);
    }

    public int mainMenu(GameView gameView) {

        drawing(gameView);

        KeyEvent[] keyEvent;
        while (playerNumber == 0) {
            keyEvent = gameView.getKeyEvents();
            try {
                if (keyEvent.length != 0) {
                    //moving "->" down
                    if ((keyEvent[0].getKeyCode() == KeyEvent.VK_DOWN) && (keyEvent[0].getID() == KeyEvent.KEY_PRESSED)) {
                        row[2] += 2;
                        if (row[2] > 10) {
                            row[2] = 4;
                        }
                        drawing(gameView);
                        playerCount = (int) (0.5 * row[2] - 1);
                    }

                    //moving "->" up
                    if ((keyEvent[0].getKeyCode() == KeyEvent.VK_UP) && (keyEvent[0].getID() == KeyEvent.KEY_PRESSED)) {
                        row[2] -= 2;
                        if (row[2] < 4) {
                            row[2] = 10;
                        }
                        drawing(gameView);
                        playerCount = (int) (0.5 * row[2] - 1);
                    }
                    if ((keyEvent[0].getKeyCode() == KeyEvent.VK_ENTER) && (keyEvent[0].getID() == KeyEvent.KEY_PRESSED)) {
                        if (playerCount == 1) {
                            System.out.println("player 1");
                            //playerNumber = 1;
                            //return 1;
                        }
                        if (playerCount == 2) {
                            System.out.println("player 2");
                            //playerNumber = 2;
                            //return 2;
                        }
                        if (playerCount == 3) {
                            System.out.println("player 3");
                            //playerNumber = 3;
                            //return 3;
                        }
                        if (playerCount == 4) {
                            System.out.println("player 4");
                            //playerNumber = 4;
                            //return 4;
                        }
                    }

                    Thread.sleep(20);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 3;
    }

    public void drawing(GameView gameView) {
        gameView.clearCanvas();
        gameView.addToCanvas(menuChars, menuRow, menuColumn);
        gameView.addToCanvas(player, row[1], column[1]);
        gameView.addToCanvas(arrow, row[2], column[2]);
        gameView.printCanvas();
    }
}