package com.campfire.smeal.controller;

import com.campfire.smeal.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String loginForm(
            @RequestParam(value = "exception", required = false) String exception,
            Model model
    ) {
        model.addAttribute("exception", exception);
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

    // OAuth2로그인 테스트용, 로그인페이지 완성되면 삭제
    @GetMapping("/auth/oauth2Login")
    public String oauth2(
            @RequestParam(value = "exception", required = false) String exception,
            Model model
    ) {
        model.addAttribute("exception", exception);
        return "test/oauth2Login";
    }

}
