package kr.co.picTO.dto.local;

import kr.co.picTO.entity.local.BaseLocalUser;
import kr.co.picTO.entity.oauth2.BaseAuthRole;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;

@Getter
public class LocalUserSignUpRequestDto {
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String provider;

    @Builder
    public LocalUserSignUpRequestDto(String email, String password, String name, String nickName, String provider) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.provider = provider;
    }

    public BaseLocalUser toEntity() {
        return BaseLocalUser.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickName(nickName)
                .provider(BaseAuthRole.LOCAL.toString())
                .roles(Collections.singletonList(BaseAuthRole.LOCAL.getKey()))
                .build();
    }
}
