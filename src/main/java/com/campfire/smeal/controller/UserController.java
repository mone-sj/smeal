package com.campfire.smeal.controller;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.mbti.NonMemberInfoDto;
import com.campfire.smeal.handler.exception.GeneralException;
import com.campfire.smeal.handler.exception.SmErrorCode;
import com.campfire.smeal.model.User;
import com.campfire.smeal.service.UserService;
import lombok.NonNull;
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
//        return "dashboard";
        return "test/dashboard";
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

    // mbti검사 후 회원가입 시 - 삭제예정
//    @PostMapping("/auth/joinMem")
//    public String joinNonMember(
//            @RequestParam(required = false) String age,
//            @RequestParam(required = false) String gender,
//            @RequestParam(required = false) String mbtiType,
//            Model model
//    ) {
//        NonMemberInfoDto nonMemberInfoDto = new NonMemberInfoDto(age, gender, mbtiType);
//        System.out.println("nonMemberInfoDto");
//        System.out.println(nonMemberInfoDto);
//        model.addAttribute("nonMemberInfo", nonMemberInfoDto);
//        return "user/register";
//    }



    // 회원 수정 페이지
    @GetMapping("/user/update")
    public String userUpdate(
            @AuthenticationPrincipal PrincipalDetails principal) {
//        return "updateForm";
        return "test/userUpdate";
    }

    // ajax 사용하지 않았을때
    @PostMapping("/auth/joinProc")
    public String userSave(
            @RequestParam(required = true) @NonNull String username,
            @RequestParam(required = true) @NonNull String password,
            @RequestParam(required = true) @NonNull String passwordRepeat,
            @RequestParam(required = true) @NonNull String email,
            @RequestParam(required = true) @NonNull String nickname,
            @RequestParam(required = false) String age,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String mbtiType
    ){
        System.out.println("age: " + age);
        System.out.println("gender: " + gender);
        System.out.println("mbtiType: " + mbtiType);

        User user=User.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .age(age)
                .gender(gender)
                .foodMbti(mbtiType)
                .build();
        userService.회원가입(user, passwordRepeat);
        log.info("회원가입완료");
        return "redirect:/";
    }

    //////////////////////// 테스트용_기능완성및매핑이 완료되면 삭제
//
//    // 회원수정 테스트
//    @PutMapping("/auth/updateProc")
//    public String update(
//            @RequestParam(required = true) Long id,
//            @RequestParam(required = true) String username,
//            @RequestParam(required = true) String password,
//            @RequestParam(required = false) String email,
//            @RequestParam(required = false) String nickname,
//            @RequestParam(required = true) String passwordRepeat,
//            @AuthenticationPrincipal PrincipalDetails principalDetails
//    ) {
//
//        User userDto=User.builder()
//                .id(id)
//                .username(username)
//                .password(password)
//                .email(email)
//                .nickname(nickname)
//                .build();
//        User updateUser = userService.회원수정(userDto, passwordRepeat);
//
//        // 세션 수정
//        Authentication authentication= authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
//        SecurityContextHolder.getContext()
//                .setAuthentication(authentication);
//        return "redirect:/dashboard";
//    }




}
