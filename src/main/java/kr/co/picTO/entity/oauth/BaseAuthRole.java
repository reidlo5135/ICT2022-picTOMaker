package kr.co.picTO.entity.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseAuthRole {
    GEUST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("USER_ADMIN", "관리자");

    private final String key;
    private final String title;
}
