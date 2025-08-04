package com.learn.SidClasses.Controller;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@EnableWebSecurity(debug = true)
public class PageController {
    @RequestMapping("/login")
    public String loginpage(){
        return "login";
    }
    @RequestMapping("/success")
    public String successpage(){
        return "success";
    }
}
