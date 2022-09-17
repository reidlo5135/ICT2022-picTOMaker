package kr.co.picto.service;

import kr.co.picto.token.application.JwtProvider;
import kr.co.picto.user.application.local.UserService;
import kr.co.picto.user.domain.local.UserRepository;
import kr.co.picto.user.dto.local.UserCreateDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalUserServiceTest {

    private static final String TEST_EMAIL = "naru5135@naver.com";
    private static final String TEST_PWD = "1235";
    private static final String TEST_NAME = "강준모";
    private static final String TEST_NICKNAME = "레이들로";
    private static final String TEST_PROV = "LOCAL";
    private static final String TEST_IMG = "asdfasf";

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void login() {

    }

    @Test
    void singUp(UserCreateDto userCreateDto) {

    }
}
