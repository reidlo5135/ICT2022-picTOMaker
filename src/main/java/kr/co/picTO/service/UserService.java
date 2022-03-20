package kr.co.picTO.service;

import kr.co.picTO.advice.exception.UserNotFoundCException;
import kr.co.picTO.entity.User;
import kr.co.picTO.repository.UserJpaRepo;
import kr.co.picTO.dto.user.UserRequestDTO;
import kr.co.picTO.dto.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserJpaRepo userJpaRepo;

    @Transactional
    public Long save(UserRequestDTO userDTO) {
        userJpaRepo.save(userDTO.toEntity());
        return userJpaRepo.findByEmail(userDTO.getEmail()).getUserid();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User user = userJpaRepo.findById(id)
                .orElseThrow(UserNotFoundCException::new);
        return new UserResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findByEmail(String email) {
        User user = userJpaRepo.findByEmail(email);
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
