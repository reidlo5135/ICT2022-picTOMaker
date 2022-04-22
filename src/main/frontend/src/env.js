export const GOOGLE_CLIENT_ID = "870944296075-94ro0s4520vfb8uk5ou73so5cffer979.apps.googleusercontent.com"
export const GOOGLE_CLIENT_SECRET = "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"

export const KAKAO_CLIENT_ID = "42c51ed9e78ced900811f39d27801209"
export const KAKAO_CLIENT_SECRET = "F8dmLtNSxlHCGCvyRWtsuQBGhU962fy5"

export const NAVER_CLIENT_ID = "JcblmorRb0KelprvLRqP"
export const NAVER_CLIENT_SECRET = "uV9NKE0OEV"

export const OAUTH_REDIRECT_URI = "http://localhost:8080/oauth2/signcallback/"

export const GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth?client_id=" + GOOGLE_CLIENT_ID +
    "&redirect_uri=" + OAUTH_REDIRECT_URI + "google" +
    "&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile" +
    "&response_type=token" +
    "&include_granted_scopes=true"

export const KAKAO_AUTH_URL = "https://kauth.kakao.com/oauth/authorize?client_id=" + KAKAO_CLIENT_ID +
    "&redirect_uri=" + OAUTH_REDIRECT_URI + "kakao" +
    "&response_type=code" +
    "&scope=profile_nickname,profile_image,account_email"

export const NAVER_AUTH_URL = "https://nid.naver.com/oauth2.0/authorize?client_id=" + NAVER_CLIENT_ID +
    "&redirect_uri=" + OAUTH_REDIRECT_URI + "naver" +
    "&response_type=token" +
    "&state=state"
