package kr.co.picTO.qna.application;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.qna.domain.UserQnaRepository;
import kr.co.picTO.qna.dto.UserQnaRequestDto;
import kr.co.picTO.user.domain.local.User;
import kr.co.picTO.user.domain.local.UserRepository;
import kr.co.picTO.user.domain.social.SocialUser;
import kr.co.picTO.user.domain.social.SocialUserRepository;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserQnaService {
    private final UserQnaRepository userQnaRepository;
    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;
    private final ResponseService responseService;

    public SingleResult<Long> registerQnA(UserQnaRequestDto userQnaRequestDto, String provider) {
        Long result = null;
        if(provider != null && provider.equals("LOCAL")) {
            User blu = userRepository.findByEmail(userQnaRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = userQnaRepository.save(userQnaRequestDto.toEntity(blu)).getId();
        } else {
            SocialUser bau = socialUserRepository.findByEmail(userQnaRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = userQnaRepository.save(userQnaRequestDto.toEntity(bau)).getId();
        }
        return responseService.getSingleResult(result);
    }
}
