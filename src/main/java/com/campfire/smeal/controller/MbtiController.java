package com.campfire.smeal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class MbtiController {

    @GetMapping("/mbti")
    public String mbtiSurvey(Model model){
        System.out.println("========mbti controller들어옴=======");
        ArrayList<String> questionList = new ArrayList<String>();
        ArrayList<String> questionNum = new ArrayList<>();
        questionList.add("나는 다른 사람에 비해 많이 먹는 편이다.");
        questionList.add("내가 좋아하는 음식을 보면 평소보다 많이 먹게 된다.");
        questionList.add("저녁에 항상 술을 먹는다");
        questionList.add("술자리를 좋아한다.");
        questionList.add("음식을 만들 경우 레시피를 참고하여 정량대로 만든다.");
        questionList.add("음식을 먹기 전에 사진을 찍는다.");
        questionList.add("SNS에 나온 음식점을 가보는 편이다.");
        questionList.add("아침 밥을 꼭 먹는다.");
        questionList.add("단체 회식이 있으면 참석하는 편이다.");
        questionList.add("매운 음식을 잘 먹는다.");
        questionList.add("좋아하는 음식이 생기면 그것만 먹는 경우도 있다.");
        questionList.add("디저트를 좋아한다.");
        questionList.add("일주일에 배달음식 3번 이상 시킨다.");
        questionList.add("다음 날 먹을 음식을 미리 정하는 편이다.");
        questionList.add("같이 먹을 때 남들이 ‘좀 먹어＇라고 핀잔을 준다.");
        questionList.add("단 맛을 좋아한다.");
        questionList.add("맛은 없지만 몸에 좋다면 먹는 편이다.");
        questionList.add("집에 손님이 오면 직접 음식을 차리는 편이다.");
        questionList.add("고기를 먹을 때 고기만 먹는 것보다 채소와 곁들여 먹는 것을 선호한다.");
        questionList.add("집에 항상 과자가 있다.");
        questionList.add("싱겁게 먹는 편이다.");
        questionList.add("야식을 싫어한다.");
        questionList.add("밖에 나가서 먹는 것을 좋아한다.");
        questionList.add("배부름을 싫어한다.");
        questionList.add("씹어 먹는 것보다 마시는 것을 좋아한다.");
        questionList.add("고기보다는 해산물을 좋아한다.");
        questionList.add("평소 인스턴트를 많이 먹는 편이다.");
        questionList.add("남들이 나에게 음식을 잘한다고 칭찬한다.");
        questionList.add("면보다는 밥을 좋아한다.");
        questionList.add("회식을 갈 때 음식을 보고 갈지 말지 결정한다.");
        questionList.add("비린 것을 잘 못 먹는다.");
        questionList.add("주기적으로 내가 먹고 싶은 것을 먹어야 한다.");
        questionList.add("만들고 싶은 음식이 있다면 찾아보고 만든다.");
        questionList.add("맛있는 음식이 있으면 주위에 추천하는 편이다.");
        questionList.add("음식 만들 때 재료 정량보다는 눈대중으로 한다.");
        questionList.add("어류를 좋아한다.");
        questionList.add("배달을 자주 시켜서 카드 등록을 해 둔 상태다.");
        questionList.add("음식에 대한 칼로리를 신경 쓰면서 먹는다.");
        questionList.add("음식을 만들면 실패할 때도 있다.");
        questionList.add("몸에 필요한 영양소 하루 권장량을 지키려고 노력한다.");

        questionNum.add("Q1");
        questionNum.add("Q2");
        questionNum.add("Q3");
        questionNum.add("Q4");
        questionNum.add("Q5");
        questionNum.add("Q6");
        questionNum.add("Q7");
        questionNum.add("Q8");
        questionNum.add("Q9");
        questionNum.add("Q10");
        questionNum.add("Q11");
        questionNum.add("Q12");
        questionNum.add("Q13");
        questionNum.add("Q14");
        questionNum.add("Q15");
        questionNum.add("Q16");
        questionNum.add("Q17");
        questionNum.add("Q18");
        questionNum.add("Q19");
        questionNum.add("Q20");
        questionNum.add("Q21");
        questionNum.add("Q22");
        questionNum.add("Q23");
        questionNum.add("Q24");
        questionNum.add("Q25");
        questionNum.add("Q26");
        questionNum.add("Q27");
        questionNum.add("Q28");
        questionNum.add("Q29");
        questionNum.add("Q30");
        questionNum.add("Q31");
        questionNum.add("Q32");
        questionNum.add("Q33");
        questionNum.add("Q34");
        questionNum.add("Q35");
        questionNum.add("Q36");
        questionNum.add("Q37");
        questionNum.add("Q38");
        questionNum.add("Q39");
        questionNum.add("Q40");

        model.addAttribute("questions", questionList);
        model.addAttribute("questionNum", questionNum);
        return "mbti/mbtiSurvey";
    }

    @GetMapping("/mbti/result")
    public String mbtiResult(){
        System.out.println();

        return "mbti/mbtiResult";
    }
}
