package org.kim.controller;

import org.kim.entity.User;
import org.kim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String Login(){
        return "../admin/login";
    }

    @RequestMapping(value = "/dologin",method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, User user, Model model){
        if(userService.login(user.getUsername(),user.getPassword())){
            request.getSession().setAttribute("user",user);
            model.addAttribute("user",user);
            return "redirect:/Article/kim";
        }else {
            model.addAttribute("error","用户名或密码错误！");
            return "../admin/login";
        }
    }

    @RequestMapping(value = "/dologin",method = RequestMethod.GET)
    public String doLogin(HttpServletRequest request,Model model){
        if(request.getSession().getAttribute("user") == null){
            return "../admin/login";
        }
        return "redirect:/Article/kim";
    }
}
