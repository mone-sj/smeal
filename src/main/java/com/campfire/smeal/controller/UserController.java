package com.campfire.smeal.controller;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.repository.MbtiTypeRepository;
import com.campfire.smeal.repository.SearchFoodRankRepository;
import com.campfire.smeal.service.BoardService;
import com.campfire.smeal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final BoardService boardService;
    private final SearchFoodRankRepository searchFoodRankRepository;
    private final MbtiTypeRepository mbtiTypeRepository;

    // 대시보드 페이지
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal PrincipalDetails principalDetails,
                            Model model
    ) {
        model.addAttribute("foodRankAllList",
                searchFoodRankRepository.typeAll());
        model.addAttribute("foodRankTypeList",
                searchFoodRankRepository.typeRank(principalDetails.getUser().getFoodMbti()));

        model.addAttribute("mbtiName", mbtiTypeRepository.selectMbtiName(principalDetails.getUser().getFoodMbti()));
        return "dashboard";
    }

    @GetMapping("/auth/forwardLogin")
    public String successForwardLogin(@AuthenticationPrincipal
                                      PrincipalDetails principalDetails) {
        System.out.println("role: " + principalDetails.getUser().getRole());
        if (principalDetails.getUser().getRole().equals("ROLE_USER")) {
            return "dashboard";
        } else if (principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            return "admin/statistics";
        } else {
            return "/";
        }

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
    public String userUpdate(
            @AuthenticationPrincipal PrincipalDetails principal) {
        return "user/userUpdate";
    }

}
