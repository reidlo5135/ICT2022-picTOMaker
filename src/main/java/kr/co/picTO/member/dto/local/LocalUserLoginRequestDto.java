package kr.co.picTO.member.dto.local;

import kr.co.picTO.member.domain.local.BaseLocalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalUserLoginRequestDto {
    private String email;
    private String password;

    public BaseLocalUser toEntity() {
        return BaseLocalUser.builder()
                .email(email)
                .password(password)
                .build();
    }
}
