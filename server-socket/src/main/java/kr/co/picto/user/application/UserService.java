package kr.co.picto.user.application;

import kr.co.picto.common.application.CommonRequestService;
import kr.co.picto.user.domain.UserRepository;
import kr.co.picto.user.dto.UserCreateDto;
import kr.co.picto.user.dto.UserLoginDto;
import kr.co.picto.user.dto.UserTokenRequestDto;
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
public class UserService {
    private final UserRepository userRepository;
    private final CommonRequestService commonRequestService;
    private final HttpHeaders httpHeaders;
    private HttpEntity httpEntity;
    private static final String COMMON_TARGET_URI = "https://www.pictomaker.com/v1/api/user";

    public Map<String, String> requestLogin(UserLoginDto userLoginDto) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity(userLoginDto, httpHeaders);
        Map<String, String> responseMap = commonRequestService.post(COMMON_TARGET_URI + "/login", httpEntity);
        if(responseMap.get("msg").equals("성공") && userRepository.findByEmailAndProvider(userLoginDto.getEmail(), userLoginDto.getProvider()).isEmpty()) userRepository.save(userLoginDto.toEntity());
        return responseMap;
    }

    public Map<String, String> requestSignUp(UserCreateDto userCreateDto) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity(userCreateDto, httpHeaders);
        Map<String, String> responseMap = commonRequestService.post(COMMON_TARGET_URI + "/signup", httpEntity);
        return responseMap;
    }

    public Map<String, String> requestInfo(String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity(token, httpHeaders);
        Map<String, String> responseMap = commonRequestService.post(COMMON_TARGET_URI + "/info", httpEntity);
        return responseMap;
    }

    public Map<String, String> requestReissue(UserTokenRequestDto tokenRequestDto) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity(tokenRequestDto, httpHeaders);
        Map<String, String> responseMap = commonRequestService.post(COMMON_TARGET_URI + "/reissue", httpEntity);
        return responseMap;
    }

    public Map<String, String> requestDelete(String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity(token, httpHeaders);
        Map<String, String> responseMap = commonRequestService.post(COMMON_TARGET_URI + "/", httpEntity);
        return responseMap;
    }

    public Map<String, String> requestLogout(String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity(token, httpHeaders);
        Map<String, String> responseMap = commonRequestService.post(COMMON_TARGET_URI + "/logout", httpEntity);
        return responseMap;
    }
}
