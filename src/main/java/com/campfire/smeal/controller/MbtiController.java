package com.campfire.smeal.controller;

import com.campfire.smeal.dto.mbti.MbtiResponseDto;
import com.campfire.smeal.model.SurveyFoodMbti;
import com.campfire.smeal.service.MbtiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MbtiController {

    private final MbtiService mbtiService;


    @GetMapping("/mbti")
    public String mbtiSurvey(Model model){

        List<SurveyFoodMbti> questionList = mbtiService.surveyRead();

        model.addAttribute("questions", questionList);

        return "mbti/mbtiSurvey";
    }

    @GetMapping("/mbti/result/{request}")
    public String mbtiResult(RedirectAttributes redirectAttributes, @PathVariable String request){
        System.out.println("===============여기 왔음==============");
        System.out.println(request);
        String[] resultArray = request.split(",");
        ArrayList<MbtiResponseDto> mbtiResponseDtos = new ArrayList<MbtiResponseDto>();

        String resultTypeCode = resultArray[resultArray.length-1];
        for (int i=1; i<resultArray.length; i++) {
            String qNo = "Q"+i;

            mbtiResponseDtos.add(new MbtiResponseDto(qNo, resultArray[i-1], resultTypeCode));
        }

        mbtiService.surveyResultSave(mbtiResponseDtos);
        redirectAttributes.addAttribute("type", resultTypeCode);


        return "redirect:/mbti/result";
    }


    @RequestMapping("/mbti/result")
    public String mbtiResultView(@RequestParam String type,
                                 Model model) {
        System.out.println(type);
        model.addAttribute("type", type);
        return "mbti/mbtiResult";
    }
}
