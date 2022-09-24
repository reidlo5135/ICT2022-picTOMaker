package kr.co.picto.token.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    private boolean isExcludeURI(String uri) {
        log.info("Jwt filter excludeFilter uri : " + uri);
        String[] patterns = {
                "/v1/api/user/reissue",
                "/v1/api/user/signup",
                "/v1/api/oauth2/",
                "/oauth2/redirect/",
                "/kakao",
                "/naver",
                "/error",
                "/index.html",
                "/swagger-ui",
                "/swagger-resources",
                "/v3/api-docs",
                ".png"
        };
        for(String str : patterns) {
            if(uri.equals(str) || uri.contains(str)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Jwt filter request URI : " + ((HttpServletRequest) request).getRequestURI());
        if(!isExcludeURI(((HttpServletRequest) request).getRequestURI())) {
            String token = jwtProvider.resolveToken((HttpServletRequest) request);
            boolean isValid = jwtProvider.validationToken(token);
            log.info("Jwt filter token : " + token);
            log.info("Jwt filter isValid : " + isValid);
            if(token != null && isValid) {
                Authentication authentication = jwtProvider.getAuthentication(token);

                log.info("Jwt Filter authentication : " + authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
