package kr.co.picTO.user.dto;

import kr.co.picTO.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserCreateDto {
    private String email;
    private String password;
    private String nickName;

    @Builder
    public UserCreateDto(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .build();
    }
}
