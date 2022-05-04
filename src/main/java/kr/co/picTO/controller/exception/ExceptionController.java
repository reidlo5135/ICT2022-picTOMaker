package kr.co.picTO.controller.exception;

import kr.co.picTO.advice.exception.CAuthenticationEntryPointException;
import kr.co.picTO.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping(value = "/accessDenied")
    public CommonResult accessDeniedException() {
        throw new AccessDeniedException("Access Denied");
    }
}
