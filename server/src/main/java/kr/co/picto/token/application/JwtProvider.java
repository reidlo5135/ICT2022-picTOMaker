package kr.co.picto.token.application;

import io.jsonwebtoken.*;
import kr.co.picto.common.exception.CustomAuthenticationEntryPointException;
import kr.co.picto.token.dto.TokenResponseDto;
import kr.co.picto.user.application.local.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Log4j2
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private final String ROLES = "roles";
    private final CustomUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public TokenResponseDto createToken(Long userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
        claims.put(ROLES, roles);

        long accessTokenValidMillisecond = 60 * 60 * 1000L;
        long refreshTokenValidMillisecond = 14 * 24 * 60 * 60 * 1000L;

        Date now = new Date();
        Date expire_access = new Date(now.getTime() + accessTokenValidMillisecond);
        Date expire_refresh = new Date(now.getTime() + refreshTokenValidMillisecond);

        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expire_access)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(expire_refresh)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenResponseDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireDate(accessTokenValidMillisecond)
                .build();
    }

    public Authentication getAuthentication(String token) {

        Claims claims = parseClaims(token);

        if(claims.get(ROLES) == null)
            throw new CustomAuthenticationEntryPointException();

        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        log.info("UserDetails : " + userDetails.getUsername());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validationToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
