package kr.co.picTO.advice.exception;

public class EmailSignUpFailedCException extends RuntimeException{

    public EmailSignUpFailedCException() {
        super();
    }

    public EmailSignUpFailedCException(String message) {
        super(message);
    }

    public EmailSignUpFailedCException(String mesasge, Throwable throwable) {
        super(mesasge, throwable);
    }
}
