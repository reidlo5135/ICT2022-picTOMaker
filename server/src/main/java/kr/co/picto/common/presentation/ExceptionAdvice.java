package kr.co.picto.common.presentation;

import kr.co.picto.common.application.ResponseService;
import kr.co.picto.common.domain.CommonResult;
import kr.co.picto.common.exception.CustomAuthenticationEntryPointException;
import kr.co.picto.common.exception.CustomCommunicationException;
import kr.co.picto.common.exception.CustomSocialAgreementException;
import kr.co.picto.community.exception.CustomCommunityNotExistException;
import kr.co.picto.file.exception.CustomFileNotFoundException;
import kr.co.picto.qna.exception.CustomQnaNotExistException;
import kr.co.picto.token.exception.CustomExpireJwtException;
import kr.co.picto.user.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResponseService responseService;
    private final MessageSource messageSource;

    /***
     * -9999
     * default Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return responseService.getFailResult
                (Integer.parseInt(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    /***
     * -1000
     * 유저를 찾지 못했을 때 발생시키는 예외
     */
    @ExceptionHandler(CustomUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CustomUserNotFoundException e) {
        e.printStackTrace();
        return responseService.getFailResult(Integer.parseInt(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    /***
     * -1001
     * 유저 이메일 로그인 실패 시 발생시키는 예외
     */
    @ExceptionHandler(CustomEmailLoginFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailLoginFailedException(HttpServletRequest request, CustomEmailLoginFailedException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("emailLoginFailed.code")), getMessage("emailLoginFailed.msg"));
    }

    /***
     * -1002
     * 회원 가입 시 이미 로그인 된 이메일인 경우 발생 시키는 예외
     */
    @ExceptionHandler(CustomEmailSignUpFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSignUpFailedCException(HttpServletRequest request, CustomEmailSignUpFailedException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("emailSignUpFailed.code")), getMessage("emailSignUpFailed.msg"));
    }

    /**
     * -1003
     * 전달한 Jwt 이 정상적이지 않은 경우 발생 시키는 예외
     */
    @ExceptionHandler(CustomAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntrypointException(HttpServletRequest request, CustomAuthenticationEntryPointException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("authenticationEntrypoint.code")), getMessage("authenticationEntrypoint.msg"));
    }

    /**
     * -1004
     * 권한이 없는 리소스를 요청한 경우 발생 시키는 예외
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }

    /***
     * -1007
     * Social 인증 과정에서 문제 발생하는 에러
     */
    @ExceptionHandler(CustomCommunicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult communicationException(HttpServletRequest request, CustomCommunicationException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("communicationException.code")), getMessage("communicationException.msg")
        );
    }

    /***
     * -1008
     * 기 가입자 에러
     */
    @ExceptionHandler(CustomUserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected CommonResult existUserException(HttpServletRequest request, CustomUserExistException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("userExistException.code")), getMessage("userExistException.msg")
        );
    }

    /***
     * -1009
     * 소셜 로그인 시 필수 동의항목 미동의시 에러
     */
    @ExceptionHandler(CustomSocialAgreementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult socialAgreementException(HttpServletRequest request, CustomSocialAgreementException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("agreementException.code")), getMessage("agreementException.msg")
        );
    }

    /***
     *
     * -1010
     * 커뮤티니 등록된 게시물이 존재하지 않을 시 에러
     */
    @ExceptionHandler(CustomCommunityNotExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult communityNotExsitException(HttpServletRequest request, CustomCommunityNotExistException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("communityNotExistException.code")), getMessage("communityNotExistException.msg")
        );
    }

    /***
     *
     * -1011
     * 동일 액세스 토큰이 이미 존재할 시 에러
     */
    @ExceptionHandler(CustomAccessTokenExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult existAccessTokenException(HttpServletRequest request, CustomAccessTokenExistException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("existAccessToken.code")), getMessage("existAcessToken.msg")
        );
    }

    /***
     *
     * -1012
     * 파일이 존재하지 않을 시 에러
     */
    @ExceptionHandler(CustomFileNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult fileNotFoundException(HttpServletRequest request, CustomFileNotFoundException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("fileNotFoundException.code")), getMessage("fileNotFoundException.msg")
        );
    }

    /***
     *
     * -1013
     * QnA가 존재하지 않을 시 에러
     */
    @ExceptionHandler(CustomQnaNotExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult qnaNotExistException(HttpServletRequest request, CustomQnaNotExistException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("qnaNotExistException.code")), getMessage("qnaNotExistException.msg")
        );
    }

    /***
     *
     * -1014
     * AccessToken 만료 시 에러
     */
    @ExceptionHandler({CustomExpireJwtException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessTokenExpiredException(HttpServletRequest request, CustomExpireJwtException e) {
        e.printStackTrace();
        return responseService.getFailResult(
                Integer.parseInt(getMessage("accessTokenExpiredException.code")), getMessage(e.getMessage())
        );
    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
