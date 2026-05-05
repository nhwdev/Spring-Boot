package com.study.basic.controller;

import com.study.basic.dto.MemberForm;
import com.study.basic.entity.Member;
import com.study.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/signup")
    public String newMemberForm(){
        return "/members/new";
    }

    @PostMapping("/join")
    public String joinMember(@ModelAttribute MemberForm memberForm){
        memberRepository.save(memberForm.toEntity());
        return "";
    }
}
