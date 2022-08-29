package kr.co.picTO.user.application.local;

import kr.co.picTO.user.exception.CustomUserNotFoundException;
import kr.co.picTO.user.domain.local.BaseLocalUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final BaseLocalUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return userRepo.findById(Long.parseLong(userPk)).orElseThrow(CustomUserNotFoundException::new);
    }
}
