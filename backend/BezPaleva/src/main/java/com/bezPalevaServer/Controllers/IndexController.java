package com.bezPalevaServer.Controllers;

import com.bezPalevaServer.db.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
