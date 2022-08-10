package kr.co.picTO.member.application;

import kr.co.picTO.member.dto.local.LocalUserResponseDto;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.common.domain.CommonResult;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.member.domain.BaseLocalUserRepo;
import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.common.application.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminUserService {

    private static final String className = AdminUserService.class.toString();

    private final BaseLocalUserRepo userJpaRepo;
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long id) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("AdminUserSVC findById id : " + id);

        BaseLocalUser user = userJpaRepo.findById(id).orElse(null);
        log.info("AdminUserSVC findById user : " + user);
        try {
            if(user == null) {
                CommonResult failResult = responseService.getFailResult(-1, "findById result is Null");
                loggingService.commonResultLogging(className, "findById", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                SingleResult<LocalUserResponseDto> singleResult = responseService.getSingleResult(new LocalUserResponseDto(user));
                loggingService.singleResultLogging(className, "findById", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AdminUserSVC findById Error Occurred : " + e.getMessage());
        } finally {
            log.info("AdminUserSVC findById ett : " + ett);
            return ett;
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByEmail(String email) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("AdminUserSVC findByEmail email : " + email);

        BaseLocalUser user = userJpaRepo.findByEmail(email).orElse(null);
        log.info("AdminUserSVC findByEmail user : " + user);
        try {
            if(user == null) {
                CommonResult failResult = responseService.getFailResult(-1, "findByEmail result is Null");
                loggingService.commonResultLogging(className, "findByEmail", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                SingleResult<LocalUserResponseDto> singleResult = responseService.getSingleResult(new LocalUserResponseDto(user));
                loggingService.singleResultLogging(className, "findByEmail", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AdminUserSVC findByEmail Error Occurred : " + e.getMessage());
        } finally {
            log.info("AdminUserSVC findByEmail ett : " + ett);
            return ett;
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllUser() {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        List<LocalUserResponseDto> list = userJpaRepo.findAll().stream().map(LocalUserResponseDto::new).collect(Collectors.toList());
        log.info("AdminUserSVC findAllUser list : " + list);
        try {
            if(list.isEmpty()) {
                CommonResult failResult = responseService.getFailResult(-1, "findAllUser result is Null");
                loggingService.commonResultLogging(className, "findAllUser", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                ListResult listResult = responseService.getListResult(list);
                loggingService.listResultLogging(className, "findAllUser", listResult);
                ett = new ResponseEntity<>(listResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AdminUserSVC findAllUser Error Occurred : " + e.getMessage());
        } finally {
            log.info("AdminUserSVC findAllUser ett : " + ett);
            return ett;
        }
    }

    @Transactional
    public ResponseEntity<?> delete(Long id) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("AdminUserSVC delete id : " + id);

        Integer result = 0;
        BaseLocalUser user = userJpaRepo.findById(id).orElse(null);
        log.info("AdminUserSVC delete user : " + user);
        try {
            if(user == null) {
                CommonResult failResult = responseService.getFailResult(-1, "delete findById is Null");
                loggingService.commonResultLogging(className, "delete", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                userJpaRepo.deleteById(id);
                result++;
                log.info("AdminUserSVC delete result : " + result);
                SingleResult<Integer> singleResult = responseService.getSingleResult(result);
                loggingService.singleResultLogging(className, "delete", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AdminUserSVC delete Error Occurred : " + e.getMessage());
        } finally {
            log.info("AdminUserSVC delete ett : " + ett);
            return ett;
        }
    }
}
