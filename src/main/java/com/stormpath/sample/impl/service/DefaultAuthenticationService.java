/*
 * Copyright (c) 2014. JLBR
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stormpath.sample.impl.service;

import com.stormpath.sample.api.service.AuthenticationService;
import com.stormpath.sample.security.HttpRequestAuthenticationToken;
import com.stormpath.sdk.lang.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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

        if (currentUser.isAuthenticated()) {
            if (logger.isInfoEnabled()) {
                logger.info("Logging out user {}", currentUser.getPrincipal().toString());
            }
            currentUser.logout();
        }

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

    @Override
    public void resolveIdentity(HttpServletRequest httpServletRequest) {
        Assert.notNull(httpServletRequest);

        Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.isAuthenticated()) {
            if (logger.isInfoEnabled()) {
                logger.info("Logging out user {}", currentUser.getPrincipal().toString());
            }
            currentUser.logout();
        }

        AuthenticationToken httpRequestAuthcToken = new HttpRequestAuthenticationToken(httpServletRequest);

        try {
            currentUser.login(httpRequestAuthcToken);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid SSO request.", e);
        }
    }
}
