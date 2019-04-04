package com.jh.jbook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/jh")
    public String home() {
        return "Hello, Jbook!";
    }

}