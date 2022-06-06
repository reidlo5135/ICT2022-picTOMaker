package kr.co.picTO.service.qna;

import kr.co.picTO.advice.exception.CustomUserNotFoundException;
import kr.co.picTO.dto.qna.UserQnaRequestDto;
import kr.co.picTO.repository.BaseAuthUserRepo;
import kr.co.picTO.repository.BaseLocalUserRepo;
import kr.co.picTO.repository.BaseUserQnaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserQnaService {

    private final BaseUserQnaRepo baseUserQnaRepo;
    private final BaseLocalUserRepo userJpaRepo;
    private final BaseAuthUserRepo authUserRepo;

    public Long registerQnA(UserQnaRequestDto userQnaRequestDto, String provider) {
        log.info("User Qna SVC Login UserQnaDto : " + userQnaRequestDto.getEmail());

        if(provider != null && !provider.equals("LOCAL")) {
            if(!authUserRepo.findByEmail(userQnaRequestDto.getEmail()).isPresent()) throw new CustomUserNotFoundException();
        } else {
            if(!userJpaRepo.findByEmail(userQnaRequestDto.getEmail()).isPresent()) throw new CustomUserNotFoundException();
        }

        return baseUserQnaRepo.save(userQnaRequestDto.toEntity()).getId();
    }
}
