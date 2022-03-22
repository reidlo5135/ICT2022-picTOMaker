package kr.co.picTO.service;

import kr.co.picTO.advice.exception.EmailLoginFailedCException;
import kr.co.picTO.advice.exception.EmailSignUpFailedCException;
import kr.co.picTO.advice.exception.UserNotFoundCException;
import kr.co.picTO.dto.user.UserLoginResponseDTO;
import kr.co.picTO.dto.user.UserSignUpRequestDTO;
import kr.co.picTO.entity.User;
import kr.co.picTO.repository.UserJpaRepo;
import kr.co.picTO.dto.user.UserRequestDTO;
import kr.co.picTO.dto.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserJpaRepo userJpaRepo;
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserLoginResponseDTO login(String email, String password) {
        User user = userJpaRepo.findByEmail(email).orElseThrow(EmailLoginFailedCException::new);
        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new EmailLoginFailedCException();
        return new UserLoginResponseDTO(user);
    }

    @Transactional
    public Long signup(UserSignUpRequestDTO userSignUpRequestDTO) {
        if(userJpaRepo.findByEmail(userSignUpRequestDTO.getEmail()).orElse(null) == null)
            return userJpaRepo.save(userSignUpRequestDTO.toEntity()).getUserid();
        else throw new EmailSignUpFailedCException();
    }

    @Transactional
    public Long save(UserRequestDTO userDTO) {
        User saved = userJpaRepo.save(userDTO.toEntity());
        return saved.getUserid();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User user = userJpaRepo.findById(id)
                .orElseThrow(UserNotFoundCException::new);
        return new UserResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findByEmail(String email) {
        User user = userJpaRepo.findByEmail(email).orElseThrow(UserNotFoundCException::new);
        if (user == null) throw new UserNotFoundCException();
        else return new UserResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAllUser() {
        return userJpaRepo.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, UserRequestDTO userRequestDTO) {
        User modifiedUser = userJpaRepo
                .findById(id).orElseThrow(UserNotFoundCException::new);
        return id;
    }

    @Transactional
    public void delete(Long id) {
        userJpaRepo.deleteById(id);
    }
}
