package com.stormpath.sample.controllers;

import com.stormpath.sample.service.AccountService;
import com.stormpath.sdk.account.Account;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public ModelAndView createUser(){
        Account account = accountService.createAccount();
        return new ModelAndView("redirect:/accounts");
    }
}
