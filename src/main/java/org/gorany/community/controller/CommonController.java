package org.gorany.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.MemberDTO;
import org.gorany.community.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(value = "/index")
    public void index(){

        log.info("CommonController, index Page");

    }

    @GetMapping(value = "/signup")
    public void signup(){

        log.info("@CommonController, Sign-up Page");
    }
    @PostMapping(value = "/signup")
    public String signupPost(String account, String password, String name, RedirectAttributes rttr){

        log.info("CommonController, Sign-up Post : 회원가입 요청");
        log.info(account);
        log.info(password);
        log.info(name);

        int result = service.signup(account, password, name);

        if(result == 0){
            rttr.addFlashAttribute("msg", "이미 존재하는 ID입니다.");
            return "redirect:/signup";
        }
        return "redirect:/index";
    }

    @GetMapping(value = "/login")
    public void login(){

        log.info("@CommonController, login page");
    }

}
