package kr.co.picTO.file.exception;

public class CustomFileNotFoundException extends RuntimeException{
    public CustomFileNotFoundException() {
    }

    public CustomFileNotFoundException(String message) {
        super(message);
    }

    public CustomFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
