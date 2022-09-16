package kr.co.picto.user.application.admin;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.user.domain.local.User;
import kr.co.picTO.user.domain.local.UserRepository;
import kr.co.picTO.user.dto.local.UserResponseDto;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;
    private final ResponseService responseService;

    @Transactional(readOnly = true)
    public ListResult<UserResponseDto> findAllUser() {
        List<UserResponseDto> list = userRepository.findAll().stream().map(UserResponseDto::new).collect(Collectors.toList());
        if(list.isEmpty()) throw new NullPointerException();

        return responseService.getListResult(list);
    }

    @Transactional(readOnly = true)
    public SingleResult<UserResponseDto> findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(CustomUserNotFoundException::new);

        return responseService.getSingleResult(new UserResponseDto(user));
    }

    @Transactional(readOnly = true)
    public SingleResult<UserResponseDto> findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(CustomUserNotFoundException::new);

        return responseService.getSingleResult(new UserResponseDto(user));
    }

    @Transactional
    public SingleResult<Long> delete(Long id) {
        userRepository.deleteById(id);
        Long result = userRepository.findById(id).stream().count();

        return responseService.getSingleResult(result);
    }
}
