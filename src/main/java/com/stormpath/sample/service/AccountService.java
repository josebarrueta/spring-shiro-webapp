package com.stormpath.sample.service;

import com.stormpath.sample.model.DefaultAccount;

import java.util.List;
import java.util.Map;

/**
 * AccountService is the interface used to get and create accounts.
 *
 * @author josebarrueta
 * @since 1/25/13
 */
public interface AccountService {

    void createAccount(DefaultAccount account);

    List<DefaultAccount> getAccounts();
}
