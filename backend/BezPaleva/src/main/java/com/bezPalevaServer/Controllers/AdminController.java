package com.bezPalevaServer.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public ModelAndView adminView ()  {

        ModelAndView indexView = new ModelAndView("admin");

        return  indexView;
    }
}
