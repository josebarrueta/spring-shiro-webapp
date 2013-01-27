package com.stormpath.sample.controllers;

import com.stormpath.sample.model.DefaultAccount;
import com.stormpath.sample.service.AccountService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller has the URLs that are accessible only for admins.
 *
 * @author josebarrueta
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    @RequiresRoles(value = "admin")
    public ModelAndView showCreateUser(){
        return new ModelAndView("createUser");
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ModelAndView createUser(@ModelAttribute("account") DefaultAccount account){

        accountService.createAccount(account);
        return new ModelAndView("redirect:/accounts");
    }
}
