package kr.co.picTO.config.security;

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

@RequiredArgsConstructor
@Log4j2
public class LocalUserJwtAuthenticationFilter extends GenericFilterBean {
    private final LocalUserJwtProvider localUserJwtProvider;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = localUserJwtProvider.resolveToken((HttpServletRequest) request);
        boolean isValid = localUserJwtProvider.validationToken(token);
        log.info("Local Jwt Filter token : " + token);
        log.info("Local Jwt Filter isValid : " + isValid);

        if(token != null && isValid) {
            log.info("Local Jwt Filter token : " + token);
            Authentication authentication = localUserJwtProvider.getAuthentication(token);
            log.info("Local Jwt Filter authentication : " + authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
