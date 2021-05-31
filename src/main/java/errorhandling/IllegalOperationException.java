package errorhandling;

public class IllegalOperationException extends Exception {

    public IllegalOperationException(String message) {
        super(message);
    }

    public IllegalOperationException() {
        super("Illegal operation.");
    }
}
