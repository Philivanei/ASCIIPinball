package asciipinball.exceptions;

public class NotSupportedNumberOfPlayersException extends RuntimeException {
    public NotSupportedNumberOfPlayersException() {
    }



    public NotSupportedNumberOfPlayersException(String message) {
        super(message);

    }
}
