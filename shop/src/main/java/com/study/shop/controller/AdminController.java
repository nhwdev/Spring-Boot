package com.study.shop.controller;

import com.study.shop.dto.Mail;
import com.study.shop.dto.User;
import com.study.shop.exception.ShopException;
import com.study.shop.service.UserService;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/*
 * AdminController의 모든 메서드는 관리자로 로그인된 경우만 실행
 * → AOP 설정 필요 (AdminLoginAspect 클래스)
 */
@Controller                  // 이 클래스가 Spring MVC의 컨트롤러임을 선언 (요청을 받아 처리하는 역할)
@RequestMapping("admin")     // 이 컨트롤러의 모든 요청 URL 앞에 "admin/"이 붙음 (예: admin/list, admin/mail)
public class AdminController {

    @Autowired               // Spring이 UserService 구현체를 자동으로 찾아서 주입해줌 (직접 new 안 해도 됨)
    private UserService service;
    private final JavaMailSender mailSender;
    @Value("${my.mail.sender}")
    private String senderEmail;

    public AdminController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RequestMapping("list")  // GET/POST 구분 없이 "admin/list" 요청을 이 메서드가 처리
    public ModelAndView list(HttpSession session) {
        ModelAndView mav = new ModelAndView(); // 뷰 이름과 데이터를 함께 담는 객체 생성
        // list : 모든 useraccount 테이블의 정보
        List<User> list = service.listUser();  // DB에서 전체 회원 목록 조회
        mav.addObject("list", list);           // 뷰(JSP)에서 "list"라는 이름으로 데이터를 꺼내 쓸 수 있게 저장
        return mav;                            // 뷰 이름은 자동으로 "admin/list"로 매핑됨 (RequestMapping 경로 기반)
    }


    @PostMapping("mailform")
    public String mailform(String[] idchks, Model model) {
        if (idchks == null || idchks.length == 0) {
            throw new ShopException("메일을 보낼 회원을 선택하세요.", "list");
        }

        List<User> list = service.listUser(idchks);
        Mail mail = new Mail();

        // Java 8+ Stream을 쓰면 훨씬 간결하게 수신자 목록을 만들 수 있습니다.
        String recipient = list.stream()
                .map(u -> u.getUsername() + "<" + u.getEmail() + ">")
                .collect(Collectors.joining(","));

        mail.setRecipient(recipient);

        // 더 이상 소스코드에 ID/PW를 직접 세팅하지 않습니다.
        // 발신자 표시용 이메일만 설정에 적힌 값을 사용합니다.
        mail.setGoogleid(senderEmail);

        model.addAttribute("mail", mail);
        return "admin/mail";
    }

    @PostMapping("mail")
    public String mail(@Valid Mail mail, BindingResult bindingResult, Model model) {
        System.out.println("메일 발송 요청 들어옴!");
        if (bindingResult.hasErrors()) return "admin/mail";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            // true: 멀티파트 메시지(파일 첨부) 사용, "UTF-8": 인코딩 설정
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail); // @Value로 가져온 값 사용
            helper.setTo(mail.getRecipient().split(","));
            helper.setSubject(mail.getTitle());
            helper.setText(mail.getContents(), true); // true면 HTML 형식 전송

            // 첨부파일 처리 (파일 저장 없이 바로 첨부)
            if (mail.getFile1() != null) {
                for (MultipartFile file : mail.getFile1()) {
                    if (!file.isEmpty()) {
                        // 파일 객체를 만들지 않고 InputStreamSource를 사용해 바로 첨부
                        helper.addAttachment(file.getOriginalFilename(), file);
                    }
                }
            }

            mailSender.send(message);
            model.addAttribute("message", "메일 전송 성공!");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "메일 전송 실패: " + e.getMessage());
        }

        model.addAttribute("url", "list");
        return "alert";
    }
}