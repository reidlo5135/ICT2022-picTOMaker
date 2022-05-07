package kr.co.picTO.dto.local;

import kr.co.picTO.entity.local.BaseLocalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocalUserLoginRequestDto {
    private String email;
    private String password;

    public BaseLocalUser toUser(PasswordEncoder passwordEncoder) {
        return BaseLocalUser.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
