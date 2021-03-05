package org.gorany.community.controller;

import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.MemberDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class CommonController {

    @GetMapping(value = "/sample")
    public void sample(@AuthenticationPrincipal MemberDTO memberDTO, Model model){

        log.info("Sample....");
        log.info(memberDTO);

        model.addAttribute("member", memberDTO);
    }
}
