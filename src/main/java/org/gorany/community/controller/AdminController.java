package org.gorany.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.BoardDTO;
import org.gorany.community.dto.PageRequestDTO;
import org.gorany.community.dto.PageResultDTO;
import org.gorany.community.entity.Board;
import org.gorany.community.service.BoardService;
import org.gorany.community.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping(value = "/board/list")
    public void getList(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        log.info("@AdminController, Board -> getList");
        model.addAttribute("list", boardService.getList(requestDTO));
    }
}
