package com.stormpath.sample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: jbarrueta
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public ModelAndView createUser(){
        return new ModelAndView("createUser");
    }
}
