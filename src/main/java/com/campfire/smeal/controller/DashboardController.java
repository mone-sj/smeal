package com.campfire.smeal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping({"/","dashboard"})
    public String dashboard(){
        return "index";
    }

}
