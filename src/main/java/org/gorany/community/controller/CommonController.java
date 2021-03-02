package org.gorany.community.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class CommonController {

    @GetMapping(value = "/sample")
    public void sample(){

        log.info("Sample....");

    }
}
