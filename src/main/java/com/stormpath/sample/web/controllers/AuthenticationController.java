package com.stormpath.sample.web.controllers;

import com.stormpath.sample.api.service.AuthenticationService;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.ApiAuthenticationResult;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.AuthenticationResultVisitor;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.oauth.authc.BasicOauthAuthenticationResult;
import com.stormpath.sdk.oauth.authc.OauthAuthenticationResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

/**
 * Controller that supports the authentication URLs for the application.
 *
 * @author josebarrueta
 */
@Controller
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @Value("${stormpath.application.restUrl}")
    private String applicationRestUrl;

    @Autowired
    private Client client;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        Assert.notNull(authenticationService, "authenticationService cannot be null.");
        this.authenticationService = authenticationService;
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
    public ModelAndView showLogin(HttpServletRequest request) {


        Map<String, String[]> parameters = request.getParameterMap();

        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            logger.info("Parameter name: {}", entry.getKey());
            logger.info("\t value: {}", entry.getValue());
        }

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            logger.info("Header name: {}", name);

            Enumeration<String> headerValues = request.getHeaders(name);
            while (headerValues.hasMoreElements()) {
                logger.info("\tvalue: {}", headerValues.nextElement());
            }

        }
        logger.info("method {}", request.getMethod());

        Enumeration<String> attributeNames = request.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            logger.info("Attribute: {}.", name);
        }

        Subject currentSubject = SecurityUtils.getSubject();
        if (currentSubject.isAuthenticated() || currentSubject.isRemembered()) {
            currentSubject.logout();
        }
        return new ModelAndView("login");
    }


    @RequestMapping(value = "/api/bearer", method = RequestMethod.POST)
    public void loginWithBearer(HttpServletRequest request, HttpServletResponse response) {


    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) {

        Application application = client.getResource(applicationRestUrl, Application.class);

//        application.authenticate(request).execute();

        AuthenticationResult result = application.authenticate(request).execute();

        Assert.isInstanceOf(ApiAuthenticationResult.class, result);

        final Account account = result.getAccount();

        logger.info("Account signed {}", account.getEmail());


        response.setStatus(HttpServletResponse.SC_OK);

        response.setContentType("application/json; charset=utf-8");

        final String[] bodyHolder = new String[1];

        result.accept(new AuthenticationResultVisitor() {
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
            public void visit(BasicOauthAuthenticationResult result) {
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

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ModelAndView login(@RequestParam("username") String username,
//                              @RequestParam("password") String password,
//                              @RequestParam(value = "rememberMe", required = false, defaultValue = "false") String rememberMe) {
//        try {
//            authenticationService.authenticate(username, password, "on".equals(rememberMe));
//        } catch (AuthenticationException e) {
//            logger.error(String.format("Error occurred while authenticating user. Description [%s].", e.getCause().getMessage()));
//            return new ModelAndView("redirect:/login");
//        }
//        return new ModelAndView("redirect:/home");
//    }

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
