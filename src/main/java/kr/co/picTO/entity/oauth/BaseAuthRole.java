package kr.co.picTO.entity.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseAuthRole {
    GOOGLE("USER_GOOGLE", "구글 사용자"),
    NAVER("USER_NAVER", "네이버 사용자"),
    KAKAO("USER_KAKAO", "카카오 사용자"),
    LOCAL("USER_LOCAL", "로컬 사용자"),
    ADMIN("USER_ADMIN", "관리자");

    private final String key;
    private final String title;
}
