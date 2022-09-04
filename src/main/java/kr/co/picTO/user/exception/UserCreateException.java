package kr.co.picTO.user.exception;

public class UserCreateException extends RuntimeException{
    private static final String MESSAGE = "입력값을 확인해주세요";

    public UserCreateException(Exception e) {
        super(MESSAGE);
    }
}
