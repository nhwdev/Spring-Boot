package com.study.basic.controller;

import com.study.basic.dto.MemberForm;
import com.study.basic.entity.Member;
import com.study.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        Member joined = memberRepository.save(memberForm.toEntity());
        return "redirect:/members/" + joined.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("member", memberRepository.findById(id).orElse(null));
        return "/members/show";
    }

    @GetMapping("/members")
    public String index(Model model) {
        model.addAttribute("memberList", memberRepository.findAll());
        return "/members/index";
    }
}
