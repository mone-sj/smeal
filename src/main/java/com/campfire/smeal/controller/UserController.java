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

    @GetMapping("/auth/login") //로그인페이지
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("/auth/joinForm") //회원가입 페이지
    public String joinForm() {
        return "user/register";
    }

    @GetMapping("/user/update") // 회원 수정 페이지
    public @ResponseBody String userUpdate(@AuthenticationPrincipal PrincipalDetails principal) {
        return "updateForm";
    }

    @GetMapping("/auth/oauth2Login")
    public String oauth2(@RequestParam(value = "error", required = false)String error,
                         @RequestParam(value = "exception", required = false)String exception,
                         Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "test/oauth2Login";
    }
}
