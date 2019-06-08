package asciipinball;

public class NotConnectedLinesException extends Exception {
    public NotConnectedLinesException() {

    }

    @Override
    public String toString() {
        return "NotConnectedLinesException";
    }
}
