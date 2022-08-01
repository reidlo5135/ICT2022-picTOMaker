package kr.co.picTO.dto.token;

import kr.co.picTO.entity.oauth2.BaseAccessToken;
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
public class LocalTokenDto {

    private String accessToken;
    private String refreshToken;

    public BaseAccessToken toEntity(String accessToken, String refreshToken) {
        return BaseAccessToken.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();
    }
}
