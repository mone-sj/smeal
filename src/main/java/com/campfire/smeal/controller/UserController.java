package com.campfire.smeal.controller;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.handler.exception.GeneralException;
import com.campfire.smeal.handler.exception.SmErrorCode;
import com.campfire.smeal.model.User;
import com.campfire.smeal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    // 대시보드 페이지
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
//        return "test/dashboard";
    }

    //로그인페이지
    @GetMapping("/auth/login")
    public String loginForm(
            @RequestParam(value = "exception", required = false) String exception,
            Model model
    ) {
        model.addAttribute("exception", exception);
        return "user/login";
//        return "test/login";
    }

    //회원가입 페이지
    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/register";
//        return "test/register";
    }

    // 회원 수정 페이지
    @GetMapping("/user/update")
    public String userUpdate(
            @AuthenticationPrincipal PrincipalDetails principal) {
        return "updateForm";
//        return "test/userUpdate";
    }

    //////////////////////// 테스트용_기능완성및매핑이 완료되면 삭제
    // 회원가입 테스트용
   /* @PostMapping("/auth/joinProc")
    public String userSave(
            @RequestParam(required = false) String username,
            @RequestParam(required = true) String password,
            @RequestParam(required = true) String passwordRepeat,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nickname
    ){
        User user=User.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();
        userService.회원가입(user, passwordRepeat);
        log.info("회원가입완료");
        return "index";
    }

    // 회원수정 테스트
    @PutMapping("/auth/updateProc")
    public String update(
            @RequestParam(required = true) Long id,
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String password,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = true) String passwordRepeat,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {

        User userDto=User.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();
        User updateUser = userService.회원수정(userDto, passwordRepeat);

        // 세션 수정
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        return "redirect:/dashboard";
    }
*/




}
