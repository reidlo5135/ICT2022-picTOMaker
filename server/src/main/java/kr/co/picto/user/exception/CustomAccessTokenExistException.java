package kr.co.picto.user.exception;

public class CustomAccessTokenExistException extends RuntimeException{
    public CustomAccessTokenExistException() {
    }

    public CustomAccessTokenExistException(String message) {
        super(message);
    }

    public CustomAccessTokenExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
