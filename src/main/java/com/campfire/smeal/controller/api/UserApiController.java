package com.campfire.smeal.controller.api;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.ErrorResponseDto;
import com.campfire.smeal.dto.ResponseDto;
import com.campfire.smeal.handler.exception.GeneralException;
import com.campfire.smeal.model.User;
import com.campfire.smeal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;


    // 회원가입
    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> userSave(@RequestBody User user) {
        userService.회원가입(user);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    // 회원수정
    // uri 추후 수정 user/updateProc
    @PutMapping("/auth/updateProc")
    public ResponseDto<Integer> update(
                        @RequestBody User user,
                        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        User updateUser = userService.회원수정(user);
        // 세션 수정
        principalDetails.setUser(updateUser);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    // 회원 삭제
    // 추후 수정 user/delete/{id}
    @DeleteMapping("/auth/delete/{id}")
    public ResponseDto<Integer> userDelete(@PathVariable Long id) {
        userService.회원삭제(id);
        // 세션 삭제
        SecurityContextHolder.clearContext();
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @PostMapping("/auth/checkUsername")
    public ResponseDto<Integer> checkUsername(@RequestBody String username) {
        Boolean result = userService.validateCreateUser(username);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);

    }

}
