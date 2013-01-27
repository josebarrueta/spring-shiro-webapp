package com.stormpath.sample.service;

import com.stormpath.sdk.account.Account;

import java.util.List;

/**
 * Interface AccountService is used for...
 *
 * @author josebarrueta
 * @since 1/25/13
 */
public interface AccountService {

    Account createAccount();

    List<Account> getAccounts();
}
