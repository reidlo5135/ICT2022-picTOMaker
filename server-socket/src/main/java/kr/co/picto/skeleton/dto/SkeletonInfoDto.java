package kr.co.picto.skeleton.dto;

import kr.co.picto.skeleton.domain.Skeleton;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import java.util.Locale;

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
                .type(skeleton.getType().toString().toLowerCase(Locale.ROOT))
                .build();
    }

    public JSONObject toJSON(SkeletonInfoDto skeletonInfoDto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("skeleton", skeletonInfoDto.getCoordinate());
        jsonObject.put("thick", skeletonInfoDto.getThick());
        jsonObject.put("lineColor", skeletonInfoDto.getLineColor());
        jsonObject.put("backgroundColor", skeletonInfoDto.getBackgroundColor());
        jsonObject.put("type", skeletonInfoDto.getType());
        return jsonObject;
    }
}
