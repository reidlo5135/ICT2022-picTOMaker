package kr.co.picTO.community.application;

import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.CommonResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.common.exception.CustomUserNotFoundException;
import kr.co.picTO.community.domain.BaseUserCommunityRepo;
import kr.co.picTO.community.dto.UserCommunityRequestDto;
import kr.co.picTO.member.domain.BaseAuthUserRepo;
import kr.co.picTO.member.domain.BaseLocalUserRepo;
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
public class UserCommunityService {
    private static final String className = UserCommunityService.class.toString();
    private BaseUserCommunityRepo communityRepo;
    private final BaseLocalUserRepo userJpaRepo;
    private final BaseAuthUserRepo authUserRepo;
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

    public ResponseEntity<?> registerBoard(UserCommunityRequestDto userCommunityRequestDto, String provider) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("User Community SVC registerBoard UserCommunityDto : " + userCommunityRequestDto.getEmail());
        log.info("User Community SVC registerBoard provider : " + provider);

        BaseLocalUser blu = null;
        BaseAuthUser bau = null;
        try {
            if(provider != null && provider.equals("LOCAL")) {
                if(!userJpaRepo.findByEmail(userCommunityRequestDto.getEmail()).isPresent()) throw new CustomUserNotFoundException();
                else blu = userJpaRepo.findByEmail(userCommunityRequestDto.getEmail()).orElse(null);
            } else {
                if(!authUserRepo.findByEmail(userCommunityRequestDto.getEmail()).isPresent()) throw new CustomUserNotFoundException();
                else bau = authUserRepo.findByEmail(userCommunityRequestDto.getEmail()).orElse(null);
            }

            log.info("User Community SVC registerBoard blu : " + blu.getEmail());
            log.info("User Community SVC registerBoard bau : " + bau.getEmail());

            Long result = communityRepo.save(userCommunityRequestDto.toEntity(blu, bau, provider)).getId();

            if(result == null || result == 0) {
                CommonResult failResult = responseService.getFailResult(-1, "registerBoard Error Occurred");
                loggingService.commonResultLogging(className, "registerBoard", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                SingleResult<Long> singleResult = responseService.getSingleResult(result);
                loggingService.singleResultLogging(className, "registerBoard", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User Community SVC registerBoard Error Occurred : " + e.getMessage());
        } finally {
            log.info("User Community SVC registerBoard ett : " + ett);
            return ett;
        }
    }
}
