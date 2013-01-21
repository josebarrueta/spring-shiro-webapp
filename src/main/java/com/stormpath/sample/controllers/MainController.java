package com.stormpath.sample.controllers;

import com.stormpath.sample.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * Main controller is the
 *
 * User: jbarrueta
 * Date: 1/20/13
 * Time: 3:43 PM
 *
 */

@Controller
public class MainController {

    @Autowired
    private LoginService loginService;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(){
        logger.info("Showing log in form.");
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView showHome(){
        logger.info("Entered to home page. User is authenticated.");
        return  new ModelAndView("home");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("rememberMe") String rememberMe){
        try {
            loginService.doLogin(username,password,Boolean.valueOf(rememberMe));
        } catch (Exception e) {
            logger.error(String.format("Error occurred while authenticating user. Description [%s].", e.getMessage()));
        }
        return new ModelAndView("redirect:/home");
    }


}
