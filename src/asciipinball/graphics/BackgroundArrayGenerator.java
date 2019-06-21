package asciipinball.graphics;

import asciipinball.Settings;
import view.GameView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BackgroundArrayGenerator {

    private String backgroundFile = "asciiArt.txt";


    public BackgroundArrayGenerator(){

    }


    public char[][] getBackground(){

        String string = getStringFromFile();

        char[][] segment = getCharArrayOfSegment(string);
        char[][] returnArray = new char[Settings.HEIGHT][Settings.GAME_VIEW_WIDTH];

        for(int row = 0; row < returnArray.length; row++){
            for(int column = 0; column < returnArray[0].length; column++){
                returnArray[row][column] = segment[row%segment.length][column%segment[0].length];
            }
        }

        return returnArray;
    }


    private String getStringFromFile(){

        BufferedReader reader = new BufferedReader(new InputStreamReader(GameView.class.getResourceAsStream("/resources/"+backgroundFile)));

        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    private char[][] getCharArrayOfSegment(String string){
        int maxColumns = 0;
        int currentColumns = 0;
        int maxRows = 0;
        for(int i = 0; i < string.length(); i++){
            char c = string.charAt(i);
            currentColumns++;
            if(c == '\n'){
                maxRows++;

                if(currentColumns > maxColumns){
                    maxColumns = currentColumns;
                }

                currentColumns = 0;
            }
        }

        char[][] returnArray = new char[maxRows][maxColumns];

        int stringIterator = 0;
        boolean wordWrapDetected = false;

        for(int row = 0; row < returnArray.length; row++){
            for (int column = 0; column < returnArray[0].length; column++){
                if(stringIterator >= string.length()){
                    stringIterator = string.length()-1;
                }
                char c = string.charAt(stringIterator);
                if(column == 0){
                    wordWrapDetected = false;
                }
                if(c == '\n' && !wordWrapDetected){
                    wordWrapDetected = true;
                    stringIterator++; //to skip the \n
                }
                if(wordWrapDetected){
                    returnArray[row][column] = ' ';
                    continue;
                }
                returnArray[row][column] = c;
                stringIterator++;
            }
        }

        return returnArray;
    }

}
