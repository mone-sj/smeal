package com.campfire.smeal.controller;

import com.campfire.smeal.model.User;
import com.campfire.smeal.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;

    // 회원관리 페이지-> 회원 등급 올리기(admin으로 수정할수 있게), 회원강제 탈퇴 시키기, 회원리스트 조회
    @GetMapping("/admin/memberManage")
    public String member(Model model,
                         @PageableDefault(size = 20, sort="id", direction = Sort.Direction.ASC)
                         Pageable pageable) {
        Page<User> userPageable = adminService.userList(pageable);
        model.addAttribute("memberList", userPageable);

        int nowPage = userPageable.getPageable().getPageNumber() + 1;
        int startPage = Math.max(1, userPageable.getPageable().getPageNumber() - 4);
        int endPage = Math.min(userPageable.getTotalPages(),
                userPageable.getPageable().getPageNumber() + 4);

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/memberManage";
    }

    // MBTI 질문지 관리 페이지-> 리스트만 조회하기, 버튼만 만들기(삭제,수정...)
    @GetMapping("/admin/mbtiQuestion")
    public String mbtiQuestion(Model model) {
        model.addAttribute("questionList",
                adminService.surveyFindAll());
        return "admin/mbtiQuestion";
    }

    // 통계 페이지 -> model 만들기(entity,table), mbti 통계관련 entity(table)만들기
    @GetMapping("/admin/statistics")
    public String statistics(Model model) {
        Map<String, Double> memRatio = adminService.memRatio();
        model.addAttribute("memRatio", memRatio.get("memRatio"));
        model.addAttribute("nonMemRatio", memRatio.get("nonMemRatio"));
        return "admin/statistics";
    }


    // 통계 save - 추후 삭제 예정 (cron으로 자동 설정함)
    @GetMapping("/auth/ratioSave")
    public @ResponseBody String ratioSave() {
        adminService.memRatioSave();
        return "완료";
    }


}
