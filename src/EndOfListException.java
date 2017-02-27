/**
 * Custom Exception called EndOfListException which
 * is thrown when the cursor reaches the end of the list
 */
public class EndOfListException extends Exception {

    public EndOfListException() {
    }

    public EndOfListException(String message) {
        super(message);
    }
}
