package kr.co.picTO.advice.exception;

public class CEmailSignUpFailedException extends RuntimeException{

    public CEmailSignUpFailedException() {
        super();
    }

    public CEmailSignUpFailedException(String message) {
        super(message);
    }

    public CEmailSignUpFailedException(String mesasge, Throwable throwable) {
        super(mesasge, throwable);
    }
}
