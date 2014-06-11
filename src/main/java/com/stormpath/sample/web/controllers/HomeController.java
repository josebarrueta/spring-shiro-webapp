/*
 * Copyright (c) 2014. JLBR
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stormpath.sample.web.controllers;

import com.stormpath.sample.api.service.AccountService;
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
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private AccountService accountService;


    /**
     * URI for accessing the "home" page of the web application.
     * <p/>
     * Sample usages of isAuthenticated or isRemembered methods from {@link org.apache.shiro.subject.Subject}
     * can be seen here.
     * <p/>
     * Instead of forcing the authc filter on this resource, we handle the validation of the user in this method. Just
     * to show shiro tools for this.
     *
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @RequiresRoles(value = {"user"})
    public ModelAndView getHome() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            logger.info("home - current user info {}", currentUser.getPrincipal().toString());

            Account account = accountService.getAuthenticatedAccount();

            Map<String, Object> model = new HashMap<>();
            model.put("account", account);
            return new ModelAndView("home", model);
        } else {
            logger.error("Not authenticated user tried to access home page.");
            return new ModelAndView("redirect:/login");
        }
    }

    /**
     * URI for getting the list of accounts that exist on the authenticated user's directory.
     * <p/>
     * Important to see here is the usage for {@link org.apache.shiro.authz.annotation.RequiresRoles} and
     * {@link org.apache.shiro.authz.annotation.Logical} annotations of Apache Shiro, in this case accounts
     * the are set with either "admin" OR "user" can access the "/accounts" resource.
     *
     * @return
     */
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    @RequiresRoles(value = {"user", "admin"}, logical = Logical.OR)
    public ModelAndView getAccounts() {
        List<Account> accountsToRetrieve = accountService.retrieveAccounts();

        logger.info(String.format("Found [%d] accounts in the application.", accountsToRetrieve.size()));

//        Stream<Account> accountStream = accountsToRetrieve.parallelStream().sorted();
//
        Map<String, Object> model = new HashMap<>();
        model.put("accountList", accountsToRetrieve);

        return new ModelAndView("accounts", model);
    }
}
