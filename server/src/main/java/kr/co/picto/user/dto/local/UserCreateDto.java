package kr.co.picto.user.dto.local;

import kr.co.picTO.user.domain.AccountStatus;
import kr.co.picTO.user.domain.local.User;
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
public class UserCreateDto {

    private String email;
    private String password;
    private String name;
    private String nickName;

    public User toEntity(PasswordEncoder passwordEncoder) {

        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickName(nickName)
                .provider("LOCAL")
                .status(AccountStatus.ACTIVE)
                .roles(Collections.singletonList("ROLE_LOCAL"))
                .build();
    }
}
