package com.campfire.smeal.controller;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.mbti.MbtiResponseDto;
import com.campfire.smeal.dto.mbti.NonMemberInfoDto;
import com.campfire.smeal.model.User;
import com.campfire.smeal.model.mbti.MbtiType;
import com.campfire.smeal.service.MbtiService;
import com.campfire.smeal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MbtiController {

    private final MbtiService mbtiService;
    private final UserService userService;


    @GetMapping("/mbti")
    public String mbtiSurvey(Model model){
        model.addAttribute("questions",
                mbtiService.surveyRead());
        return "mbti/mbtiSurvey";
    }

    // mbti 검사결과 DB 저장 (검사 결과값은 모델에서 전달받음)
    @GetMapping("/mbti/result/{request}")
    public String mbtiResult(RedirectAttributes redirectAttributes,
                             Model model,
                             @PathVariable String request,
                             @AuthenticationPrincipal PrincipalDetails principalDetails){

        String[] resultArray = request.split(",");
        ArrayList<MbtiResponseDto> mbtiResponseDtos = new ArrayList<MbtiResponseDto>();

        String age = resultArray[resultArray.length - 1];
        String gender = resultArray[resultArray.length - 2];
        String resultTypeCode = resultArray[resultArray.length-3];

        String memberStatus = null; // 회원이면 Y, 비회원이면 N
        if (principalDetails != null) {
            Long id = principalDetails.getUser().getId();
            User updateUser = userService.회원정보추가(id, gender, age, resultTypeCode);
            principalDetails.setUser(updateUser);
            memberStatus = "Y";
        } else {
            NonMemberInfoDto nonMember = new NonMemberInfoDto(age, gender, resultTypeCode);
            redirectAttributes.addFlashAttribute("nonMember", nonMember);
            memberStatus = "N";
        }

        for (int i = 1; i < resultArray.length-2; i++) {
            String qNo = "Q" + i;
            mbtiResponseDtos.add(new MbtiResponseDto(qNo, resultArray[i - 1], resultTypeCode, age, gender, memberStatus));
        }

        mbtiService.surveyResultSave(mbtiResponseDtos);
        redirectAttributes.addAttribute("type", resultTypeCode);

        return "redirect:/mbti/result";
    }


    // mbti type에 대한 설명 DB에서 조회
    @RequestMapping("/mbti/result")
    public String mbtiResultView(@RequestParam String type,
                                 Model model) {

        MbtiType mbtiContent = mbtiService.findMbtiType(type);
        List<String> recomFoodList =
                Arrays.asList(mbtiContent.getRecommendFood().split(", "));

        model.addAttribute("mbti", mbtiContent);
        model.addAttribute("recommendRecipe", recomFoodList);
        return "mbti/mbtiResult";
    }

}
