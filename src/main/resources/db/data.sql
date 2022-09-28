INSERT INTO USER (username, password, email, nickname, userId, role, createDate)
VALUES("test1", "1234", "test1@naver.com", "test1","test1", "ROLE_USER", now());

UPDATE USER SET ROLE="ROLE_ADMIN" WHERE username="admin";

INSERT INTO SURVEYFOODMBTI (qNo, question)
VALUES
("Q1", "나는 다른 사람에 비해 많이 먹는 편이다."),
("Q2", "내가 좋아하는 음식을 보면 평소보다 많이 먹게 된다."),
("Q3", "저녁에 항상 술을 먹는다"),
("Q4", "술자리를 좋아한다."),
("Q5", "음식을 만들 경우 레시피를 참고하여 정량대로 만든다."),
("Q6", "음식을 먹기 전에 사진을 찍는다."),
("Q7", "SNS에 나온 음식점을 가보는 편이다."),
("Q8", "아침 밥을 꼭 먹는다."),
("Q9", "단체 회식이 있으면 참석하는 편이다."),
("Q10", "매운 음식을 잘 먹는다."),
("Q11", "좋아하는 음식이 생기면 그것만 먹는 경우도 있다."),
("Q12", "디저트를 좋아한다."),
("Q13", "일주일에 배달음식 3번 이상 시킨다."),
("Q14", "다음 날 먹을 음식을 미리 정하는 편이다."),
("Q15", "같이 먹을 때 남들이 ‘좀 먹어＇라고 핀잔을 준다."),
("Q16", "단 맛을 좋아한다."),
("Q17", "맛은 없지만 몸에 좋다면 먹는 편이다."),
("Q18", "집에 손님이 오면 직접 음식을 차리는 편이다."),
("Q19", "고기를 먹을 때 고기만 먹는 것보다 채소와 곁들여 먹는 것을 선호한다."),
("Q20", "집에 항상 과자가 있다."),
("Q21", "싱겁게 먹는 편이다."),
("Q22", "야식을 싫어한다."),
("Q23", "밖에 나가서 먹는 것을 좋아한다."),
("Q24", "배부름을 싫어한다."),
("Q25", "씹어 먹는 것보다 마시는 것을 좋아한다."),
("Q26", "고기보다는 해산물을 좋아한다."),
("Q27", "평소 인스턴트를 많이 먹는 편이다."),
("Q28", "남들이 나에게 음식을 잘한다고 칭찬한다."),
("Q29", "면보다는 밥을 좋아한다."),
("Q30", "회식을 갈 때 음식을 보고 갈지 말지 결정한다."),
("Q31", "비린 것을 잘 못 먹는다."),
("Q32", "주기적으로 내가 먹고 싶은 것을 먹어야 한다."),
("Q33", "만들고 싶은 음식이 있다면 찾아보고 만든다."),
("Q34", "맛있는 음식이 있으면 주위에 추천하는 편이다."),
("Q35", "음식 만들 때 재료 정량보다는 눈대중으로 한다."),
("Q36", "어류를 좋아한다."),
("Q37", "배달을 자주 시켜서 카드 등록을 해 둔 상태다."),
("Q38", "음식에 대한 칼로리를 신경 쓰면서 먹는다."),
("Q39", "음식을 만들면 실패할 때도 있다."),
("Q40", "몸에 필요한 영양소 하루 권장량을 지키려고 노력한다.");


INSERT INTO MBTITYPE (typeCode, typeName, pictureUrl)
VALUES
("A", "대식가", "https://cdn.pixabay.com/photo/2021/08/28/14/31/gluttony-6581108_960_720.jpg"),
("B", "소식좌", "https://cdn.pixabay.com/photo/2017/05/01/00/58/food-2274428_960_720.png"),
("C", "애주가", "https://cdn.pixabay.com/photo/2015/10/30/12/18/drunk-1013965_960_720.jpg"),
("D", "개척자", "https://cdn.pixabay.com/photo/2019/10/20/10/41/columbus-4563218_960_720.png"),
("E", "분석가", "https://cdn.pixabay.com/photo/2018/04/19/20/44/systems-icons-3334262_960_720.jpg"),
("F", "포토그래퍼", "https://cdn.pixabay.com/photo/2016/04/01/08/34/camera-1298800_960_720.png"),
("G", "원칙주의자", "https://cdn.pixabay.com/photo/2013/07/13/09/59/justice-156442_960_720.png"),
("H", "푸드슈타인", "https://cdn.pixabay.com/photo/2014/04/03/10/08/chemist-309922_960_720.png"),
("I", "위일리언", "https://cdn.pixabay.com/photo/2012/04/18/12/39/aliens-36912_960_720.png"),
("J", "자만추", "https://cdn.pixabay.com/photo/2021/06/10/15/06/people-6326288_960_720.png"),
("K", "푸드 장인", "https://cdn.pixabay.com/photo/2012/04/01/18/01/blacksmith-23791_960_720.png"),
("L", "강철 통규씨", "https://cdn.pixabay.com/photo/2012/04/18/14/45/king-37240_960_720.png"),
("M", "슈가홀릭", "https://cdn.pixabay.com/photo/2021/04/06/12/58/women-6156311_960_720.png"),
("N", "인어맨", "https://cdn.pixabay.com/photo/2021/01/29/08/10/monster-5960104_960_720.jpg"),
("O", "효율효율 열매", "https://cdn.pixabay.com/photo/2018/05/14/12/41/time-3399858_960_720.png");

INSERT INTO MAINGROUPINFO (foodName, naverCatCode)
VALUES
("축산물", "50000145"),
("수산물", "50000159"),
("농산물", "50000160"),
("반찬","50000146"),
("김치","50000147"),
("음료","50000148"),
("과자/베이커리","50000149"),
("유가공품","50000150"),
("냉동/간편조리식품","50000026"),
("건강식품","50000023"),
("다이어트식품","50000024"),
("전통주","50006349"),
("통조림/캔","50011940"),
("제과/제빵재료","50012460"),
("조미료","50012520"),
("식용유/오일","50012620"),
("소스/드레싱","50012782"),
("가루/분말류","50013360"),
("잼/시럽","50013520"),
("라면/면류","50013960"),
("장류","50013881"),
("밀키트","50014240")
;

INSERT INTO SUBGROUPINFO (foodName, naverCatCode, mainId)
VALUES
("쇠고기","50001171","1"),
("닭고기","50001172","1"),
("양고기","50000280","1"),
("알류","50001173","1"),
("축산가공식품","50001174","1"),
("기타육류","50000215","1"),
("오리고기","50013600","1"),
("생선","50001175","2"),
("김/해초","50001176","2"),
("해산물/어패류","50001049","2"),
("젓갈/장류","50001050","2"),
("건어물","50001051","2"),
("수산가공식품","50013900","2"),
("쌀","50001052","3"),
("잡곡/혼합곡","50001053","3"),
("과일","50000960","3"),
("채소","50001077","3"),
("견과류","50001078","3"),
("건과류","50001093","3"),
("절임류","50002015","4"),
("조림류","50002016","4"),
("장아찌","50001916","4"),
("볶음류","50014360","4"),
("장조림","50001917","4"),
("단무지","50014340","4"),
("반찬세트","50002017","4"),
("기타반찬류","50002018","4"),
("포기김치","50002019","5"),
("갓김치","50002020","5"),
("총각김치","50002021","5"),
("깍두기","50002022","5"),
("겉절이","50002023","5"),
("나박김치","50002024","5"),
("동치미","50002025","5"),
("묵은지","50002026","5"),
("백김치","50002027","5"),
("열무김치","50002028","5"),
("별미김치","50002030","5"),
("절임배추","50002031","5"),
("파김치","50002029","5"),
("오이소박이","50014081","5"),
("생수","","6"),
("탄산수","","6"),
("청량/탄산음료","","6"),
("주스/과즙음료","","6"),
("커피","","6"),
("차류","","6"),
("건강/기능성음료","","6"),
("전통/차음료","","6"),
("두유","","6"),
("우유/요구르트","","6"),
("파우더/스무디","","6"),
("코코아","","6"),
("쿠키","","7"),
("초콜릿","","7"),
("사탕","","7"),
("스낵","","7"),
("껌","","7"),
("엿","","7"),
("젤리","","7"),
("푸딩","","7"),
("캐러멜","","7"),
("팝콘/강냉이류","","7"),
("강정","","7"),
("한과","","7"),
("전병","","7"),
("화과자","","7"),
("가공안주류","","7"),
("기타과자","","7"),
("아이스크림/빙수","","7"),
("떡","","7"),
("빵","","7"),
("케이크","","7"),
("시리얼","","7"),
("치즈","","8"),
("생크림","","8"),
("마가린","","8"),
("버터","","8"),
("연유","","8"),
("휘핑크림","","8"),
("피자","50001867","9"),
("핫도그","50001868","9"),
("햄버거","50001869","9"),
("딤섬","50001870","9"),
("만두","50001871","9"),
("채식푸드","50001872","9"),
("샐러드","50001873","9"),
("어묵","50001874","9"),
("즉석국/즉석탕","50001876","9"),
("튀김류","50001877","9"),
("기타냉동/간편조리식품","50001878","9"),
("도시락","50006199","9"),
("떡볶이","50012340","9"),
("카레/짜장","50012302","9"),
("함박/미트볼","50012360","9"),
("맛살/게살","50012380","9"),
("누룽지","50012400","9"),
("스프","50012303","9"),
("즉석밥","50012420","9"),
("죽","50012440","9"),
("건강환/정","","10"),
("건강분말","","10"),
("비타민제","","10"),
("인삼","","10"),
("한방재료","","10"),
("꿀","","10"),
("영양제","","10"),
("건강즙/과일즙","","10"),
("홍삼","","10"),
("환자식/영양보충식","","10"),
("다이어트차","","11"),
("다이어트바","","11"),
("가르시니아","","11"),
("식이섬유","","11"),
("CLA","","11"),
("뷰티푸드","","11"),
("기타다이어트식품","","11"),
("곤약","","11"),
("콜라겐","","11"),
("와일드망고","","11"),
("잔티젠","","11"),
("카테킨","","11"),
("히알루론산","","11"),
("레몬밤","","11"),
("단백질보충제","","11"),
("시서스","","11"),
("시네트롤","","11"),
("막걸리/탁주","","12"),
("약주","","12"),
("소주","","12"),
("일반증류수","","12"),
("리큐르주","","12"),
("기타주류","","12"),
("과실주","","12"),
("전통주선물세트","","12"),
("참치/연어","50011941","13"),
("꽁치/고등어","50011960","13"),
("골뱅이/번데기","50011980","13"),
("옥수수/콩","50012000","13"),
("황도/과일","50011942","13"),
("햄버거","50011943","13"),
("피클/올리브","50011921","13"),
("세트","50012020","13"),
("기타통조림/캔","50012040","13"),
("베이킹파우더","50012480","14"),
("제과/제빵믹스","50012201","14"),
("호떡믹스","50012441","14"),
("기타제과/제빵재료","50012500","14"),
("고춧가루","50012540","15"),
("식초","50012461","15"),
("액젓","50012521","15"),
("고추냉이","50012442","15"),
("후추","50012304","15"),
("겨자","50012560","15"),
("물엿/올리고당","50011944","15"),
("소금","50012580","15"),
("천연감미료","50012600","15"),
("설탕","50012581","15"),
("기타조미료","50012421","15"),
("대두유","","16"),
("올리브유","","16"),
("카놀라유","","16"),
("포도씨유","","16"),
("해바라기씨유","","16"),
("들기름","","16"),
("참기름","","16"),
("아보카도오일","","16"),
("기타기름","","16"),
("마요네즈","","17"),
("케첩","","17"),
("스파게티/파스타소스","","17"),
("칠리/핫소스","","17"),
("돈가스소스","","17"),
("스테이크/바베큐소스","","17"),
("굴소스","","17"),
("발사믹드레싱","","17"),
("오리엔탈드레싱","","17"),
("머스타드소스","","17"),
("기타소스/드레싱","","17"),
("튀김가루","","18"),
("부침가루","","18"),
("빵가루","","18"),
("오트밀","","18"),
("밀가루","","18"),
("찹쌀가루","","18"),
("쌀가루","","18"),
("콩가루","","18"),
("아몬드가루","","18"),
("기타분말가루","","18"),
("딸기잼","","19"),
("사과잼","","19"),
("땅콩잼","","19"),
("초코잼","","19"),
("메이플시럽","","19"),
("커피시럽","","19"),
("초코시럽","","19"),
("기타잼/시럽","","19"),
("라면","","20"),
("면류","","20"),
("고추장","","21"),
("된장","","21"),
("간장","","21"),
("청국장","","21"),
("쌈장","","21"),
("양념장","","21"),
("메주","","21"),
("낫토","","21"),
("기타장류","","21"),
("밥/죽","","22"),
("찌개/국","","22"),
("면/파스타","","22"),
("구이","","22"),
("볶음/튀김","","22"),
("조림/찜","","22"),
("간식/디저트","","22"),
("세트요리","","22")
--("돼지고기", "50001170","1"),
--("쇠고기", "50001171","1"),
--("닭고기", "50001172","1"),
--("양고기", "50000280","1"),
--("알류","50001173","1"),
--("축산가공식품","50001174","1"),
--("기타육류","50000215","1"),
--("오리고기","50013600","1"),
--("생선", "50001175","2"),
--("건어물", "50001051","2"),
--("김/해초","50001176","2"),
--("해산물/어패류", "50001049","2"),
--("젓갈/장류","50001050","2"),
--("건어물","50001051","2"),
--("수산가공식품","50013900","2"),
--("쌀","50001052","3"),
--("잡곡/혼합곡","50001053","3"),
--("과일","50000960","3"),
--("채소","50001077","3"),
--("견과류","50001078","3"),
--("건과류","50001093","3"),
--("절임류","50002015","4"),
--("조림류","50002016","4"),
--("장아찌","50001916","4"),
--("볶음류","50014360","4"),
--("장조림","50001917","4"),
--("단무지","50014340","4"),
--("반찬세트","50002017","3"),
--("기타반찬류","50002018","3"),
--("포기김치","50002019","4"),
--("갓김치","50002020","4"),
--("총각김치","50002021","4"),
--("깍두기","50002022","4"),
--("겉절이","50002023","4"),
--("나박김치","50002024","4"),
--("동치미","50002025","4"),
--("묵은지","50002026","4"),
--("백김치","50002027","4"),
--("열무김치","50002028","4"),
--("별미김치","50002030","4"),
--("절임배추","50002031","4"),
--("파김치","50002029","4"),
--("오이소박이","50014081","4"),
--("피자","50001867","9"),
--("핫도그","50001868","9"),
--("햄버거","50001869","9"),
--("딤섬","50001870","9"),
--("만두","50001871","9"),
--("채식푸드","50001872","9"),
--("샐러드","50001873","9"),
--("어묵","50001874","9"),
--("즉석국/즉석탕","50001876","9"),
--("튀김류","50001877","9"),
--("기타냉동/간편조리식품","50001878","9"),
--("도시락","50006199","9"),
--("떡볶이","50012340","9"),
--("카레/짜장","50012302","9"),
--("함박/미트볼","50012360","9"),
--("맛살/게살","50012380","9"),
--("누룽지","50012400","9"),
--("스프","50012303","9"),
--("즉석밥","50012420","9"),
--("죽","50012440","9"),
--("참치/연어","50011941","13"),
--("꽁치/고등어","50011960","13"),
--("골뱅이/번데기","50011980","13"),
--("옥수수/콩","50012000","13"),
--("황도/과일","50011942","13"),
--("햄버거","50011943","13"),
--("피클/올리브","50011921","13"),
--("세트","50012020","13"),
--("기타통조림/캔","50012040","13"),
--("베이킹파우더","50012480","14"),
--("제과/제빵믹스","50012201","14"),
--("호떡믹스","50012441","14"),
--("기타제과/제빵재료","50012500","14"),
--("고춧가루","50012540","15"),
--("식초","50012461","15"),
--("액젓","50012521","15"),
--("고추냉이","50012442","15"),
--("후추","50012304","15"),
--("겨자","50012560","15"),
--("물엿/올리고당","50011944","15"),
--("소금","50012580","15"),
--("천연감미료","50012600","15"),
--("설탕","50012581","15"),
--("기타조미료","50012421","15"),
--("밥/죽","","22"),
--("찌개/국","","22"),
--("면/파스타","","22"),
--("구이","","22"),
--("볶음/튀김","","22"),
--("조림/찜","","22"),
--("간식/디저트","","22"),
--("세트요리","","22")
;