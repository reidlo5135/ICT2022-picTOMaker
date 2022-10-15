package kr.co.picto.user.dto;

import kr.co.picto.user.TypeEnum;
import kr.co.picto.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private String email;
    private String provider;
    private String skeleton;
    private int thick;
    private String lineColor;
    private String backgroundColor;
    private String type;

    public User from() {
        return User.builder()
                .email(email)
                .provider(provider)
                .skeleton(skeleton)
                .thick(thick)
                .lineColor(lineColor)
                .backgroundColor(backgroundColor)
                .type(TypeEnum.valueOf(type))
                .build();
    }
}
