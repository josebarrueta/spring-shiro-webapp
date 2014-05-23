package com.stormpath.sample.web.controllers;

import com.stormpath.sample.api.service.AccountService;
import com.stormpath.sample.model.DefaultAccount;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller has the URLs that are accessible only for admins.
 *
 * @author josebarrueta
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final String[] CONCAT_CHAR = {"a", "b", "c", "d", "e", "f","g", "h", "i", "j","k", "l", "m","n","o","p","q","r","s","t","u","v"};

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    @RequiresRoles(value = "admin")
    public ModelAndView showCreateUser(){
        return new ModelAndView("createUser");
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @RequiresRoles(value = "admin")
    public ModelAndView createUser(@ModelAttribute("account") DefaultAccount account){

        String username = account.getUsername();
        String givenName = account.getGivenName();
        String lastName = account.getGivenName();

        for(int i = 0; i < 1; i++){

            StringBuilder email = new StringBuilder(username);
            email.append(CONCAT_CHAR[i]);
            email.append("@yopmail.com");

            account.setUsername(username +  CONCAT_CHAR[i]);
            account.setEmail(email.toString());

            account.setGivenName(givenName + CONCAT_CHAR[i]);
            account.setSurname(lastName + CONCAT_CHAR[i]);

            accountService.createAccount(null);

        }
        return new ModelAndView("redirect:/accounts");
    }

    @RequestMapping(value = ".", method = RequestMethod.POST)
    public ModelAndView createApplication(@RequestParam String applicationName){
       return null;

    }
}
