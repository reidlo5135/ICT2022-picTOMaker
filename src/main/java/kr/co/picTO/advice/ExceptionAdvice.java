package kr.co.picTO.advice;

import kr.co.picTO.advice.exception.*;
import kr.co.picTO.model.response.CommonResult;
import kr.co.picTO.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;
    private final MessageSource messageSource;

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(CEmailLoginFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailLoginFailedException(HttpServletRequest request, CEmailLoginFailedException e) {
        return responseService.getFailResult(
                Integer.parseInt(getMessage("emailLoginFailed.code")), getMessage("emailLoginFailed.msg"));
    }

    @ExceptionHandler(CEmailSignUpFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSignUpFailedCException(HttpServletRequest request, CEmailSignUpFailedException e) {
        return responseService.getFailResult(
                Integer.parseInt(getMessage("emailSignUpFailed.code")), getMessage("emailSignUpFailed.msg"));
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntrypointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(
                Integer.parseInt(getMessage("authenticationEntrypoint.code")), getMessage("authenticationEntrypoint.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult(
                Integer.parseInt(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }

    @ExceptionHandler(CCommunicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult communicationException(HttpServletRequest request, CCommunicationException e) {
        return responseService.getFailResult(
                Integer.parseInt(getMessage("communicationException.code")), getMessage("communicationException.msg")
        );
    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
