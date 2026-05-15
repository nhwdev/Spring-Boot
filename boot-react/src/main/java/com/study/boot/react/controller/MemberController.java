package com.study.boot.react.controller;

import com.study.boot.react.dto.MemberDto;
import com.study.boot.react.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/idCheck")
    public ResponseEntity<Boolean> idCheck(@RequestParam String id) {
        return ResponseEntity.ok().body(memberService.idCheck(id));
    }

    @PostMapping("/joinPro")
    public ResponseEntity<?> joinPro(@ModelAttribute MemberDto memberDto) {
        memberService.insertMember(memberDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> post(@RequestBody MemberDto memberDto, HttpServletResponse response) {
        Cookie cookie = new Cookie("login", memberDto.getId());
        cookie.setHttpOnly(true);       // 💡 필수: JavaScript(리액트)에서 쿠키 탈취 방지 (XSS 공격 방어)
        // cookie.setSecure(true);         // 💡 HTTPS 통신에서만 쿠키 전송
        cookie.setPath("/");            // 💡 이 사이트의 모든 경로에서 쿠키를 사용할 수 있도록 설정
        cookie.setMaxAge(-1); // 💡 쿠키 유효 기간 설정 (초 단위, 여기서는 1일)
        // cookie.setAttribute("SameSite", "None"); // 💡 크로스 도메인(CORS) 환경에서 필수 (리액트 3000, 스프링 8080 일 때)

        // 3. 응답에 쿠키 추가
        response.addCookie(cookie);
//        memberService.loginMember(memberDto);

        return ResponseEntity.ok().build();
    }
}
