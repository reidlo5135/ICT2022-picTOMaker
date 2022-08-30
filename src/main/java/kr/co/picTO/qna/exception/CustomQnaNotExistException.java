package kr.co.picTO.qna.exception;

public class CustomQnaNotExistException extends RuntimeException{
    public CustomQnaNotExistException() {
    }

    public CustomQnaNotExistException(String message) {
        super(message);
    }

    public CustomQnaNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
