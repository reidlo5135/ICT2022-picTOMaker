package kr.co.picTO.member.dto.local;

import kr.co.picTO.member.domain.local.BaseLocalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalUserLoginRequestDto {
    private String email;
    private String password;

    public BaseLocalUser toUser(@NotNull PasswordEncoder passwordEncoder) {
        return BaseLocalUser.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
