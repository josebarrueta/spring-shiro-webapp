package com.stormpath.sample.impl;

import com.stormpath.sample.model.DefaultAccount;
import com.stormpath.sample.service.AccountService;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.Directory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createAccount(DefaultAccount newAccount) {

        Subject currentUser = SecurityUtils.getSubject();
        String accountHref = currentUser.getPrincipal().toString();

        Account account = stormpathClient.getDataStore().getResource(accountHref,Account.class);

        Directory directory = account.getDirectory();

        Account sdkAccount = stormpathClient.getDataStore().instantiate(Account.class);
        sdkAccount.setEmail(newAccount.getEmail());
        sdkAccount.setUsername(newAccount.getUsername());
        sdkAccount.setGivenName(newAccount.getGivenName());
        sdkAccount.setPassword(newAccount.getPassword());
        sdkAccount.setSurname(newAccount.getSurname());

        directory.createAccount(sdkAccount);
    }

    @Override
    public List<DefaultAccount> getAccounts() {
        Subject currentUser = SecurityUtils.getSubject();

        String accountHref = currentUser.getPrincipal().toString();

        Account sdkAccount = stormpathClient.getDataStore().getResource(accountHref,Account.class);
        AccountList accountList = sdkAccount.getDirectory().getAccounts();

        List<DefaultAccount> accountsToRetrieve ;

        if(accountList != null){
            accountsToRetrieve = new ArrayList<DefaultAccount>();
            for(Account acct : accountList){
                DefaultAccount dfAcct = new DefaultAccount();
                dfAcct.setEmail(acct.getEmail());
                dfAcct.setGivenName(acct.getGivenName());
                dfAcct.setUsername(acct.getUsername());
                dfAcct.setSurname(acct.getSurname());
                dfAcct.setStatus(acct.getStatus().name().toLowerCase());
                accountsToRetrieve.add(dfAcct);
            }
        }
        else {
            accountsToRetrieve = Collections.emptyList();
        }
        return accountsToRetrieve;
    }
}
