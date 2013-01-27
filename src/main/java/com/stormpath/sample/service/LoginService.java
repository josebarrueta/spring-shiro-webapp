package com.stormpath.sample.service;

/**
 *
 * @author josebarrueta
 * @since 1/18/2013
 *
 */
public interface LoginService {

    /**
     * log in action to authenticate a user.
     *
     * @param username
     * @param password
     */
    void doLogin(String username, String password, boolean rememberMe) throws Exception;

}
