package kr.co.picTO.dto.sign;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSocialLoginRequestDTO {
    private String accessToken;
}
