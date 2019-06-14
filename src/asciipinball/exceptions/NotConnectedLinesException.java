package asciipinball.exceptions;

public class NotConnectedLinesException extends RuntimeException {
    public NotConnectedLinesException() {

    }

    public NotConnectedLinesException(String message){
        super(message);
    }

}
