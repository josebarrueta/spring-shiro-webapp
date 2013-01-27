package com.stormpath.sample.controllers;

import com.stormpath.sample.service.AccountService;
import com.stormpath.sdk.account.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Main controller has the URLs accessible for regular users as well as admins.
 *
 * @author josebarrueta
 */
@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @RequiresRoles(value = {"user","admin"}, logical = Logical.OR)
    public ModelAndView getHome() {
        Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.isAuthenticated()) {
            logger.info(String.format("Current user info [%s]", currentUser.getPrincipal().toString()));
            return new ModelAndView("home");
        } else {
            logger.error("Not authenticated user tried to access home page.");
            return new ModelAndView("redirect:/login");
        }

    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    @RequiresRoles(value = {"user","admin"}, logical = Logical.OR)
    public ModelAndView getAccounts() {
        List<Account> accountsToRetrieve =  accountService.getAccounts();
        logger.info(String.format("Found [%d] accounts in the application.", accountsToRetrieve.size()));

        Map<String,Object> model = new HashMap<String, Object>();
        model.put("accountList", accountsToRetrieve);

        return new ModelAndView("accounts", model);
    }
}
