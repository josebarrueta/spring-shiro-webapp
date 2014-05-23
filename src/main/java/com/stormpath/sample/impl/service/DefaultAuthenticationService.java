package com.stormpath.sample.impl.service;

import com.stormpath.sample.api.service.AuthenticationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class DefaultAuthenticationService provides an implementation of {@link AuthenticationService} using
 * Apache Shiro.
 *
 * @author josebarrueta
 * @since 1/18/13
 */
@Component
public class DefaultAuthenticationService implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Override
    public void authenticate(String username, String password, boolean rememberMe) throws AuthenticationException {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                currentUser.login(token);
                token.setRememberMe(rememberMe);
            } catch (UnknownAccountException uae) {
                throw new AuthenticationException("UnknownAccountException occurred.", uae);
            } catch (IncorrectCredentialsException ice) {
                throw new AuthenticationException("IncorrectCredentialsException occurred.", ice);
            } catch (LockedAccountException lae) {
                //account for that username is locked - can't login.  Show them a message?
                throw new AuthenticationException("LockedAccountException occurred.", lae);
            }
        }
    }
}
