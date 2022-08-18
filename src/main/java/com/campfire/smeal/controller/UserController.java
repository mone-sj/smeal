package com.campfire.smeal.controller;

import com.campfire.smeal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class UserController {

    @GetMapping("/auth/login") //로그인페이지
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("/auth/joinForm") //회원가입 페이지
    public String joinForm() {
        return "user/register";
    }

    @GetMapping("/user/update") // 회원 수정 페이지
    public @ResponseBody String userUpdate() {
        return "updateForm";
    }
}
