package kr.co.picTO.auth.advice;

import kr.co.picTO.auth.WebToken;
import kr.co.picTO.auth.exception.TokenNotFoundException;
import kr.co.picTO.user.dto.UserInfoDto;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static kr.co.picTO.auth.advice.LoggedInInterceptor.TOKEN;

public class LoggedInUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoggedInUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String token = getToken(request);

        return UserInfoDto.from(WebToken.from(token));
    }

    private String getToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(TOKEN))
                .findFirst()
                .orElseThrow(TokenNotFoundException::new).getValue();
    }
}
