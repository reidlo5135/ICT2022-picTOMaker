package kr.co.picTO.service.local;

import kr.co.picTO.advice.exception.CEmailLoginFailedException;
import kr.co.picTO.advice.exception.CUserNotFoundException;
import kr.co.picTO.config.security.LocalUserJwtProvider;
import kr.co.picTO.dto.local.LocalUserLoginResponseDto;
import kr.co.picTO.dto.local.LocalUserRequestDto;
import kr.co.picTO.dto.local.LocalUserResponseDto;
import kr.co.picTO.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.entity.local.BaseLocalUser;
import kr.co.picTO.repository.BaseLocalUserRepo;
import kr.co.picTO.repository.BaseTokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class LocalUserService {
    private final BaseLocalUserRepo userJpaRepo;
    private final PasswordEncoder passwordEncoder;
    private final LocalUserJwtProvider localUserJwtProvider;
    @Transactional(readOnly = true)
    public LocalUserLoginResponseDto login(String email, String password) {
        BaseLocalUser user = userJpaRepo.findByEmail(email).orElseThrow(CEmailLoginFailedException::new);
        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailLoginFailedException();
        log.info("Local User SVC login e,p : " + email + ", " + password);
        return new LocalUserLoginResponseDto(user);
    }
    @Transactional
    public Long singUp(LocalUserSignUpRequestDto localUserSignUpRequestDto) {
        if(userJpaRepo.findByEmail(localUserSignUpRequestDto.getEmail()).orElse(null) == null)
            return userJpaRepo.save(localUserSignUpRequestDto.toEntity()).getId();
        else throw new CEmailLoginFailedException();
    }
    @Transactional
    public String createToken(Long id, List<String> roles) {
        String token = localUserJwtProvider.createToken(String.valueOf(id), roles);
        if (token == null || token.equals(""))
            throw new NullPointerException("Local User token is null");
        log.info("Local User SVC i, r : " + id + ", " + roles);
        log.info("Local User SVC createToken : " + token);

        return token;
    }
    @Transactional(readOnly = true)
    public LocalUserResponseDto findById(Long id) {
        BaseLocalUser user = userJpaRepo.findById(id)
                .orElseThrow(CUserNotFoundException::new);
        return new LocalUserResponseDto(user);
    }
    @Transactional(readOnly = true)
    public LocalUserResponseDto findByEmail(String email) {
        BaseLocalUser user = userJpaRepo.findByEmail(email)
                .orElseThrow(CUserNotFoundException::new);
        return new LocalUserResponseDto(user);
    }
    @Transactional(readOnly = true)
    public List<LocalUserResponseDto> findAllUser() {
        return userJpaRepo.findAll()
                .stream()
                .map(LocalUserResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public Long update(Long id, LocalUserRequestDto userRequestDto) {
        BaseLocalUser modifiedUser = userJpaRepo
                .findById(id).orElseThrow(CUserNotFoundException::new);
        modifiedUser.updateNickName(userRequestDto.getNickName());
        return id;
    }
    @Transactional
    public void delete(Long id) {
        userJpaRepo.deleteById(id);
    }
}
