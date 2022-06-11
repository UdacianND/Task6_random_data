package com.example.task6_random_data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomePageController {

    @GetMapping
    private String homePage(HttpSession session){
        return "home-page";
    }

}
