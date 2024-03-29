package kr.co.picto.user.dto;

import kr.co.picto.user.domain.local.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String email;
    private String provider;

    public User toEntity() {
        return User.builder()
                .email(email)
                .provider(provider)
                .build();
    }
}
