package kr.co.picTO.member.dto.local;

import kr.co.picTO.member.domain.local.BaseLocalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Log4j2
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalUserSignUpRequestDto {

    private String email;
    private String password;
    private String name;
    private String nickName;

    public BaseLocalUser toEntity(PasswordEncoder passwordEncoder) {

        return BaseLocalUser.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickName(nickName)
                .provider("LOCAL")
                .roles(Collections.singletonList("ROLE_LOCAL"))
                .build();
    }
}
