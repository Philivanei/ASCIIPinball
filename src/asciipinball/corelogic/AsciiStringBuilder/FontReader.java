package asciipinball.corelogic.AsciiStringBuilder;

import asciipinball.fonts.AsciiStringContainer;

/**
 * Ließt AsciiStringContainer ein
 */
public class FontReader {

    /**
     * Ließt den richtigen Buchstaben aus den AsciiStringContainern ein und gibt Ihn als String zurück
     * @param letter Buchstabe der gelesen werden soll
     * @param font Font in der der Buchstabe gelesen werden soll
     * @return String des Buchstaben in AsciiArt
     */
    public String getStringOfLetter(char letter, AsciiStringContainer font){
        switch (letter) {
            case 'A':
            case 'a':
                return font.getA();

            case 'B':
            case 'b':
                return font.getB();

            case 'C':
            case 'c':
                return font.getC();

            case 'D':
            case 'd':
                return font.getD();

            case 'E':
            case 'e':
                return font.getE();

            case 'F':
            case 'f':
                return font.getF();

            case 'G':
            case 'g':
                return font.getG();

            case 'H':
            case 'h':
                return font.getH();

            case 'I':
            case 'i':
                return font.getI();

            case 'J':
            case 'j':
                return font.getJ();

            case 'K':
            case 'k':
                return font.getK();

            case 'L':
            case 'l':
                return font.getL();

            case 'M':
            case 'm':
                return font.getM();

            case 'N':
            case 'n':
                return font.getN();

            case 'O':
            case 'o':
                return font.getO();

            case 'P':
            case 'p':
                return font.getP();

            case 'Q':
            case 'q':
                return font.getQ();

            case 'R':
            case 'r':
                return font.getR();

            case 'S':
            case 's':
                return font.getS();

            case 'T':
            case 't':
                return font.getT();

            case 'U':
            case 'u':
                return font.getU();

            case 'V':
            case 'v':
                return font.getV();

            case 'W':
            case 'w':
                return font.getW();

            case 'X':
            case 'x':
                return font.getX();

            case 'Y':
            case 'y':
                return font.getY();

            case 'Z':
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

            case '-':
                return font.getARROW();

            default:
                return font.getSPACE();

        }
    }

}
