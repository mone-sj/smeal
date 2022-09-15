package com.campfire.smeal.service;

import com.campfire.smeal.model.search.MainGroupInfo;
import com.campfire.smeal.model.search.SubGroupInfo;
import com.campfire.smeal.repository.SubGroupRepository;
import com.campfire.smeal.repository.MainGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RecipeSearchService {

    private final MainGroupRepository mainGroupRepository;
    private final SubGroupRepository subGroupRepository;

    @Transactional
    public Map<String, Object> cateListFindAll() {
        Map<String, Object> result = new HashMap<>();
        List<MainGroupInfo> mainGroupInfoList = mainGroupRepository.findAll();
        System.out.println("mainGroupInfoList");
        System.out.println(mainGroupInfoList);
        result.put("mainGroupList", mainGroupInfoList);

        List<SubGroupInfo> subGroupInfoList = subGroupRepository.findAll();
        result.put("subCateList", mainGroupRepository.findAll());
        return result;
    }

    @Transactional
    public List<MainGroupInfo> mainGroupListFindAll() {
        return mainGroupRepository.findAll();
    }

    @Transactional
    public List<SubGroupInfo> subGroupInfoList(int groupId){
        List<SubGroupInfo> subGroupInfoList = subGroupRepository.findSubGroup(groupId);
        System.out.println("subGroupInfoList");
        System.out.println(subGroupInfoList);
        return subGroupInfoList;
    }
}
