package kr.co.picTO.advice.exception;

public class CustomSocialAgreementException extends RuntimeException{
    public CustomSocialAgreementException() { super();
    }

    public CustomSocialAgreementException(String message) {
        super(message);
    }

    public CustomSocialAgreementException(String message, Throwable cause) {
        super(message, cause);
    }
}
