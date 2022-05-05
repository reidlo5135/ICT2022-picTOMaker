package kr.co.picTO.service.local;

import kr.co.picTO.advice.exception.CustomUserNotFoundException;
import kr.co.picTO.repository.BaseLocalUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final BaseLocalUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return userRepo.findById(Long.parseLong(userPk)).orElseThrow(CustomUserNotFoundException::new);
    }
}
