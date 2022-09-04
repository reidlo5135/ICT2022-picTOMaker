package kr.co.picTO.auth.exception;

public class OauthException extends RuntimeException{
    private static final String MESSAGE = "아이디와 비밀번호를 확인하세요";

    public OauthException() {
        super(MESSAGE);
    }
}
