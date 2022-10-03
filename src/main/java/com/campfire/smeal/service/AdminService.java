package com.campfire.smeal.service;

import com.campfire.smeal.dto.memberStatusDto;
import com.campfire.smeal.handler.exception.GeneralException;
import com.campfire.smeal.handler.exception.SmErrorCode;
import com.campfire.smeal.model.RoleType;
import com.campfire.smeal.model.User;
import com.campfire.smeal.model.mbti.SurveyFoodMbti;
import com.campfire.smeal.model.memNonmemRatioHist;
import com.campfire.smeal.repository.SurveyRepository;
import com.campfire.smeal.repository.SurveyResultRepository;
import com.campfire.smeal.repository.UserRepository;
import com.campfire.smeal.repository.memNonmemRatioHistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final SurveyResultRepository surveyResultRepository;
    private final memNonmemRatioHistRepository memNonmemRatioHistRepository;


    @Transactional
    public List<SurveyFoodMbti> surveyFindAll() {
        return surveyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<User> userList(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public User memLevelUp(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new GeneralException(SmErrorCode.NO_USER));
        System.out.println(user.getId());
        System.out.println(user);
        if (user != null) {
            System.out.println("null아님");
            user.setRole(RoleType.ROLE_ADMIN);
            //user.setRole(RoleType.ROLE_MANAGER);
        }
        return user;
    }

    // 회원/비회원 비율 계산 - 매일 오전 1시에 실행
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void memRatioSave() {
        memNonmemRatioHist memNonmemRatio = new memNonmemRatioHist();
        List<memberStatusDto> memberStatusDtos = surveyResultRepository.memStatusRatio();
        int total = 0;
        for (int i = 0; i < memberStatusDtos.size(); i++) {
            total += Integer.valueOf(memberStatusDtos.get(i).getCnt());
            if (memberStatusDtos.get(i).getMemberStatus().equals("Y")) {
                memNonmemRatio.setMembers(Integer.valueOf(memberStatusDtos.get(i).getCnt()));
            } else if (memberStatusDtos.get(i).getMemberStatus().equals("N")) {
                memNonmemRatio.setNonMembers(Integer.valueOf(memberStatusDtos.get(i).getCnt()));
            }
        }
        memNonmemRatio.setTotal(total);
        memNonmemRatioHistRepository.save(memNonmemRatio);
    }

    // 회원-비회원 비율
    @Transactional
    public Map<String, Double> memRatio() {
        Map<String, Double> ratioMap = new HashMap<>();

        memNonmemRatioHist ratio = memNonmemRatioHistRepository.memStatusRatio();
        Double memRatio = Math.round(ratio.getMembers() / Double.valueOf(ratio.getTotal()) * 100) / 100.0;
        Double nonMemRatio = Math.round(ratio.getNonMembers() / Double.valueOf(ratio.getTotal()) * 100) / 100.0;

        ratioMap.put("memRatio", memRatio);
        ratioMap.put("nonMemRatio", nonMemRatio);
        return ratioMap;
    }

}
