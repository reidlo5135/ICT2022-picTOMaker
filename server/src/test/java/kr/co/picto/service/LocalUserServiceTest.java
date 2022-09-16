package kr.co.picto.service;

import kr.co.picTO.token.application.JwtProvider;
import kr.co.picTO.user.dto.local.UserCreateDto;
import kr.co.picTO.user.domain.local.UserRepository;
import kr.co.picTO.user.application.local.UserService;
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
