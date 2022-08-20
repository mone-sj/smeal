package com.campfire.smeal.controller;

import com.campfire.smeal.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class UserController {

    // 대시보드 페이지
    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }
    //로그인페이지
    @GetMapping("/auth/login")
    public String loginForm() {
        return "user/login";
    }
    //회원가입 페이지
    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/register";
    }

    // 회원 수정 페이지
    @GetMapping("/user/update")
    public @ResponseBody String userUpdate(@AuthenticationPrincipal PrincipalDetails principal) {
        return "updateForm";
    }

    @GetMapping("/auth/oauth2Login")
    public String oauth2() {
        return "test/oauth2Login";
    }
}
