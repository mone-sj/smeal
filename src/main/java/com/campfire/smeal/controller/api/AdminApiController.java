package com.campfire.smeal.controller.api;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.ResponseDto;
import com.campfire.smeal.model.User;
import com.campfire.smeal.service.AdminService;
import com.campfire.smeal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdminApiController {

    private final UserService userService;
    private final AdminService adminService;


    // 회원 삭제
    @DeleteMapping("/admin/deleteMem/{id}")
    public ResponseDto<Integer> deleteMem(@PathVariable int id) {
        System.out.println("회원삭제");
        userService.회원삭제(Long.valueOf(id));
        System.out.println("삭제완료?");
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // admin으로 등급업 (혹은 MANAGER)
    @GetMapping("/admin/levelUp/{id}")
    public ResponseDto<Integer> levelUpMem(@PathVariable String id){
        User levelUpUser=adminService.memLevelUp(Long.valueOf(id));
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


}
