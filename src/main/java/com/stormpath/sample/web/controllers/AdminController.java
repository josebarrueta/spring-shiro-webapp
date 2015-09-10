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
import com.stormpath.sample.model.DefaultAccount;
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

    private final String[] CONCAT_CHAR = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v"};

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    @RequiresRoles(value = "admin")
    public ModelAndView showCreateUser() {
        return new ModelAndView("createUser");
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @RequiresRoles(value = "admin")
    public ModelAndView createUser(@ModelAttribute("account") DefaultAccount account) {

        String username = account.getUsername();
        String givenName = account.getGivenName();
        String lastName = account.getGivenName();

        for (int i = 0; i < 1; i++) {

            StringBuilder email = new StringBuilder(username);
            email.append(CONCAT_CHAR[i]);
            email.append("@yopmail.com");

            account.setUsername(username + CONCAT_CHAR[i]);
            account.setEmail(email.toString());

            account.setGivenName(givenName + CONCAT_CHAR[i]);
            account.setSurname(lastName + CONCAT_CHAR[i]);

            accountService.createAccount(null);

        }
        return new ModelAndView("redirect:/accounts");
    }
}
