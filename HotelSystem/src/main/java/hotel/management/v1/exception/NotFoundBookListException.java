package hotel.management.v1.exception;

public class NotFoundBookListException extends RuntimeException {
    public NotFoundBookListException(String message) {
        super(message);
    }
}
