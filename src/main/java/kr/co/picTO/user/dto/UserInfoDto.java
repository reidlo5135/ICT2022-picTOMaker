package kr.co.picTO.user.dto;

import kr.co.picTO.auth.WebToken;
import kr.co.picTO.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
public class UserInfoDto {
    public static final String ID_KEY = "id";
    public static final String EMAIL_KEY = "email";
    public static final String NICK_NAME_KEY = "nickName";
    public static final String INTRODUCTION_KEY = "introduction";
    public static final String PROFILE_IMAGE_KEY = "profile_image";

    private Long id;
    private String email;
    private String nickName;
    private String introduction;
    private String profile_image_url;

    @Builder
    public UserInfoDto(Long id, String email, String nickName, String introduction, String profile_image_url) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.introduction = introduction;
        this.profile_image_url = profile_image_url;
    }

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .introduction(user.getIntroduction())
                .profile_image_url(user.getProfile_image_url())
                .build();
    }

    public static UserInfoDto from(WebToken token) {
        return UserInfoDto.builder()
                .id(Long.parseLong(token.getPayload(ID_KEY)))
                .email(token.getPayload(EMAIL_KEY))
                .nickName(token.getPayload(NICK_NAME_KEY))
                .introduction(token.getPayload(INTRODUCTION_KEY))
                .profile_image_url(token.getPayload(PROFILE_IMAGE_KEY))
                .build();
    }

    public Map<String, String> toMap() {
        Map<String, String> result = new HashMap<>();
        result.put(ID_KEY, this.id.toString());
        result.put(EMAIL_KEY, this.email);
        result.put(NICK_NAME_KEY, this.nickName);

        return result;
    }
}
