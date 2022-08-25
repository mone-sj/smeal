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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    // 회원가입
    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> userSave(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String passwordRepeat,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nickname
    ) {
        User user=User.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();
        userService.회원가입(user, passwordRepeat);
        log.info("회원가입완료");
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    // 회원수정
    // uri 추후 수정 user/updateProc
    @PutMapping("/auth/updateProc")
    public ResponseDto<Integer> update(
                        @RequestBody User user,
                        @RequestParam(required = true) String passwordRepeat,
                        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        User updateUser = userService.회원수정(user,passwordRepeat);

        // 세션 수정
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    // 회원 삭제
    // 추후 수정 user/delete/{id}
    @DeleteMapping("/auth/delete/{id}")
    public ResponseDto<Integer> userDelete(@PathVariable int id) {
        userService.회원삭제(Long.valueOf(id));
        // 세션 삭제
        SecurityContextHolder.clearContext();
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @PostMapping("/auth/checkUserId")
    public ResponseDto<Integer> checkUserId(@RequestBody String userId) {
        Boolean result = userService.validateCreateUser(userId);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

}
