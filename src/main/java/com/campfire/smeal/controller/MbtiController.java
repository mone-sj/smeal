package com.campfire.smeal.controller;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.mbti.MbtiResponseDto;
import com.campfire.smeal.dto.mbti.NonMemberInfoDto;
import com.campfire.smeal.model.User;
import com.campfire.smeal.model.mbti.MbtiType;
import com.campfire.smeal.model.mbti.SurveyFoodMbti;
import com.campfire.smeal.service.MbtiService;
import com.campfire.smeal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

        List<SurveyFoodMbti> questionList = mbtiService.surveyRead();

        model.addAttribute("questions", questionList);

        return "mbti/mbtiSurvey";
    }

    @GetMapping("/mbti/result/{request}")
    public String mbtiResult(RedirectAttributes redirectAttributes,
                             Model model,
                             @PathVariable String request,
                             @AuthenticationPrincipal PrincipalDetails principalDetails){

        System.out.println(request);
        String[] resultArray = request.split(",");
        ArrayList<MbtiResponseDto> mbtiResponseDtos = new ArrayList<MbtiResponseDto>();

        String age = resultArray[resultArray.length - 1];
        String gender = resultArray[resultArray.length - 2];
        String resultTypeCode = resultArray[resultArray.length-3];

        System.out.println("principalDetails");
        System.out.println(principalDetails);

        if (principalDetails != null) {
            Long id = principalDetails.getUser().getId();
            userService.회원정보추가(id, gender, age, resultTypeCode);
        } else {
            NonMemberInfoDto nonMember = new NonMemberInfoDto(age, gender, resultTypeCode);
            log.info("nonMember");
            System.out.println(nonMember);
            redirectAttributes.addFlashAttribute("nonMember", nonMember);
        }

        for (int i = 1; i < resultArray.length-2; i++) {
            String qNo = "Q" + i;
            mbtiResponseDtos.add(new MbtiResponseDto(qNo, resultArray[i - 1], resultTypeCode));
        }

        mbtiService.surveyResultSave(mbtiResponseDtos);
        redirectAttributes.addAttribute("type", resultTypeCode);

        return "redirect:/mbti/result";
    }


    @RequestMapping("/mbti/result")
    public String mbtiResultView(@RequestParam String type,
                                 Model model) {
        System.out.println(type);
        MbtiType mbtiContent = mbtiService.findMbtiType(type);

        List<String> recomFoodList = Arrays.asList(mbtiContent.getRecommendFood().split(", "));

        model.addAttribute("mbti", mbtiContent);
        model.addAttribute("recommendRecipe", recomFoodList);
        return "mbti/mbtiResult";
    }

}
