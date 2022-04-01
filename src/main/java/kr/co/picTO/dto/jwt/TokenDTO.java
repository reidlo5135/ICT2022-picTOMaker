package kr.co.picTO.dto.jwt;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpireDate;
}
