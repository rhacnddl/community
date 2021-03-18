package org.gorany.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.dto.MemberDTO;
import org.gorany.community.dto.ProfileDTO;
import org.gorany.community.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    @Value("${org.gorany.upload.path}")
    private String uploadPath;

    private final MemberService memberService;

    @GetMapping(value = "/modify")
    public ModelAndView modifyGet(String account){

        ModelAndView mv = new ModelAndView("member/modify");

        log.info("@MemberController, modify GET");
        MemberDTO memberDTO = memberService.get(account);

        log.info(memberDTO);

        mv.addObject("dto", memberDTO);
        return mv;
    }
    @PostMapping(value = "/modify")
    public String modifyPost(String account, String name, String profile, MultipartFile multipartFile, RedirectAttributes rttr){

        ProfileDTO profileDTO = null;

        if(!multipartFile.isEmpty() && multipartFile.getSize() > 0){
            log.info(multipartFile.getName());

            String datePath = getDatePath();
            String filename = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String fullName = uploadPath + File.separator + datePath + File.separator + uuid + "_" + filename;

            profileDTO = new ProfileDTO(uuid, multipartFile.getOriginalFilename(), datePath, account);

            Path path = Paths.get(fullName);

            try {
                FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(path.toFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        memberService.modify(account, name, profile, profileDTO);

        return "redirect:/index";
    }

    public String getDatePath(){

        String path = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy\\MM\\dd"));
        File filePath = new File(uploadPath + File.separator + path);

        if(!filePath.exists())
            filePath.mkdirs();

        return path;
    }
}