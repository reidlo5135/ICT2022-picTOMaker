package kr.co.picTO.service.user;

import kr.co.picTO.advice.exception.CUserNotFoundException;
import kr.co.picTO.entity.user.User;
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

    @Transactional
    public Long save(UserRequestDTO userDTO) {
        User saved = userJpaRepo.save(userDTO.toEntity());
        return saved.getUserid();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User user = userJpaRepo.findById(id)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findByEmail(String email) {
        User user = userJpaRepo.findByEmail(email).orElseThrow(CUserNotFoundException::new);
        if (user == null) throw new CUserNotFoundException();
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
                .findById(id).orElseThrow(CUserNotFoundException::new);
        return id;
    }

    @Transactional
    public void delete(Long id) {
        userJpaRepo.deleteById(id);
    }
}
