package kr.co.picTO.advice.exception;

public class CAuthenticationEntryPointException extends RuntimeException{

    public CAuthenticationEntryPointException() {
        super();
    }

    public CAuthenticationEntryPointException(String message) {
        super(message);
    }

    public CAuthenticationEntryPointException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
