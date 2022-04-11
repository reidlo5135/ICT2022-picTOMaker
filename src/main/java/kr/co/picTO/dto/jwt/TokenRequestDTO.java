package kr.co.picTO.dto.jwt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TokenRequestDTO {
    String accessToken;
    String refreshToken;

    @Builder
    public TokenRequestDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
