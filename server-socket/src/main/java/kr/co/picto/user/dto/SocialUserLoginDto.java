package kr.co.picto.user.dto;

import kr.co.picto.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserLoginDto {
    private String email;
    private String provider;

    public User toEntity() {
        return User.builder()
                .email(email)
                .provider(provider)
                .build();
    }
}
