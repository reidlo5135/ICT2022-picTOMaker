package kr.co.picTO.dto.user;

import kr.co.picTO.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;

@Getter
public class UserSignUpRequestDTO {

    private String email;
    private String password;
    private String name;
    private String nickName;

    @Builder
    public UserSignUpRequestDTO(String email, String password, String name, String nickName) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
