package com.bezPalevaServer.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class IndexController {

    @RequestMapping("/")
    public ModelAndView index ()  {

        ModelAndView indexView = new ModelAndView("index");


        return  indexView;
    }

    @RequestMapping("/log")
    public ModelAndView log (){

        ModelAndView log = new ModelAndView("log");

        return  log;
    }


}
