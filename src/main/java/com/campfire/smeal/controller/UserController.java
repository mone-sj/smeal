package com.campfire.smeal.controller;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.ResponseDto;
import com.campfire.smeal.model.User;
import com.campfire.smeal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // 대시보드 페이지
    @GetMapping("/dashboard")
    public String dashboard() {
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
    public @ResponseBody String userUpdate(
            @AuthenticationPrincipal PrincipalDetails principal) {
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


    // 로그인 테스트용
    @GetMapping("/auth/joinTest") //회원가입 화면
    public String joinTest() {
        return "test/joinTest";
    }

    @PostMapping("/auth/joinTest")
    public @ResponseBody ResponseDto<Integer> joinTest(@RequestBody User user) {
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    //로그인화면
    @GetMapping("auth/loginTest")
    public String loginPage() {
        return "test/loginTest";
    }

    @PostMapping("/aauth/loginTest")
    public @ResponseBody ResponseDto<Integer> loginTest(@RequestBody User user) {
        userService.회원찾기(user.getUsername());
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }
}
