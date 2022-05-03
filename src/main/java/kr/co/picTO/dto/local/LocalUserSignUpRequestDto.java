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

    @Builder
    public LocalUserSignUpRequestDto(String email, String password, String name, String nickName, String provider) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.provider = provider;
    }

    public BaseLocalUser toEntity() {
        String provider = null;

        if(email.equals("picTOadmin@picTOMaker.com") && email != null) {
            provider = BaseAuthRole.ADMIN.toString();
            log.info("Local User SRUDTO provider : " + provider);

            return BaseLocalUser.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .nickName(nickName)
                    .provider(provider)
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        } else {
            provider = BaseAuthRole.LOCAL.toString();
            log.info("Local User SRUDTO provider : " + provider);

            return BaseLocalUser.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .nickName(nickName)
                    .provider(provider)
                    .roles(Collections.singletonList("ROLE_LOCAL"))
                    .build();
        }
    }
}
