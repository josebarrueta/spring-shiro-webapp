package com.stormpath.sample.controllers;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.client.Client;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Main controller has the URLs accessible for regular users and admins.
 *
 * @author josebarrueta
 */
@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);


    @Autowired
    private Client stormpathClient;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @RequiresPermissions(value = "admin,user", logical = Logical.OR)
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
    @RequiresPermissions(value = "admin,user", logical = Logical.OR)
    public ModelAndView getAccounts() {
        Subject currentUser = SecurityUtils.getSubject();

        String accountHref = currentUser.getPrincipal().toString();

        Account account = stormpathClient.getDataStore().getResource(accountHref, Account.class);

        AccountList accountList = account.getDirectory().getAccounts();

        List<Account> accountsToRetrieve = new ArrayList<Account>();
        for(Account acct : accountList){
            accountsToRetrieve.add(acct);
        }

        logger.info(String.format("Found [%d] accounts in the application.", accountsToRetrieve.size()));

        Map<String,Object> model = new HashMap<String, Object>();
        model.put("accountList", accountsToRetrieve);

        return new ModelAndView("accounts", model);
    }
}
