package kr.co.picto.user.dto.social;

import kr.co.picto.user.domain.social.SocialUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserLoginDto {
    private String email;
    private String provider;

    public SocialUser toEntity() {
        return SocialUser.builder()
                .email(email)
                .provider(provider)
                .build();
    }
}
