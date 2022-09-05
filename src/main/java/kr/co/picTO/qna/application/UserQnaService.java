package kr.co.picTO.qna.application;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.qna.domain.UserQnaRepo;
import kr.co.picTO.qna.dto.UserQnaRequestDto;
import kr.co.picTO.user.domain.User;
import kr.co.picTO.user.domain.UserRepository;
import kr.co.picTO.user.domain.local.BaseLocalUserRepo;
import kr.co.picTO.user.domain.social.SocialUserRepository;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserQnaService {
    private final UserQnaRepo userQnaRepository;
    private final UserRepository userRepository;
    private final BaseLocalUserRepo userJpaRepo;
    private final SocialUserRepository authUserRepo;
    private final ResponseService responseService;

    public SingleResult<Long> registerQnA(UserQnaRequestDto userQnaRequestDto) {
        User user = userRepository.findByEmail(userQnaRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
        return responseService.getSingleResult(userQnaRepository.save(userQnaRequestDto.toEntity(user)).getId());
    }
}
