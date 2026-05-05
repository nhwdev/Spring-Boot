package com.study.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi") // URL 요청 접수
    public String niceToMeetYou(Model model) {
        model.addAttribute("username", "웅이");
        return "greetings"; // 뷰 템플릿 페이지 반환
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "웅이");
        return "goodbye";
    }
}
