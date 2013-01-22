package com.stormpath.sample.impl;

import com.stormpath.sample.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;

/**
 * Created with IntelliJ IDEA.
 * User: jbarrueta
 * Date: 1/18/13
 * Time: 8:04 PM
 */
public class LoginServiceImpl implements LoginService {

    @Override
    public void doLogin(String username, String password, boolean rememberMe) throws Exception{
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                currentUser.login(token);
                token.setRememberMe(rememberMe);
            } catch ( UnknownAccountException uae ) {
                throw new Exception("UnknownAccountException occurred.", uae);
            } catch ( IncorrectCredentialsException ice ) {
                throw new Exception("IncorrectCredentialsException occurred.", ice);
            } catch ( LockedAccountException lae ) {
                //account for that username is locked - can't login.  Show them a message?
                throw new Exception("LockedAccountException occurred.", lae);
            } catch ( AuthenticationException ae ) {
                //unexpected condition - error?
                throw new Exception("AuthenticationException occurred.", ae);
            }
        }
    }
}
