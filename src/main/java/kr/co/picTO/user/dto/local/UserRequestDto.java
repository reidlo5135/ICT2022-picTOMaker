package kr.co.picTO.user.dto.local;

import kr.co.picTO.user.domain.local.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private String email;
    private String name;
    private String nickName;

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .nickName(nickName)
                .build();
    }
}
