package asciipinball.corelogic;

import asciipinball.corelogic.players.AsciiStringContainer;

public class AsciiStringBuilder {

    public char[][] buildAsciiString(String string) {
        int maxColumns = 0;
        int maxRows = 0;
        int currentColumns = 0;

        for (int i = 0; i < string.length(); i++) {
            maxRows += countRows(getString(string.charAt(i)));
            currentColumns = countColumns(getString(string.charAt(i)));
            if(currentColumns > maxColumns){
                maxColumns = currentColumns;
            }
        }

        char[][] returnArray = new char[maxRows][maxColumns];



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
            for (int column = 0; column < returnCharArray[0].length; row++) {
                if (wordwrapDetected) {
                    continue;
                }
                stringIterator++;
                if (string.charAt(stringIterator) == '\n') {
                    wordwrapDetected = true;
                } else {
                    returnCharArray[row][column] = string.charAt(stringIterator);
                }
            }
        }
        return new char[][]{};
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
            if (string.charAt(i) != 'n') {
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
