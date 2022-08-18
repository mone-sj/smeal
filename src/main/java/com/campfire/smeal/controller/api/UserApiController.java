package com.campfire.smeal.controller.api;

import com.campfire.smeal.dto.ResponseDto;
import com.campfire.smeal.model.User;
import com.campfire.smeal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/auth/updateProc") //user/updateProc
    public ResponseDto<Integer> update(@RequestBody User user) {
        User updateUser = userService.회원수정(user);
//        principalDetails.setUser(updateUser); //추가
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    // 회원 삭제
    @DeleteMapping("/auth/delete/{id}") //user/delete/{id}
    public ResponseDto<Integer> userDelete(@PathVariable Long id) {
        userService.회원삭제(id);
        //SecurityContextHolder.clearContext(); // 세션 삭제
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }
}
