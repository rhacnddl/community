package org.gorany.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.BoardDTO;
import org.gorany.community.dto.MemberDTO;
import org.gorany.community.dto.PageRequestDTO;
import org.gorany.community.dto.PageResultDTO;
import org.gorany.community.entity.Board;
import org.gorany.community.service.BoardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/board")
@PreAuthorize("isAuthenticated()")
public class BoardController {

    private final BoardService service;

    @GetMapping(value = "/list")
    public ModelAndView getList(@ModelAttribute("requestDTO") PageRequestDTO requestDTO){

        ModelAndView mv = new ModelAndView("board/list");
        log.info("@BoardController, getList " + requestDTO);

        PageResultDTO<BoardDTO, Object[]> result = service.getList(requestDTO);
        log.info(result);
        mv.addObject("list", result);

        return mv;
    }

    @GetMapping(value = "/register")
    public ModelAndView register(){

        ModelAndView mv = new ModelAndView("board/register");
        log.info("@BoardController, register");

        return mv;
    }
    @PostMapping(value = "/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes rttr){

        log.info("@BoardController, " + boardDTO);

        int bno = service.register(boardDTO);
        rttr.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @GetMapping(value = {"/read", "/modify"})
    public void get(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, int bno, Model model){

        log.info("@BoardController, Read/Modify " + requestDTO + " BNO : " + bno);

        BoardDTO boardDTO = service.read(bno);
        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }
    @PostMapping(value = "/modify")
    @PreAuthorize("#member != null && #member.username eq #boardDTO.writer")
    public String modify(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, BoardDTO boardDTO, RedirectAttributes rttr,
                         @AuthenticationPrincipal MemberDTO member){

        log.info("@BoardController, Modify Post : " + boardDTO);

        service.modify(boardDTO);

        rttr.addAttribute("bno", boardDTO.getBno());
        rttr.addAttribute("page", requestDTO.getPage());
        rttr.addAttribute("size", requestDTO.getSize());
        rttr.addAttribute("keyword", requestDTO.getKeyword());
        rttr.addAttribute("type", requestDTO.getType());

        return "redirect:/board/read";
    }
    @PostMapping(value = "/remove")
    @PreAuthorize("#member != null && #member.username eq #boardDTO.writer")
    public String remove(int bno, RedirectAttributes rttr,
                         @AuthenticationPrincipal MemberDTO member){

        log.info("@BoardController, remove : " + bno);

        service.remove(bno);
        rttr.addFlashAttribute("bno", bno);

        return "redirect:/board/list";
    }
}