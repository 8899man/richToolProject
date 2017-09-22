package com.tsp.financial.controller;

import com.tsp.financial.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    User user = new User();

    @RequestMapping(value = "/home")
    public String home(Model model) {
        model.addAttribute("user",user);
        return "aa";
    }

    //提交表单后进行数据读取，并将数据传出
    @RequestMapping(value = "/bb",method = RequestMethod.POST)
    public String bb(User user,Model model) {
        model.addAttribute("user", user);
        model.addAttribute("message", ",welcome");
        return "bb";
    }
}
