package org.gorany.community.controller;

import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.MemberDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class ChatController {

    @GetMapping("/chat")
    public void chatGET(){

        log.info("@ChatController, chat GET()");
    }
}
