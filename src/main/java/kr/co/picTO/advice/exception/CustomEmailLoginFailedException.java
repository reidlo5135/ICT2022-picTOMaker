package kr.co.picTO.advice.exception;

public class CustomEmailLoginFailedException extends RuntimeException{

    public CustomEmailLoginFailedException() {
        super();
    }

    public CustomEmailLoginFailedException(String message) {
        super(message);
    }

    public CustomEmailLoginFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
