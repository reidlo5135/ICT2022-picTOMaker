package kr.co.picTO.member.application.local;

import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.local.BaseLocalUserRepo;
import kr.co.picTO.member.dto.local.LocalUserResponseDto;
import kr.co.picTO.member.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final BaseLocalUserRepo userRepository;
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

    @Transactional(readOnly = true)
    public SingleResult<LocalUserResponseDto> findById(Long id) {
        BaseLocalUser user = userRepository.findById(id).orElseThrow(CustomUserNotFoundException::new);
        log.info("AdminUserSVC findById user : " + user);
        SingleResult<LocalUserResponseDto> singleResult = responseService.getSingleResult(new LocalUserResponseDto(user));
        loggingService.singleResultLogging(this.getClass(), "findById", singleResult);

        return singleResult;
    }

    @Transactional(readOnly = true)
    public SingleResult<LocalUserResponseDto> findByEmail(String email) {
        BaseLocalUser user = userRepository.findByEmail(email).orElseThrow(CustomUserNotFoundException::new);

        SingleResult<LocalUserResponseDto> singleResult = responseService.getSingleResult(new LocalUserResponseDto(user));
        loggingService.singleResultLogging(this.getClass(), "findByEmail", singleResult);

        return singleResult;
    }

    @Transactional(readOnly = true)
    public ListResult<LocalUserResponseDto> findAllUser() {
        List<LocalUserResponseDto> list = userRepository.findAll().stream().map(LocalUserResponseDto::new).collect(Collectors.toList());
        if(list.isEmpty()) throw new NullPointerException();

        ListResult<LocalUserResponseDto> listResult = responseService.getListResult(list);
        loggingService.listResultLogging(this.getClass(), "findAllUser", listResult);

        return listResult;
    }

    @Transactional
    public SingleResult<Long> delete(Long id) {
        userRepository.deleteById(id);
        Long result = userRepository.findById(id).stream().count();
        log.info("AdminUserSVC delete result : " + result);
        SingleResult<Long> singleResult = responseService.getSingleResult(result);
        loggingService.singleResultLogging(this.getClass(), "delete", singleResult);

        return singleResult;
    }
}
