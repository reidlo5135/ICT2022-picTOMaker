package kr.co.picTO.community.application;

import kr.co.picTO.common.exception.CustomUserNotFoundException;
import kr.co.picTO.community.dto.UserQnaRequestDto;
import kr.co.picTO.common.domain.CommonResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.member.domain.oauth2.BaseAuthUserRepo;
import kr.co.picTO.member.domain.local.BaseLocalUserRepo;
import kr.co.picTO.community.domain.BaseUserQnaRepo;
import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.oauth2.BaseAuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> registerQnA(UserQnaRequestDto userQnaRequestDto, String provider) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("User QnA SVC Login UserQnaDto : " + userQnaRequestDto.getEmail());

        BaseLocalUser blu = null;
        BaseAuthUser bau = null;
        Long result = null;
        try {
            if(provider != null && provider.equals("LOCAL")) {
                if(!userJpaRepo.findByEmail(userQnaRequestDto.getEmail()).isPresent()) {
                    throw new CustomUserNotFoundException();
                } else {
                    blu = userJpaRepo.findByEmail(userQnaRequestDto.getEmail()).orElse(null);
                    result = baseUserQnaRepo.save(userQnaRequestDto.toEntity(blu)).getId();
                }
            } else {
                if(!authUserRepo.findByEmail(userQnaRequestDto.getEmail()).isPresent()) {
                    throw new CustomUserNotFoundException();
                } else {
                    bau = authUserRepo.findByEmail(userQnaRequestDto.getEmail()).orElse(null);
                    result = baseUserQnaRepo.save(userQnaRequestDto.toEntity(bau)).getId();
                }
            }
            log.info("User Community SVC registerBoard result : " + result);

            if(result == null || result == 0) {
                CommonResult failResult = responseService.getFailResult(-1, "registerQnA Error Occurred");
                loggingService.commonResultLogging(this.getClass(), "registerQnA", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                SingleResult<Long> singleResult = responseService.getSingleResult(result);
                loggingService.singleResultLogging(this.getClass(), "registerQnA", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User QnA SVC registerQnA Error Occurred : " + e.getMessage());
        } finally {
            log.info("User QnA SVC registerQnA ett : " + ett);
            return ett;
        }
    }
}
