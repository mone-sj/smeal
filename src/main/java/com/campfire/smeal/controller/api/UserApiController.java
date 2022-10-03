package com.campfire.smeal.controller.api;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.ResponseDto;
import com.campfire.smeal.model.User;
import com.campfire.smeal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    // 회원가입
    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> userSave(
            @RequestBody User user
    ) {
        userService.회원가입(user, user.getPasswordRepeat());
        log.info("회원가입완료");
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    //     회원수정
    @PutMapping("/user/updateProc")
    public ResponseDto<Integer> update(
            @RequestBody User user,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        User updateUser = userService.회원수정(user);

        // 세션 수정
        principalDetails.setUser(updateUser);
//        Authentication authentication= authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    // 회원 삭제
    // 추후 수정 user/delete/{id}
    @DeleteMapping("/user/delete/{id}")
    public ResponseDto<Integer> userDelete(@PathVariable int id) {
        userService.회원삭제(Long.valueOf(id));
        // 세션 삭제
        SecurityContextHolder.clearContext();
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @PostMapping("/user/checkUserId")
    public ResponseDto<Integer> checkUserId(@RequestBody String userId) {
        Boolean result = userService.validateCreateUser(userId);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

}
