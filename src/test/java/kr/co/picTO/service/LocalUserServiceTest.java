package kr.co.picTO.service;

import kr.co.picTO.member.application.token.LocalUserJwtProvider;
import kr.co.picTO.member.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.member.domain.local.BaseLocalUserRepo;
import kr.co.picTO.member.application.local.LocalUserService;
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
    private BaseLocalUserRepo userRepo;
    @Autowired
    private LocalUserService userService;
    @Autowired
    private LocalUserJwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void login() {

    }

    @Test
    void singUp(LocalUserSignUpRequestDto localUserSignUpRequestDto) {

    }
}
