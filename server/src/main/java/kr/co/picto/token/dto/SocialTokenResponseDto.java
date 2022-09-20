package kr.co.picto.token.dto;

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
public class SocialTokenResponseDto {
    private String accessToken;
    private String refreshToken;
}
