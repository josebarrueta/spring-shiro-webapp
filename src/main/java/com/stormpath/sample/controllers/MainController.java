package com.stormpath.sample.controllers;

import com.stormpath.sample.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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
        Subject currentUser = SecurityUtils.getSubject();

        if(currentUser.isAuthenticated()){
            logger.info("Entered to home page. User is authenticated.");
            Map<String,Object> model = new HashMap<String, Object>();

            model.put("isAdmin", currentUser.hasRole("admin")) ;
            model.put("isUser", currentUser.hasRole("user"));
            return new ModelAndView("home", model);
        }
        else{
            logger.error("Not authenticated user tried to access home page.");
            return new ModelAndView("redirect:/login");
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam(value = "rememberMe", required = false, defaultValue = "false") String rememberMe){
        try {
            loginService.doLogin(username,password,Boolean.valueOf(rememberMe));
        } catch (Exception e) {
            logger.error(String.format("Error occurred while authenticating user. Description [%s].", e.getCause().getMessage()));
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("redirect:/home");
    }


}
