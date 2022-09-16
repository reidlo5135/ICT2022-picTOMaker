package kr.co.picto.common.exception;

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
