package asciipinball.exceptions;

public class ClassNotSupportedException extends RuntimeException {


    public ClassNotSupportedException() {
    }

    public ClassNotSupportedException(String message){
        super(message);
    }
}
