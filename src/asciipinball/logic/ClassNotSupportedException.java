package asciipinball.logic;

public class ClassNotSupportedException extends Exception {
    public ClassNotSupportedException() {

    }

    @Override
    public String toString() {
        return "ClassNotSupportedException";
    }
}
