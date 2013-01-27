package com.stormpath.sample.impl;

import com.stormpath.sample.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Class DefaultLoginService provides an implementation of LoginService using
 * Apache Shiro.
 *
 * @author josebarrueta
 * @since 1/18/13
 *
 */
@Service
public class DefaultLoginService implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLoginService.class);

    @Override
    public void doLogin(String username, String password, boolean rememberMe) throws Exception{
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                currentUser.login(token);
                token.setRememberMe(rememberMe);

                logger.info("Current user: " + currentUser.getPrincipal());
            } catch ( UnknownAccountException uae ) {
                throw new AuthenticationException("UnknownAccountException occurred.", uae);
            } catch ( IncorrectCredentialsException ice ) {
                throw new AuthenticationException("IncorrectCredentialsException occurred.", ice);
            } catch ( LockedAccountException lae ) {
                //account for that username is locked - can't login.  Show them a message?
                throw new AuthenticationException("LockedAccountException occurred.", lae);
            }
        }
    }
}
