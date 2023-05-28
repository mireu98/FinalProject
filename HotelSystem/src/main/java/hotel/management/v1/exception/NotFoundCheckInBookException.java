package hotel.management.v1.exception;

public class NotFoundCheckInBookException extends RuntimeException {
    public NotFoundCheckInBookException(String message) {
        super(message);
    }
}
