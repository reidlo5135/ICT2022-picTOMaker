package kr.co.picTO.advice.exception;

public class EmailLoginFailedCException extends RuntimeException{

    public EmailLoginFailedCException() {
        super();
    }

    public EmailLoginFailedCException(String message) {
        super(message);
    }

    public EmailLoginFailedCException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
