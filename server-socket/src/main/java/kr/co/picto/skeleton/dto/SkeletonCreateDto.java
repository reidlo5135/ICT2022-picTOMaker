package kr.co.picto.skeleton.dto;

import kr.co.picto.skeleton.TypeEnum;
import kr.co.picto.skeleton.domain.Skeleton;
import kr.co.picto.user.domain.social.SocialUser;
import kr.co.picto.user.domain.local.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkeletonCreateDto {
    private String coordinate;
    private int thick;
    private String lineColor;
    private String backgroundColor;
    private String type;

    public Skeleton toEntity(User user) {
        return Skeleton.builder()
                .coordinate(coordinate)
                .thick(thick)
                .lineColor(lineColor)
                .backgroundColor(backgroundColor)
                .type(TypeEnum.valueOf(type))
                .user(user)
                .build();
    }

    public Skeleton toEntity(SocialUser socialUser) {
        return Skeleton.builder()
                .coordinate(coordinate)
                .thick(thick)
                .lineColor(lineColor)
                .backgroundColor(backgroundColor)
                .type(TypeEnum.valueOf(type))
                .socialUser(socialUser)
                .build();
    }
}
