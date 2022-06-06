package kr.co.picTO.config.security;

import io.jsonwebtoken.*;
import kr.co.picTO.advice.exception.CustomAuthenticationEntryPointException;
import kr.co.picTO.entity.oauth2.BaseAccessToken;
import kr.co.picTO.repository.BaseTokenRepo;
import kr.co.picTO.service.local.CustomUserDetailsService;
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

@RequiredArgsConstructor
@Component
@Log4j2
public class LocalUserJwtProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private String ROLES = "roles";
    private Long accessTokenValidMillisecond = 60 * 60 * 1000L;
    private Long refreshTokenValidMillisecond = 14 * 24 * 60 * 60 * 1000L;
    private final BaseTokenRepo tokenRepo;
    private final CustomUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        log.info("Local Jwt Prov : " + secretKey);
    }

    public BaseAccessToken createToken(String userPk, Long userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
        claims.put(ROLES, roles);

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
                .setExpiration(expire_refresh)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String provider = roles.get(0);
        log.info("Local Jwt Prov prov : "  + provider);
        log.info("Local Jwt Prov accessToken : " + accessToken);

        BaseAccessToken baseAccessToken = BaseAccessToken.builder()
                .local_user_id(userId)
                .access_token(accessToken)
                .expires_in(expire_access.getTime())
                .token_type("bearer")
                .refresh_token(refreshToken)
                .refresh_token_expires_in(expire_refresh.getTime())
                .provider(provider)
                .build();

        tokenRepo.save(baseAccessToken);

        return baseAccessToken;
    }

    public Authentication getAuthentication(String token) {

        Claims claims = parseClaims(token);

        if(claims.get(ROLES) == null)
            throw new CustomAuthenticationEntryPointException();

        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        log.info("Local Jwt Prov userDetails : " + userDetails);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getUserpk(String token) {
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
            log.error("Local Jwt Prov validToken err : " + e.toString());
            return false;
        }
    }
}
