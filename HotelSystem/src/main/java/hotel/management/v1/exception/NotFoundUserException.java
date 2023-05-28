package hotel.management.v1.exception;

public class NotFoundUserException extends RuntimeException {
	public NotFoundUserException(String message) {
        super(message);
    }
}
