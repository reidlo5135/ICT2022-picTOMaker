package kr.co.picTO.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import kr.co.picTO.entity.User;
import kr.co.picTO.model.response.CommonResult;
import kr.co.picTO.model.response.ListResult;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.repo.UserJpaRepo;

import kr.co.picTO.service.ResponseService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. User"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 단건 검색", notes = "userID로 회원 조회")
    public SingleResult<User> findUserByKey(@ApiParam(value = "회원 ID", required = true) @PathVariable Long userId) {
        return responseService
                .getSingleResult(userJpaRepo.findById(userId).orElse(null));
    }

    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원 목록을 조회")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiOperation(value = "회원 등록", notes = "회원 가입")
    @PostMapping(value = "/user")
    public SingleResult<User> save(@ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .email(email)
                .name(name)
                .build();

        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원 정보 수정")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(@ApiParam(value = "회원 아이디", required = true) @RequestParam Long userId,
                                     @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .userid(userId)
                .email(email)
                .name(name)
                .build();

        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "회원 영구 삭제")
    @DeleteMapping(value = "/user/{userId}")
    public CommonResult delete(@ApiParam(value = "회원 아이디", required = true) @PathVariable Long userId) {
        userJpaRepo.deleteById(userId);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "회원 검색(이름)", notes = "이름으로 회원 검색.")
    @GetMapping(value = "/findUser/{name}")
    public User findUserByName(@ApiParam(value = "회원 이름", required = true) @PathVariable String name) {
        return userJpaRepo.findByName(name);
    }

    @ApiOperation(value = "회원 검색(이메일)", notes = "이메일로 회원 검색.")
    @GetMapping(value = "/findUserByEmail/{email}")
    public User findUserByEmail(@ApiParam(value = "회원 이메일", required = true) @PathVariable String email) {
        return userJpaRepo.findByEmail(email);
    }
}
