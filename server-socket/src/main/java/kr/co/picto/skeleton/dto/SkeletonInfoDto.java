package kr.co.picto.skeleton.dto;

import kr.co.picto.skeleton.domain.Skeleton;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkeletonInfoDto {
    private String coordinate;
    private int thick;
    private String lineColor;
    private String backgroundColor;
    private String type;

    public static SkeletonInfoDto from(Skeleton skeleton) {
        return SkeletonInfoDto.builder()
                .coordinate(skeleton.getCoordinate())
                .thick(skeleton.getThick())
                .lineColor(skeleton.getLineColor())
                .backgroundColor(skeleton.getBackgroundColor())
                .type(skeleton.getType().toString())
                .build();
    }

    public Map toMap(SkeletonInfoDto skeletonInfoDto) {
        Map map = new HashMap();
        map.put("skeleton", skeletonInfoDto.getCoordinate());
        map.put("thick", skeletonInfoDto.getThick());
        map.put("lineColor", skeletonInfoDto.getLineColor());
        map.put("backgroundColor", skeletonInfoDto.getBackgroundColor());
        map.put("type", skeletonInfoDto.getType());
        return map;
    }
}
