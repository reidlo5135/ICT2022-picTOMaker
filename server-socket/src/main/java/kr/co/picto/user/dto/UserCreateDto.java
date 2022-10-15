package kr.co.picto.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String email;
    private String password;
    private String name;
    private String nickName;
}
