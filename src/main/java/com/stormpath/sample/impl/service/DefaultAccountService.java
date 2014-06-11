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
package com.stormpath.sample.impl.service;

import com.stormpath.sample.api.service.AccountService;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class DefaultAccountService provides an implementation of the {code}AccountService{code}
 * which communicates with Stormpath APIs to perform account operations.
 *
 * @author josebarrueta
 * @since 1.0
 */
@Service
public class DefaultAccountService implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAccountService.class);

    private final Client stormpathClient;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Account getAuthenticatedAccount() {
        return applicationContext.getBean("authenticatedAccountRetriever", Account.class);
    }

    public Application getCloudApplication() {
        return applicationContext.getBean("cloudApplication", Application.class);
    }

    @Autowired
    public DefaultAccountService(Client stormpathClient) {
        Assert.notNull(stormpathClient, "stormpathClient is required.");
        this.stormpathClient = stormpathClient;
    }

    @Override
    public Account createAccount(Account account) {

        Application application = getCloudApplication();

        if (logger.isDebugEnabled()) {
            logger.debug("Creating application {}", application.getHref());
        }

        account = application.createAccount(account);

        if (logger.isInfoEnabled()) {
            logger.info("Account created {}.", account.getHref());
        }

        return account;
    }

    @Override
    public List<Account> retrieveAccounts() {

        AccountList accountList = getCloudApplication().getAccounts();

        List<Account> accountsToRetrieve;

        if (accountList != null) {
            accountsToRetrieve = new ArrayList<>();
            for (Account acct : accountList) {
                Account dfAcct = stormpathClient.getDataStore().instantiate(Account.class);
                dfAcct.setEmail(acct.getEmail());
                dfAcct.setGivenName(acct.getGivenName());
                dfAcct.setUsername(acct.getUsername());
                dfAcct.setSurname(acct.getSurname());
                dfAcct.setStatus(dfAcct.getStatus());
                accountsToRetrieve.add(dfAcct);
            }
        } else {
            accountsToRetrieve = Collections.emptyList();
        }
        return accountsToRetrieve;
    }
}
