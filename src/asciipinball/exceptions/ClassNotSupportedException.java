package asciipinball.exceptions;

public class ClassNotSupportedException extends Exception {

    private Object classThatCausedTheError;

    public ClassNotSupportedException(Object classThatCausedTheError) {
        this.classThatCausedTheError = classThatCausedTheError;
    }

    @Override
    public String toString() {
        return "ClassNotSupportedException: " + classThatCausedTheError + " isn't printable";
    }
}
