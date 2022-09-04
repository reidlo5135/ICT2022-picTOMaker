package kr.co.picTO.auth.exception;

public class TokenNotFoundException extends RuntimeException{
    private static final String MESSAGE = "토큰이 없습니다.";

    public TokenNotFoundException() {
        super(MESSAGE);
    }
}
