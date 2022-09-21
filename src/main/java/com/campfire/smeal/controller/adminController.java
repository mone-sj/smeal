package com.campfire.smeal.controller;

import com.campfire.smeal.repository.SurveyRepository;
import com.campfire.smeal.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class adminController {

    private final AdminService adminService;

    // 회원관리 페이지
    @GetMapping("/admin/memberManage")
    public String member() {
        return "test/memberManage";
    }

    // MBTI 질문지 관리 페이지
    @GetMapping("/admin/mbtiQuestion")
    public String mbtiQuestion(Model model) {
        model.addAttribute("QuestionList",adminService.surveyFindAll());
        return "test/mbtiQuestion";
    }

    // 게시판 관리 페이지
    @GetMapping("/admin/board")
    public String board() {
        return null;
    }

    // 통계 페이지
    @GetMapping("/admin/statistics")
    public String statistics() {
        return null;
    }

}
