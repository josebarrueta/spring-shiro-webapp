package com.stormpath.sample.impl;

import com.stormpath.sample.service.AccountService;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.client.Client;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class DefaultAccountService provides an implementation of the {code}AccountService{code}
 * which communicates with Stormpath APIs to perform account operations.
 *
 * @author josebarrueta
 * @since 1/25/13
 */
@Service
public class DefaultAccountService implements AccountService{

    @Autowired
    private Client stormpathClient;

    @Override
    public Account createAccount() {
        return null;
    }

    @Override
    public List<Account> getAccounts() {
        Subject currentUser = SecurityUtils.getSubject();

        String accountHref = currentUser.getPrincipal().toString();

        Account account = stormpathClient.getDataStore().getResource(accountHref, Account.class);

        AccountList accountList = account.getDirectory().getAccounts();

        List<Account> accountsToRetrieve ;

        if(accountList != null){
            accountsToRetrieve = new ArrayList<Account>();
            for(Account acct : accountList){
                accountsToRetrieve.add(acct);
            }
        }
        else {
            accountsToRetrieve = Collections.emptyList();
        }
        return accountsToRetrieve;
    }
}
