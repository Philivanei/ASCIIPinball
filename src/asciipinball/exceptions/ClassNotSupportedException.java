package asciipinball.exceptions;

/**
 * Exception für nicht implementierte Klassen
 */
public class ClassNotSupportedException extends RuntimeException {

    /**
     * Erstellt eine neue ClassNotSupportedException
     */
    public ClassNotSupportedException() {
    }

    /**
     * Erstellt eine neue ClassNotSupportedException mit Nachricht
     */
    public ClassNotSupportedException(String message){
        super(message);
    }
}
