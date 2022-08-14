package kr.co.picTO.community.application;

import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.CommonResult;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.common.exception.CustomUserNotFoundException;
import kr.co.picTO.community.domain.BaseUserCommunity;
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

import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserCommunityService {
    private static final String className = UserCommunityService.class.toString();
    private final BaseUserCommunityRepo communityRepo;
    private final BaseLocalUserRepo userJpaRepo;
    private final BaseAuthUserRepo authUserRepo;
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

    public ResponseEntity<?> findBoardAll() {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            List<BaseUserCommunity> result = communityRepo.findAll();

            if(result.isEmpty()) {
                CommonResult failResult = responseService.getFailResult(-1, "등록된 게시물이 존재하지 않습니다.");
                loggingService.commonResultLogging(className, "findBoardAll", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                for(int i=0;i<result.size();i++) {
                    log.info("User Community SVC findBoardAll result : " + result.get(i));
                }
                ListResult<BaseUserCommunity> listResult = responseService.getListResult(result);
                loggingService.listResultLogging(className, "findBoardAll", listResult);
                ett = new ResponseEntity<>(listResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User Community SVC findBoardAll Error Occurred : " + e.getMessage());
        } finally {
            log.info("User Community SVC findBoardAll ett : " + ett);
            return ett;
        }
    }

    public ResponseEntity<?> registerBoard(UserCommunityRequestDto userCommunityRequestDto, String provider) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("User Community SVC registerBoard UserCommunityDto email : " + userCommunityRequestDto.getEmail());
        log.info("User Community SVC registerBoard UserCommunityDto title : " + userCommunityRequestDto.getTitle());
        log.info("User Community SVC registerBoard UserCommunityDto content : " + userCommunityRequestDto.getContent());
        log.info("User Community SVC registerBoard provider : " + provider);

        BaseLocalUser blu = null;
        BaseAuthUser bau = null;
        Long result = null;
        try {
            if(provider != null && provider.equals("LOCAL")) {
                if(!userJpaRepo.findByEmail(userCommunityRequestDto.getEmail()).isPresent()) {
                    throw new CustomUserNotFoundException();
                } else {
                    blu = userJpaRepo.findByEmail(userCommunityRequestDto.getEmail()).orElse(null);
                    result = communityRepo.save(userCommunityRequestDto.toBluEntity(blu)).getId();
                }
            } else {
                if(!authUserRepo.findByEmail(userCommunityRequestDto.getEmail()).isPresent()) {
                    throw new CustomUserNotFoundException();
                } else {
                    bau = authUserRepo.findByEmail(userCommunityRequestDto.getEmail()).orElse(null);
                    result = communityRepo.save(userCommunityRequestDto.toBauEntity(bau)).getId();
                }
            }
            log.info("User Community SVC registerBoard result : " + result);

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
