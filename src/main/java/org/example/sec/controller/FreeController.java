package org.example.sec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/free")
public class FreeController {
    @GetMapping("/1")
    @ResponseBody
    public String free1() {
        return "free1";
    }
}
