package kr.co.picto.token.dto;

import kr.co.picTO.token.domain.AccessToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDto {

    private String accessToken;
    private String refreshToken;

    public AccessToken toEntity(String accessToken, String refreshToken) {
        return AccessToken.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();
    }
}
