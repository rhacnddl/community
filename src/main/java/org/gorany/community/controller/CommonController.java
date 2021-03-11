package org.gorany.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.MemberDTO;
import org.gorany.community.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class CommonController {

    private final MemberService service;

    @GetMapping(value = "/sample")
    public void sample(@AuthenticationPrincipal MemberDTO memberDTO, Model model){

        log.info("Sample....");
        log.info(memberDTO);

        model.addAttribute("member", memberDTO);
    }

    @GetMapping(value = "/signup")
    public void signup(){

        log.info("@CommonController, Sign-up Page");
    }
    @PostMapping(value = "/signup")
    public String signupPost(String account, String password, String name){

        log.info("CommonController, Sign-up Post : 회원가입 요청");
        log.info(account);
        log.info(password);
        log.info(name);

        service.signup(account, password, name);

        return "redirect:/";
    }
}
