package com.campfire.smeal.controller;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.mbti.NonMemberInfoDto;
import com.campfire.smeal.handler.exception.GeneralException;
import com.campfire.smeal.handler.exception.SmErrorCode;
import com.campfire.smeal.model.Board;
import com.campfire.smeal.model.User;
import com.campfire.smeal.service.BoardService;
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

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final BoardService boardService;

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
        return "user/userUpdate";
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

    // 마이페이지(나의 게시글 보기)
    @GetMapping("/user/myPage")
    public String myPage(
            @AuthenticationPrincipal PrincipalDetails principal,
            Model model
    ) {
        // 게시글보기
        System.out.println("회원No. : "+principal.getUser().getId());
        //List<Board> boardList=
                boardService.myBoard(Long.valueOf(principal.getUser().getId()));
        return "user/myPage";
    }

}
