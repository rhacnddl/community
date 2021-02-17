package org.gorany.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.BoardDTO;
import org.gorany.community.dto.PageRequestDTO;
import org.gorany.community.dto.PageResultDTO;
import org.gorany.community.entity.Board;
import org.gorany.community.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardController {

    private final BoardService service;

    @GetMapping(value = "/list")
    public ModelAndView getList(@ModelAttribute("requestDTO") PageRequestDTO requestDTO){

        ModelAndView mv = new ModelAndView("board/list");
        log.info("@BoardController, getList " + requestDTO);

        mv.addObject("list", service.getList(requestDTO));

        return mv;
    }

}
