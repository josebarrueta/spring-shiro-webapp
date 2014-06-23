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
package com.stormpath.sample.web.controllers;

import com.stormpath.sample.api.service.AuthenticationService;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.api.ApiAuthenticationResult;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.AuthenticationResultVisitorAdapter;
import com.stormpath.sdk.group.Group;
import com.stormpath.sdk.group.GroupList;
import com.stormpath.sdk.group.Groups;
import com.stormpath.sdk.idsite.IdSiteUrlBuilder;
import com.stormpath.sdk.lang.Strings;
import com.stormpath.sdk.oauth.AccessTokenResult;
import com.stormpath.sdk.oauth.OauthAuthenticationResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Controller that supports the authentication URLs for the application.
 *
 * @author josebarrueta
 */
@Controller
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @Autowired
    private Application cloudApplication;

    private Group userGroup;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        Assert.notNull(authenticationService, "authenticationService cannot be null.");
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    public void initGroup() {
        GroupList groups = cloudApplication.getGroups(Groups.where(Groups.name().eqIgnoreCase("user")));
        for (Iterator<Group> groupIterator = groups.iterator(); groupIterator.hasNext(); ) {
            userGroup = groupIterator.next();
            break;
        }
        Assert.notNull(userGroup, "couldn't find user group.");
    }


    @RequestMapping(value = "/authorization/google", method = RequestMethod.GET)
    public ModelAndView authorizeGoogle(@RequestParam(value = "code", required = true) String code) {
        logger.info("The code sent by google is {}", code);
        Subject currentSubject = SecurityUtils.getSubject();
        if (currentSubject.isAuthenticated() || currentSubject.isRemembered()) {
            currentSubject.logout();
        }

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin() {

        Subject currentSubject = SecurityUtils.getSubject();
        if (currentSubject.isAuthenticated() || currentSubject.isRemembered()) {
            currentSubject.logout();
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/sso/redirect", method = RequestMethod.GET)
    public void createSsoUrl(HttpServletResponse httpResponse, @RequestParam(value = "state", required = false) String state) {

        IdSiteUrlBuilder urlBuilder = cloudApplication.newIdSiteUrlBuilder().setCallbackUri("http://localhost:8088/sso/response");

        if (Strings.hasText(state)) {
            urlBuilder.setState(state);
        }

        httpResponse.setStatus(HttpServletResponse.SC_FOUND);

        httpResponse.setHeader("Location", urlBuilder.build());
    }

    @RequestMapping(value = "/sso/response", method = RequestMethod.GET)
    public ModelAndView handleSsoResponse(HttpServletRequest request) {

        authenticationService.resolveIdentity(request);

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) {

        ApiAuthenticationResult result = cloudApplication.authenticateApiRequest(request);

        final Account account = result.getAccount();

        logger.info("Account signed {}", account.getEmail());

        response.setStatus(HttpServletResponse.SC_OK);

        response.setContentType("application/json; charset=utf-8");

        final String[] bodyHolder = new String[1];

        result.accept(new AuthenticationResultVisitorAdapter() {
            @Override
            public void visit(AuthenticationResult result) {
                throw new UnsupportedOperationException("visit() method hasn't been implemented.");
            }

            @Override
            public void visit(ApiAuthenticationResult result) {
                bodyHolder[0] = "{href: " + account.getHref() + ", email: " + account.getEmail() + " }";
            }

            @Override
            public void visit(OauthAuthenticationResult result) {
                bodyHolder[0] = "{href: " + account.getHref() + ", email: " + account.getEmail() + " }";
            }

            @Override
            public void visit(AccessTokenResult result) {
                bodyHolder[0] = result.getTokenResponse().toJson();
            }
        });

        try (PrintWriter pw = response.getWriter()) {
            pw.print(bodyHolder[0]);
            pw.flush();
        } catch (IOException e) {
            logger.error("Failed to write response. Problem {}", e.getMessage());
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam(value = "rememberMe", required = false, defaultValue = "false") String rememberMe) {
        try {
            authenticationService.authenticate(username, password, "on".equals(rememberMe));
        } catch (AuthenticationException e) {
            logger.error(String.format("Error occurred while authenticating user. Description [%s].", e.getCause().getMessage()));
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout() {
        Subject currentSubject = SecurityUtils.getSubject();

        if (currentSubject.isAuthenticated() || currentSubject.isRemembered()) {
            logger.info(String.format("User [%s] is logging out from the app.", currentSubject.getPrincipal()));
            currentSubject.logout();
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
    public ModelAndView unauthorized() {
        return new ModelAndView("error403");
    }
}
