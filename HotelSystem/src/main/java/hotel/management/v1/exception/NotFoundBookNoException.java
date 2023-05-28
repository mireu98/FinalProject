package hotel.management.v1.exception;

public class NotFoundBookNoException extends RuntimeException {
    public NotFoundBookNoException(String message) {
        super(message);
    }
}
