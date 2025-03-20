package com.example.pl_lab_server.Default;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/apply/index")
    public String apply(){
        return "apply";
    }

    @GetMapping("/apply/suc")
    public String applySuc(){
        return "applySuc";
    }

    @GetMapping("/admin/apply/history")
    public String adminApply(){
        return "adminApply";
    }

}
