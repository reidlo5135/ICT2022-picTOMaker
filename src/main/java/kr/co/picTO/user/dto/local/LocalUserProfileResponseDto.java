package kr.co.picTO.user.dto.local;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalUserProfileResponseDto {
    private String email;
    private String name;
    private String nickName;
    private String profile_image_url;
}
