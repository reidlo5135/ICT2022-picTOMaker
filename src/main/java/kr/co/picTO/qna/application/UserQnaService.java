package kr.co.picTO.qna.application;

import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.local.BaseLocalUserRepo;
import kr.co.picTO.member.domain.social.BaseAuthUser;
import kr.co.picTO.member.domain.social.BaseAuthUserRepo;
import kr.co.picTO.member.exception.CustomUserNotFoundException;
import kr.co.picTO.qna.domain.BaseUserQnaRepo;
import kr.co.picTO.qna.dto.UserQnaRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserQnaService {
    private final BaseUserQnaRepo baseUserQnaRepo;
    private final BaseLocalUserRepo userJpaRepo;
    private final BaseAuthUserRepo authUserRepo;
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

    public SingleResult<Long> registerQnA(UserQnaRequestDto userQnaRequestDto, String provider) {
        Long result = null;
        if(provider != null && provider.equals("LOCAL")) {
            BaseLocalUser blu = userJpaRepo.findByEmail(userQnaRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = baseUserQnaRepo.save(userQnaRequestDto.toEntity(blu)).getId();
        } else {
            BaseAuthUser bau = authUserRepo.findByEmail(userQnaRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = baseUserQnaRepo.save(userQnaRequestDto.toEntity(bau)).getId();
        }
        SingleResult<Long> singleResult = responseService.getSingleResult(result);
        loggingService.singleResultLogging(this.getClass(), "registerQnA", singleResult);

        return singleResult;
    }
}
