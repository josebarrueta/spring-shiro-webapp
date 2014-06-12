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
package com.stormpath.sample.security;

import com.google.common.collect.ImmutableSet;
import com.stormpath.sdk.account.AccountResult;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.error.jwt.InvalidJwtException;
import com.stormpath.sdk.resource.ResourceException;
import com.stormpath.shiro.realm.ApplicationRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.StringUtils;

import java.util.Set;

/**
 * @since 1.0.1
 */
public class SampleApplicationRealm extends ApplicationRealm {

    private static final Set<Class<? extends AuthenticationToken>> SUPPORTED_AUTHENTICATION_TOKENS;

    static {
        SUPPORTED_AUTHENTICATION_TOKENS = new ImmutableSet.Builder<Class<? extends AuthenticationToken>>()
                .add(HttpRequestAuthenticationToken.class)
                .add(UsernamePasswordToken.class)
                .build();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token == null) {
            return false;
        }

        for (Class<? extends AuthenticationToken> authenticationTokenClass : SUPPORTED_AUTHENTICATION_TOKENS) {
            if (authenticationTokenClass.isAssignableFrom(token.getClass())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        if (authcToken instanceof UsernamePasswordToken) {
            return super.doGetAuthenticationInfo(authcToken);
        }

        HttpRequestAuthenticationToken token = (HttpRequestAuthenticationToken) authcToken;

        Application application = ensureApplicationReference();

        AccountResult accountResult;

        try {
            accountResult = application.handleSsoResponse(token.getHttpServletRequest()).resolve();
        } catch (ResourceException | InvalidJwtException | IllegalArgumentException e) {
            String msg = StringUtils.clean(e.getMessage());
            if (msg == null) {
                msg = "Invalid SSO Request";
            }
            throw new AuthenticationException(msg, e);
        }

        PrincipalCollection principals;

        try {
            principals = createPrincipals(accountResult.getAccount());
        } catch (Exception e) {
            throw new AuthenticationException("Unable to obtain authenticated account properties.", e);
        }
        return new SimpleAuthenticationInfo(principals, null);
    }
}
