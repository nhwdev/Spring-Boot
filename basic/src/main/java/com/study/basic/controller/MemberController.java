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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/members")
    public String index(Model model) {
        model.addAttribute("memberList", memberRepository.findAll());
        return "/members/index";
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("member", memberRepository.findById(id).orElse(null));
        return "/members/show";
    }


    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        Member memberEntity = memberRepository.findById(id).orElseThrow();
        model.addAttribute("member", memberEntity);
        return "/members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm memberForm) {
        memberRepository.save(memberForm.toEntity());
        return "redirect:/members/" + memberForm.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        memberRepository.findById(id).ifPresent(memberRepository::delete);
        redirectAttributes.addFlashAttribute("msg", "삭제되었습니다!");
        return "redirect:/members";
    }
}
