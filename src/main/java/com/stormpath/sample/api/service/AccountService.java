package com.stormpath.sample.api.service;

import com.stormpath.sdk.account.Account;

import java.util.List;

/**
 * AccountService is the interface used to get and create accounts.
 *
 * @author josebarrueta
 * @since 1.0.1
 */
public interface AccountService {

    Account getAccount();

    List<Account> retrieveAccounts();

    void createAccount(Account account);

}
