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
import com.stormpath.sample.impl.converters.MapToAccountConverter;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * SignupController handles user's registration to this webapp.
 *
 * @since 1.0.1
 */
@Controller
public class SignupController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AccountService accountService;
    private final MapToAccountConverter mapToAccountConverter;

    @Autowired
    public SignupController(AccountService accountService, MapToAccountConverter mapToAccountConverter) {
        Assert.notNull(accountService, "accountService cannot be null.");
        Assert.notNull(mapToAccountConverter, "mapToAccountConverter cannot be null.");
        this.accountService = accountService;
        this.mapToAccountConverter = mapToAccountConverter;

    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public String signup(@RequestBody Map accountData) {

        Account account = mapToAccountConverter.convert(accountData);

        accountService.createAccount(account);

        return "works";
    }
}
