package kr.co.picTO.user.dto.local;

import kr.co.picTO.user.domain.local.BaseLocalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalUserRequestDto {

    private String email;
    private String name;
    private String nickName;

    public BaseLocalUser toEntity() {
        return BaseLocalUser.builder()
                .email(email)
                .name(name)
                .nickName(nickName)
                .build();
    }
}
