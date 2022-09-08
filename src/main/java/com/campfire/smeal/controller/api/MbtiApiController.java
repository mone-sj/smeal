package com.campfire.smeal.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MbtiApiController {

    @PostMapping("/api/mbti")
    public void submit(){
        System.out.println("왔음");
    }



}
