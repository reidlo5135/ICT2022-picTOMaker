package kr.co.picto.community.exception;

public class CustomCommunityNotExistException extends RuntimeException{
    public CustomCommunityNotExistException() {
        super();
    }

    public CustomCommunityNotExistException(String message) {
        super(message);
    }

    public CustomCommunityNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
