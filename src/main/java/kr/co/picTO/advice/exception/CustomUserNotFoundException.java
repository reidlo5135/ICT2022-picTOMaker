package kr.co.picTO.advice.exception;

public class CustomUserNotFoundException extends RuntimeException{

    public CustomUserNotFoundException() {
        super();
    }

    public CustomUserNotFoundException(String message) {
        super(message);
    }

    public CustomUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
