package com.stormpath.sample.api.service;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author josebarrueta
 * @since 1.0
 */
public interface AuthenticationService {

    /**
     * log in action to validateToken a user.
     *
     * @param username
     * @param password
     */
    void authenticate(String username, String password, boolean rememberMe) throws AuthenticationException ;

}
