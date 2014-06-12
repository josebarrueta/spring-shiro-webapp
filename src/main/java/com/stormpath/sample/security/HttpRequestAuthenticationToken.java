package com.stormpath.sample.security;

import com.stormpath.sdk.lang.Assert;
import org.apache.shiro.authc.AuthenticationToken;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpRequestAuthenticationToken
 *
 * @since 0.49
 */
public class HttpRequestAuthenticationToken implements AuthenticationToken {

    private final HttpServletRequest httpServletRequest;

    public HttpRequestAuthenticationToken(HttpServletRequest httpServletRequest) {
        Assert.notNull(httpServletRequest);
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public Object getPrincipal() {
        return getHttpServletRequest();
    }

    @Override
    public Object getCredentials() {
        return getHttpServletRequest();
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }
}
