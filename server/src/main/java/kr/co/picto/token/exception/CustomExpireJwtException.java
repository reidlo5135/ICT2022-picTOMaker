package kr.co.picto.token.exception;

import io.jsonwebtoken.JwtException;

public class CustomExpireJwtException extends JwtException {

    public CustomExpireJwtException(String message) {
        super(message);
    }

    public CustomExpireJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
