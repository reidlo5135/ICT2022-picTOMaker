package kr.co.picTO.auth.exception;

public class UserAccountException extends RuntimeException{
    private static final String MESSAGE = "접근할 수 없는 유저입니다.";

    public UserAccountException() {
        super(MESSAGE);
    }
}
