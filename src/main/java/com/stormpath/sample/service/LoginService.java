package com.stormpath.sample.service;

/**
 * Created with IntelliJ IDEA.
 * User: jbarrueta
 * Date: 12/27/12
 * Time: 9:13 AM
 *
 */
public interface LoginService {

    /**
     * Service to provide login feature.
     *
     * @param username
     * @param password
     */
    void doLogin(String username, String password, boolean rememberMe) throws Exception;

}
