package kr.co.picto.user.application;

import kr.co.picto.common.application.CommonRequestService;
import kr.co.picto.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class SocialUserService {
    private final UserRepository userRepository;
    private final CommonRequestService commonRequestService;
    private final HttpHeaders httpHeaders;
    private HttpEntity httpEntity;
    private static final String COMMON_TARGET_URI = "https://www.pictomaker.com/v1/api/oauth2";
//    private static final String COMMON_TARGET_URI = "http://localhost:8080/v1/api/oauth2";

    public Map<String, String> requestSocialLogin(String token, String provider){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-AUTH-TOKEN", token);
        httpEntity = new HttpEntity(httpHeaders);
        Map<String, String> responseMap = commonRequestService.post(COMMON_TARGET_URI + "/login/" + provider, httpEntity);
        return responseMap;
    }

    public Map<String, String> requestSocialProfile(String token, String provider) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-AUTH-TOKEN", token);
        httpEntity = new HttpEntity(httpHeaders);
        Map<String, String> responseMap = commonRequestService.post(COMMON_TARGET_URI + "/info/" + provider, httpEntity);
        log.info("requestSocialProf responseMap : " + responseMap);
        return responseMap;
    }

    public Map<String, String> requestSocialLogout(String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-AUTH-TOKEN", token);
        httpEntity = new HttpEntity(httpHeaders);
        Map<String, String> responseMap = commonRequestService.post(COMMON_TARGET_URI + "/logout", httpEntity);
        return responseMap;
    }

    public Map<String, String> requestDeActive(String token, String provider) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-AUTH-TOKEN", token);
        httpEntity = new HttpEntity(httpHeaders);
        Map<String, String> responseMap = commonRequestService.post(COMMON_TARGET_URI + "/" + provider, httpEntity);
        return responseMap;
    }
}
