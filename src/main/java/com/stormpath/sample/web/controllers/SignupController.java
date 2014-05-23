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
