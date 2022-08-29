package kr.co.picTO.user.exception;

public class CustomUserExistException extends RuntimeException {
    public CustomUserExistException() {
        super();
    }

    public CustomUserExistException(String message) {
        super(message);
    }

    public CustomUserExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
