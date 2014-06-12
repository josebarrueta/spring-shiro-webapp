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
public class MultiTokenApplicationRealm extends ApplicationRealm {

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
        } catch (ResourceException | InvalidJwtException e) {
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
