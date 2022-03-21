package kr.co.picTO.service;

import kr.co.picTO.advice.exception.UserNotFoundCException;
import kr.co.picTO.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;

    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return userJpaRepo.findById(Long.parseLong(userPk)).orElseThrow(UserNotFoundCException::new);
    }
}
