package kr.co.picto.skeleton.dto;

import kr.co.picto.skeleton.domain.Skeleton;
import kr.co.picto.skeleton.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkeletonResponseDto {
    private String coordinate;
    private int thick;
    private String lineColor;
    private String backgroundColor;
    private String type;

    public Skeleton toEntity() {
        return Skeleton.builder()
                .coordinate(coordinate)
                .thick(thick)
                .lineColor(lineColor)
                .backgroundColor(backgroundColor)
                .type(TypeEnum.valueOf(type))
                .build();
    }
}
