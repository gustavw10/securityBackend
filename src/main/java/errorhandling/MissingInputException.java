
package errorhandling;

public class MissingInputException extends Exception {
    public MissingInputException (String message) {
        super(message);
    }
    public MissingInputException() {
        super("Make your password stronger");
    }
}
