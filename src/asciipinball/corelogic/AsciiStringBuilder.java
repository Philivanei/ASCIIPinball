package asciipinball.corelogic;

import asciipinball.corelogic.players.AsciiStringContainer;

public class AsciiStringBuilder {

    public AsciiStringBuilder() {

    }

    public char[][] buildAsciiString(String string) {
        int maxColumns = 0;
        int maxRows = 0;
        int currentRows = 0;
        int totalRows = 0;

        for (int i = 0; i < string.length(); i++) {

            if(string.charAt(i) == '\n'){
                totalRows += maxRows;
                maxRows = 0;
            }else {
                maxColumns += countColumns(getString(string.charAt(i)));
                currentRows = countRows(getString(string.charAt(i)));
                if(currentRows > maxRows){
                    maxRows = currentRows;
                }
            }


        }
        totalRows += maxRows;

        char[][] returnArray = new char[totalRows][maxColumns];

        for (char[] array: returnArray) {
            for (char xyz: array) {
                xyz = 'X';
            }
        }


        int columnTracker = 0;
        int rowTracker = 0;
        int maxRowCountSinceWordwrap = 0;

        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) == '\n') {
                rowTracker += maxRowCountSinceWordwrap;
                maxRowCountSinceWordwrap = 0;
                columnTracker = 0;
            }else {
                char[][] currentAsciiChar = convertStringToArray(getString(string.charAt(i)));

                maxRowCountSinceWordwrap = currentAsciiChar.length > maxRowCountSinceWordwrap ? currentAsciiChar.length : maxRowCountSinceWordwrap;
                for (int column = columnTracker; column < columnTracker + currentAsciiChar[0].length; column++) {
                    for (int row = rowTracker; row < rowTracker + currentAsciiChar.length; row++) {
                        returnArray[row][column] = currentAsciiChar[row - rowTracker][column - columnTracker];
                    }
                }
                columnTracker += currentAsciiChar[0].length;
            }

        }

        return returnArray;
    }

    public String getString(char c) {
        switch (c) {
            case 'a':
                return AsciiStringContainer.A;

            case 'b':
                return AsciiStringContainer.B;

            case 'c':
                return AsciiStringContainer.C;

            case 'd':
                return AsciiStringContainer.D;

            case 'e':
                return AsciiStringContainer.E;

            case 'f':
                return AsciiStringContainer.F;

            case 'g':
                return AsciiStringContainer.G;

            case 'h':
                return AsciiStringContainer.H;

            case 'i':
                return AsciiStringContainer.I;

            case 'j':
                return AsciiStringContainer.J;

            case 'k':
                return AsciiStringContainer.K;

            case 'l':
                return AsciiStringContainer.L;

            case 'm':
                return AsciiStringContainer.M;

            case 'n':
                return AsciiStringContainer.N;

            case 'o':
                return AsciiStringContainer.O;

            case 'p':
                return AsciiStringContainer.P;

            case 'q':
                return AsciiStringContainer.Q;

            case 'r':
                return AsciiStringContainer.R;

            case 's':
                return AsciiStringContainer.S;

            case 't':
                return AsciiStringContainer.T;

            case 'u':
                return AsciiStringContainer.U;

            case 'v':
                return AsciiStringContainer.V;

            case 'w':
                return AsciiStringContainer.W;

            case 'x':
                return AsciiStringContainer.X;

            case 'y':
                return AsciiStringContainer.Y;

            case 'z':
                return AsciiStringContainer.Z;

            case '0':
                return AsciiStringContainer.N0;

            case '1':
                return AsciiStringContainer.N1;

            case '2':
                return AsciiStringContainer.N2;

            case '3':
                return AsciiStringContainer.N3;

            case '4':
                return AsciiStringContainer.N4;

            case '5':
                return AsciiStringContainer.N5;

            case '6':
                return AsciiStringContainer.N6;

            case '7':
                return AsciiStringContainer.N7;

            case '8':
                return AsciiStringContainer.N8;

            case '9':
                return AsciiStringContainer.N9;

            case ' ':
                return AsciiStringContainer.SPACE;

            default:
                return " ";

        }
    }

    public char[][] convertStringToArray(String string) {

        char[][] returnCharArray = new char[countRows(string)][countColumns(string)];

        boolean wordwrapDetected;
        int stringIterator = 0;

        for (int row = 0; row < returnCharArray.length; row++) {
            wordwrapDetected = false;
            for (int column = 0; column < returnCharArray[0].length; column++) {
                stringIterator++;
                if(stringIterator >= string.length()){
                    continue;
                }
                if (wordwrapDetected) {
                    returnCharArray[row][column] = ' ';
                    continue;
                }
                if (string.charAt(stringIterator) == '\n') {
                    //wordwrapDetected = true;
                    returnCharArray[row][column] = ' ';
                } else {
                    returnCharArray[row][column] = string.charAt(stringIterator);
                }
            }
        }
        return returnCharArray;
    }

    private int countRows(String string) {
        int numberOfRows = 1;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '\n') {
                numberOfRows++;
            }
        }
        return numberOfRows;
    }

    private int countColumns(String string) {
        int maxColumns = 0;
        int columnsInCurrentRow = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) != '\n') {
                columnsInCurrentRow++;
            } else {
                if (columnsInCurrentRow > maxColumns) {
                    maxColumns = columnsInCurrentRow;
                }
                columnsInCurrentRow = 0;
            }
        }

        return maxColumns;

    }
}
