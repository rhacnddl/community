package org.gorany.community.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class UploadTestController {

    @GetMapping(value = "/uploadEx")
    public void uploadEx(){

    }
}
