package kr.co.picTO.member.dto.local;

import kr.co.picTO.member.domain.token.BaseAccessToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Locale;

@Getter
@Log4j2
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialTokenRequestDto {

    private Long id;
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private long refresh_token_expires_in;

    public BaseAccessToken toEntity(String provider) {
        return BaseAccessToken.builder()
                .id(id)
                .access_token(access_token)
                .token_type(token_type)
                .refresh_token(refresh_token)
                .expires_in(expires_in)
                .refresh_token_expires_in(refresh_token_expires_in)
                .provider(provider.toUpperCase(Locale.ROOT))
                .build();
    }
}
