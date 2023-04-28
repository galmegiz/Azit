package com.sun.Azit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model){
        return "redirect:/events";
    }

    @GetMapping("/main")
    public String home2(){
        return "main2";
    }
}
