package org.gorany.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.BoardDTO;
import org.gorany.community.dto.MemberDTO;
import org.gorany.community.dto.PageRequestDTO;
import org.gorany.community.dto.PageResultDTO;
import org.gorany.community.entity.Board;
import org.gorany.community.service.BoardService;
import org.gorany.community.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping(value = "/member/manage")
    public ModelAndView getList(){

        ModelAndView mv = new ModelAndView("admin/member/manage");
        mv.addObject("list", memberService.getList());

        log.info("@AdminController, Member -> getList");

        return mv;
    }
    @PostMapping(value = "/member/manage")
    @ResponseBody
    public ResponseEntity<String> changeRole(String account, int role){

        log.info("@AdminController, changeRole " + account + " " + role);
        memberService.changeRole(account, role);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /* ----------------------------------------------------------------- */

    @GetMapping(value = "/board/list")
    public void getList(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        log.info("@AdminController, Board -> getList");
        model.addAttribute("list", boardService.getList(requestDTO));
    }
}
