package kr.co.picto.user.application.social;

import kr.co.picto.user.domain.social.SocialUserRepository;
import kr.co.picto.user.dto.social.SocialUserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class SocialUserService {
    private final SocialUserRepository socialUserRepository;

    public String saveSocialUser(SocialUserLoginDto socialUserLoginDto) {
        if(socialUserRepository.findByEmailAndProvider(socialUserLoginDto.getEmail(), socialUserLoginDto.getProvider()).isEmpty()) socialUserRepository.save(socialUserLoginDto.toEntity());
        return "success";
    }
}
