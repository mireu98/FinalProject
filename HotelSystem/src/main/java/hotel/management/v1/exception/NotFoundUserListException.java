package hotel.management.v1.exception;

public class NotFoundUserListException extends RuntimeException {
    public NotFoundUserListException(String message) {
        super(message);
    }
}
