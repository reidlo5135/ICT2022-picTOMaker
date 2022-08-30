package kr.co.picTO.qna.application;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.qna.domain.BaseUserQnaRepo;
import kr.co.picTO.qna.dto.UserQnaRequestDto;
import kr.co.picTO.user.domain.local.BaseLocalUser;
import kr.co.picTO.user.domain.local.BaseLocalUserRepo;
import kr.co.picTO.user.domain.social.BaseAuthUser;
import kr.co.picTO.user.domain.social.BaseAuthUserRepo;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
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

    public SingleResult<Long> registerQnA(UserQnaRequestDto userQnaRequestDto, String provider) {
        Long result = null;
        if(provider != null && provider.equals("LOCAL")) {
            BaseLocalUser blu = userJpaRepo.findByEmail(userQnaRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = baseUserQnaRepo.save(userQnaRequestDto.toEntity(blu)).getId();
        } else {
            BaseAuthUser bau = authUserRepo.findByEmail(userQnaRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = baseUserQnaRepo.save(userQnaRequestDto.toEntity(bau)).getId();
        }
        return responseService.getSingleResult(result);
    }
}
