package com.stormpath.sample.controllers;

import com.stormpath.sample.model.DefaultAccount;
import com.stormpath.sample.service.AccountService;
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


    /**
     * URI for accessing the "home" page of the web application.
     *
     * Sample usages of isAuthenticated or isRemembered methods from {@link org.apache.shiro.subject.Subject}
     * can be seen here.
     *
     * Instead of forcing the authc filter on this resource, we handle the validation of the user in this method. Just
     * to show shiro tools for this.
     *
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getHome() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            logger.info(String.format("home - current user info [%s]", currentUser.getPrincipal().toString()));
            return new ModelAndView("home");
        } else {
            logger.error("Not authenticated user tried to access home page.");
            return new ModelAndView("redirect:/login");
        }

    }

    /**
     * URI for getting the list of accounts that exist on the authenticated user's directory.
     *
     * Important to see here is the usage for {@link org.apache.shiro.authz.annotation.RequiresRoles} and
     * {@link org.apache.shiro.authz.annotation.Logical} annotations of Apache Shiro, in this case accounts
     * the are set with either "admin" OR "user" can access the "/accounts" resource.
     *
     * @return
     */
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    @RequiresRoles(value = {"user","admin"}, logical = Logical.OR)
    public ModelAndView getAccounts() {
        List<DefaultAccount> accountsToRetrieve =  accountService.getAccounts();

        logger.info(String.format("Found [%d] accounts in the application.", accountsToRetrieve.size()));

        Map<String,Object> model = new HashMap<String, Object>();
        model.put("accountList", accountsToRetrieve);

        return new ModelAndView("accounts", model);
    }
}
