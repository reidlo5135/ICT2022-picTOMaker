package kr.co.picTO.user.application;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.user.domain.local.BaseLocalUser;
import kr.co.picTO.user.domain.local.BaseLocalUserRepo;
import kr.co.picTO.user.dto.local.LocalUserResponseDto;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final BaseLocalUserRepo userRepository;
    private final ResponseService responseService;

    @Transactional(readOnly = true)
    public ListResult<LocalUserResponseDto> findAllUser() {
        List<LocalUserResponseDto> list = userRepository.findAll().stream().map(LocalUserResponseDto::new).collect(Collectors.toList());
        if(list.isEmpty()) throw new NullPointerException();

        return responseService.getListResult(list);
    }

    @Transactional(readOnly = true)
    public SingleResult<LocalUserResponseDto> findById(Long id) {
        BaseLocalUser user = userRepository.findById(id).orElseThrow(CustomUserNotFoundException::new);

        return responseService.getSingleResult(new LocalUserResponseDto(user));
    }

    @Transactional(readOnly = true)
    public SingleResult<LocalUserResponseDto> findByEmail(String email) {
        BaseLocalUser user = userRepository.findByEmail(email).orElseThrow(CustomUserNotFoundException::new);

        return responseService.getSingleResult(new LocalUserResponseDto(user));
    }

    @Transactional
    public SingleResult<Long> delete(Long id) {
        userRepository.deleteById(id);
        Long result = userRepository.findById(id).stream().count();

        return responseService.getSingleResult(result);
    }
}
