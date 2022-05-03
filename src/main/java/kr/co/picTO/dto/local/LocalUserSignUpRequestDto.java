package kr.co.picTO.dto.local;

import kr.co.picTO.entity.local.BaseLocalUser;
import kr.co.picTO.entity.oauth2.BaseAuthRole;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;

@Getter
@Log4j2
public class LocalUserSignUpRequestDto {
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String provider;
    private BaseAuthRole role;

    @Builder
    public LocalUserSignUpRequestDto(String email, String password, String name, String nickName, String provider, BaseAuthRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.provider = provider;
        this.role = role;
    }

    public BaseLocalUser toEntity() {
        if(email.equals("picTOadmin@picTOMaker.com") && email != null) {
            String role = BaseAuthRole.ADMIN.getKey();
            String provider = BaseAuthRole.ADMIN.toString();

            log.info("Local User SRUDTO r, p : " + role + ", " + provider);

            return BaseLocalUser.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .nickName(nickName)
                    .provider(provider)
                    .role(BaseAuthRole.ADMIN)
                    .roles(Collections.singletonList(role))
                    .build();
        } else {
            return BaseLocalUser.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .nickName(nickName)
                    .provider(BaseAuthRole.LOCAL.toString())
                    .role(BaseAuthRole.LOCAL)
                    .roles(Collections.singletonList(BaseAuthRole.LOCAL.getKey()))
                    .build();
        }
    }
}
