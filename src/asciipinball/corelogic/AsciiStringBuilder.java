package asciipinball.corelogic;

import asciipinball.fonts.AsciiStringContainer;

public class AsciiStringBuilder<Font extends  AsciiStringContainer> {

    private Font font;

    public AsciiStringBuilder(Font font) {
        this.font = font;
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
            for (char c: array) {
                c = ' ';
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
                return font.getA();

            case 'b':
                return font.getB();

            case 'c':
                return font.getC();

            case 'd':
                return font.getD();

            case 'e':
                return font.getE();

            case 'f':
                return font.getF();

            case 'g':
                return font.getG();

            case 'h':
                return font.getH();

            case 'i':
                return font.getI();

            case 'j':
                return font.getJ();

            case 'k':
                return font.getK();

            case 'l':
                return font.getL();

            case 'm':
                return font.getM();

            case 'n':
                return font.getN();

            case 'o':
                return font.getO();

            case 'p':
                return font.getP();

            case 'q':
                return font.getQ();

            case 'r':
                return font.getR();

            case 's':
                return font.getS();

            case 't':
                return font.getT();

            case 'u':
                return font.getU();

            case 'v':
                return font.getV();

            case 'w':
                return font.getW();

            case 'x':
                return font.getX();

            case 'y':
                return font.getY();

            case 'z':
                return font.getZ();

            case '0':
                return font.get0();

            case '1':
                return font.get1();

            case '2':
                return font.get2();

            case '3':
                return font.get3();

            case '4':
                return font.get4();

            case '5':
                return font.get5();

            case '6':
                return font.get6();

            case '7':
                return font.get7();

            case '8':
                return font.get8();

            case '9':
                return font.get9();

            case ' ':
                return font.getSPACE();

            default:
                return " ";

        }
    }

    public char[][] convertStringToArray(String string) {

        char[][] returnCharArray = new char[countRows(string)][countColumns(string)];

        int stringIterator = -1;

        for (int row = 0; row < returnCharArray.length; row++) {
            for (int column = 0; column < returnCharArray[0].length; column++) {
                stringIterator++;
                if(stringIterator >= string.length()){
                    continue;
                }
                if (string.charAt(stringIterator) == '\n') {
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
                columnsInCurrentRow++;
                if (columnsInCurrentRow > maxColumns) {
                    maxColumns = columnsInCurrentRow;
                }
                columnsInCurrentRow = 0;
            }
        }

        return maxColumns;

    }
}