package kr.co.picTO.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.picTO.entity.oauth2.AccessToken;
import kr.co.picTO.entity.oauth2.BaseAuthRole;
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
    private Long tokenValidMillisecond = 60 * 60 * 1000L;
    private final BaseTokenRepo tokenRepo;
    private final CustomUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        log.info("Local Jwt Prov : " + secretKey);
    }
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);

        Date now = new Date();

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);

        String provider = roles.get(0);
        log.info("Local Jwt Prov prov : "  + provider + ", " + BaseAuthRole.ADMIN.getKey());

        AccessToken accessToken = AccessToken.builder()
                .access_token(jwt)
                .expires_in(claimsJws.getBody().getExpiration().getTime())
                .token_type("bearer")
                .refresh_token(null)
                .refresh_token_expires_in(0)
                .provider(provider)
                .build();
        tokenRepo.save(accessToken);

        log.info("Local Jwt Prov createToken : " + jwt);
        log.info("Local Jwt Prov Claims : " + claimsJws.getBody());
        log.info("Local Jwt Prov accessToken : " + accessToken);

        return jwt;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserpk(token));
        log.info("Local Jwt Prov userDetails : " + userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
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
        } catch (Exception e) {
            log.error("Local Jwt Prov validToken err : " + e.getMessage());
            return false;
        }
    }
}
