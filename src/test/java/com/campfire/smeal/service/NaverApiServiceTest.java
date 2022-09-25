package com.campfire.smeal.service;

import com.campfire.smeal.dto.api.NaverApiTrendShoppingTargetRes;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.TargetRes;
import com.campfire.smeal.dto.api.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class NaverApiServiceTest {

//    @InjectMocks
//    private NaverApiService naverApiService;


    @Test
    void test() throws ParseException {
        LocalDate today = LocalDate.now();
        System.out.println("today: " + today);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedToday = today.format(formatter);

        String strDate = "20220220";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        Date dt = format.parse(strDate);
        cal.setTime(dt);

        cal.add(Calendar.YEAR, 0);
        cal.add(Calendar.MONTH, -3);
        cal.add(Calendar.DATE, 0);

        System.out.println(format.format(cal.getTime()));


    }

    @Test
    void datetest() throws ParseException {
        HashMap<String, String> period = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);

        String startDate = formatter.format(cal.getTime());
        period.put("startDate", startDate);

        cal.add(Calendar.MONTH, -2);
        String endDate = formatter.format(cal.getTime());
        period.put("endDate", endDate);

        System.out.println("startDate: "+period.get("startDate"));
        System.out.println("endDate: "+period.get("endDate"));

    }

    @Test
    void stringAddTest() throws ParseException {
        String value1 = "12.45";
        String value2 = "33.12";

        Double result = Double.valueOf(value1) + Double.valueOf(value2);
        System.out.println("result(double): "+result);
        String resultString = String.valueOf(result);
        System.out.println("resultString: "+resultString);

    }

    @Test
    void nameSortTest() {
        List<Recipe.RespFood> foodList = new ArrayList<>();
        Recipe.RespFood food1=Recipe.RespFood.builder()
                .rcp_nm("인삼떡갈비")
                .att_file_no_main("")
                .rcp_way2("")
                .build();

        Recipe.RespFood food2=Recipe.RespFood.builder()
                .rcp_nm("나가사키부대찌개")
                .att_file_no_main("")
                .rcp_way2("")
                .build();

        Recipe.RespFood food3=Recipe.RespFood.builder()
                .rcp_nm("라이스버거떡갈비")
                .att_file_no_main("")
                .rcp_way2("")
                .build();

        Recipe.RespFood food4=Recipe.RespFood.builder()
                .rcp_nm("나로시작하는음식")
                .att_file_no_main("")
                .rcp_way2("")
                .build();

        Recipe.RespFood food5=Recipe.RespFood.builder()
                .rcp_nm("하로 시작하는음식")
                .att_file_no_main("")
                .rcp_way2("")
                .build();

        Recipe.RespFood food6=Recipe.RespFood.builder()
                .rcp_nm("해해로 시작하는음식")
                .att_file_no_main("")
                .rcp_way2("")
                .build();

        foodList.add(food1);
        foodList.add(food2);
        foodList.add(food3);
        foodList.add(food4);
        foodList.add(food5);
        foodList.add(food6);

        Collections.sort(foodList, Recipe.foodNameComparator);

        foodList.stream().forEach(respFood -> System.out.println(respFood.getRcp_nm()));

        String[] chs = {
                "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ",
                "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
                "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ",
                "ㅋ", "ㅌ", "ㅍ", "ㅎ"
        };

        LinkedHashMap<String, List<Recipe.RespFood>> foodListSortMap = new LinkedHashMap<>();
        List<Recipe.RespFood> newFoodList = new ArrayList<>();
        String chosung = "";
        for (int i = 0; i < foodList.size(); i++) {
            System.out.println("i값: "+i);
            System.out.println("foodList.size(): " + foodList.size());
            System.out.println(foodList.get(i).getRcp_nm());
            String text=foodList.get(i).getRcp_nm();
            Recipe.RespFood respFood = foodList.get(i);
            if (text.length() > 0) {
                char chName = text.charAt(0);
                if (chName >= 0xAC00) { // 한글일 경우만 시작
                    // 한글이 유니코드에 저장되는 규칙
                    // (초성 * 21 + 중성) * 28 + 종성 + 0xAC00 = 한글
                    int cho = (int) ((chName - 0xAC00) / 28 / 21);
                    System.out.println("초성은: ");
                    System.out.println(chs[cho]);

                    if (chosung.equals("")) {
                        System.out.println("초성이 빈칸일때");
                        System.out.println(chosung);
                        newFoodList.add(respFood);
                        chosung = chs[cho];
                    } else if (!chosung.equals(chs[cho])) {
                        System.out.println("초성이 그전과 다를때 if");
                        System.out.println(chosung);
                        foodListSortMap.put(chosung, newFoodList);
                        System.out.println(foodListSortMap);
                        newFoodList = new ArrayList<>();
                        newFoodList.add(respFood);
                        chosung = chs[cho];
                        System.out.println("newFoodList: "+newFoodList);
                        System.out.println("chosung: " + chosung);
                    } else if (i == foodList.size()-1) {
                        System.out.println("마지막 값");
                        newFoodList.add(respFood);
                        foodListSortMap.put(chosung, newFoodList);
                    } else {
                        System.out.println("초성이 그전이랑 같을때");
                        System.out.println(chosung);
                        newFoodList.add(respFood);
                    }

                }
            }
        }

        System.out.println("Map 결과: ");
        System.out.println(foodListSortMap);


    }

    @Test
    void mapTest() {

        NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes
                mRearrRes=NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes.builder()
                .group("m")
                .ratio("23")
                .build();

        NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes
                gRearrRes=NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes.builder()
                .group("g")
                .ratio("56")
                .build();

        ArrayList<NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes> rearrResList = new ArrayList<>();
        rearrResList.add(mRearrRes);
        rearrResList.add(gRearrRes);

        TargetRes
                genderResult= TargetRes.builder()
                .keyword("된장찌개")
                .results(rearrResList)
                .build();

        NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes
                m2RearrRes=NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes.builder()
                .group("m")
                .ratio("55")
                .build();

        NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes
                g2RearrRes=NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes.builder()
                .group("g")
                .ratio("98")
                .build();

        ArrayList<NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes> rearrResList2 = new ArrayList<>();
        rearrResList2.add(m2RearrRes);
        rearrResList2.add(g2RearrRes);

        TargetRes
                genderResult2= TargetRes.builder()
                .keyword("김치찌개")
                .results(rearrResList2)
                .build();

        List<TargetRes> resList = new ArrayList<>();
        resList.add(genderResult);
        resList.add(genderResult2);

        System.out.println(resList);

        List<String> targetList = new ArrayList<>();
        List<String> resultValuesList = new ArrayList<>();
        Map<String, List<String>> valueMap = new HashMap<>();

        for (TargetRes result : resList){
            targetList.add(result.getKeyword());
            System.out.println(result.getKeyword());
            System.out.println("valueMap");
            System.out.println(valueMap);

            for (NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes value : result.getResults()) {
                if (valueMap.containsKey(value.getGroup())) {
                    System.out.println("key값 있을때");
                    System.out.println(value.getGroup());
                    System.out.println(value.getRatio());
                    resultValuesList = valueMap.get(value.getGroup());
//                    tempList = valueMap.get(value.getGroup());
                    System.out.println("resultValuesList 가져오기");
                    System.out.println(resultValuesList);
                    resultValuesList.add(value.getRatio());
                    //valueMap.remove(value.getGroup());
                    valueMap.replace(value.getGroup(), resultValuesList);
                    resultValuesList = new ArrayList<>();
                    System.out.println("resultValuesList 삭제됐는지 확인: "+resultValuesList);
                    System.out.println(resultValuesList);
                    System.out.println(valueMap);
                } else {
                    System.out.println("key값 없을때");
                    System.out.println(value.getGroup());
                    System.out.println(value.getRatio());
                    resultValuesList.add(value.getRatio());
                    valueMap.put(value.getGroup(), resultValuesList);
                    resultValuesList = new ArrayList<>();
                    System.out.println("resultValuesList 삭제됐는지 확인: "+resultValuesList);
                    System.out.println(resultValuesList);
                    System.out.println(valueMap);
                }
            }

        }

        System.out.println("targetList");
        System.out.println(targetList);
        System.out.println("valueMap");
        System.out.println(valueMap);


    }

}