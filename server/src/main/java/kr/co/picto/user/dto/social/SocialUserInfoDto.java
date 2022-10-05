package kr.co.picto.user.dto.social;

import kr.co.picto.user.domain.AccountRole;
import kr.co.picto.user.domain.AccountStatus;
import kr.co.picto.user.domain.social.SocialUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserInfoDto {
    private String email;
    private String name;
    private String profile_image_url;
    private String provider;

    public SocialUser from() {
        return SocialUser.builder()
                .email(email)
                .name(name)
                .picture(profile_image_url)
                .provider(provider)
                .role(AccountRole.valueOf(provider.toUpperCase(Locale.ROOT)))
                .status(AccountStatus.ACTIVE)
                .build();
    }
}
