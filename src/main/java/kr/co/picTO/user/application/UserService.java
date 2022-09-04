package kr.co.picTO.user.application;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.user.domain.User;
import kr.co.picTO.user.domain.UserRepository;
import kr.co.picTO.user.dto.UserCreateDto;
import kr.co.picTO.user.dto.UserInfoDto;
import kr.co.picTO.user.dto.UserLoginDto;
import kr.co.picTO.user.dto.UserUpdateDto;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
import kr.co.picTO.user.exception.UserCreateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ResponseService responseService;

    public SingleResult<UserInfoDto> save(UserCreateDto userCreateDto) {
        try {
            return responseService.getSingleResult(UserInfoDto.from(userRepository.save(userCreateDto.toEntity())));
        } catch (Exception e) {
            throw new UserCreateException(e);
        }
    }

    public SingleResult<UserInfoDto> findInfoDtoById(long userId) {
        return responseService.getSingleResult(UserInfoDto.from(findById(userId)));
    }

    @Transactional(readOnly = true)
    public User findById(long userId) {
        return userRepository.findById(userId).orElseThrow(CustomUserNotFoundException::new);
    }

    @Transactional
    public SingleResult<UserInfoDto> update(UserUpdateDto userUpdateDto, long userId) {
        User findUser = findById(userId);
        findUser.update(userUpdateDto.toEntity());
        findUser.activate();

        return responseService.getSingleResult(UserInfoDto.from(findUser));
    }

    @Transactional
    public void delete(long userId) {
        userRepository.findById(userId)
                .orElseThrow(CustomUserNotFoundException::new)
                .deactivate();
    }

    @Transactional(readOnly = true)
    public User authenticate(UserLoginDto userLoginDto) {
        User loginUser = userLoginDto.toEntity();
        return userRepository.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword()).orElseThrow(CustomUserNotFoundException::new);
    }

    public User findInfoDtoByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(CustomUserNotFoundException::new);
    }
}
