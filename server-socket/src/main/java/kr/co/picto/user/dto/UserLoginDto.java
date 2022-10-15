package kr.co.picto.user.dto;

import kr.co.picto.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    private String email;
    private String password;
    private String provider;

    public User toEntity() {
        return User.builder()
                .email(email)
                .provider(provider)
                .build();
    }
}
