package kr.co.picTO.user.domain.social;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountRole {
    GOOGLE("GOOGLE", "구글 사용자"),
    NAVER("NAVER", "네이버 사용자"),
    KAKAO("KAKAO", "카카오 사용자"),
    LOCAL("LOCAL", "로컬 사용자"),
    ADMIN("ADMIN", "관리자");

    private final String key;
    private final String title;
}
