package asciipinball;

import asciipinball.frontend.Menu;
import asciipinball.logic.PinballGame;

public class Program {
    public static void main (String[] args){
        GameView gameView = new GameView(15, 25, "Menu");
        Menu menu = new Menu(gameView);

    }
}