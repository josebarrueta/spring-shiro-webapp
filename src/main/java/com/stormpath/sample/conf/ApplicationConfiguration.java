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
package com.stormpath.sample.conf;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.stormpath.sample.security.HttpRequestAuthenticationToken;
import com.stormpath.sample.security.MultiTokenApplicationRealm;
import com.stormpath.sample.web.resolvers.SampleSimpleExceptionResolver;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.shiro.realm.ApplicationRealm;
import com.stormpath.shiro.realm.DefaultGroupRoleResolver;
import com.stormpath.shiro.realm.GroupRoleResolver;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * BeansConfiguration is a Java Configuration file to declare the Spring managed beans.
 *
 * @since 1.0.1
 */
@Configuration
@ComponentScan("com.stormpath.sample")
@EnableWebMvc
@Import({PropertiesConfiguration.class})
public class ApplicationConfiguration {

    @Autowired
    private CacheConfiguration cacheConfiguration;

    @Autowired
    private StormpathClientConfiguration stormpathClientConfiguration;

    @Value("${stormpath.application.restUrl}")
    private String applicationRestUrl;

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(applicationRealm());
        securityManager.setCacheManager(cacheConfiguration.cacheManager());
        return securityManager;
    }

    @Bean
    public GroupRoleResolver groupRoleResolver() {
        DefaultGroupRoleResolver resolver = new DefaultGroupRoleResolver();
        resolver.setModeNames(new ImmutableSet.Builder<String>().add("href").add("id").add("name").build());
        return resolver;
    }

    private ApplicationRealm applicationRealm() {

        MultiTokenApplicationRealm realm = new MultiTokenApplicationRealm();
        realm.setClient(stormpathClientConfiguration.getClient());
        realm.setGroupRoleResolver(groupRoleResolver());
        realm.setApplicationRestUrl(applicationRestUrl);
        realm.setName("ssoRealm");
        return realm;
    }

    @Bean(name = "authenticatedAccountRetriever")
    @Scope("prototype")
    public Account authenticatedAccountRetriever() {
        return stormpathClientConfiguration.getClient().getResource(SecurityUtils.getSubject().getPrincipal().toString(), Account.class);
    }

    @Bean(name = "cloudApplication")
    @Scope("prototype")
    public Application cloudApplication() {
        return stormpathClientConfiguration.getClient().getResource(applicationRestUrl, Application.class);
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        attributeSourceAdvisor.setSecurityManager(securityManager());
        return attributeSourceAdvisor;
    }

    @Bean
    public Map<String, String> filterChainDefinitionsMap() {
        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<String, String>()
                .put("/views/**", "anon")
                .put("/assets/**", "anon")
                .put("/favicon.ico", "anon")
                .put("/login", "anon")
                .put("/sso/redirect", "anon")
                .put("/sso/response", "anon")
                .put("/api/login", "anon")
                .put("/authorization/google", "anon")
                .put("/signup", "anon")
                .put("/admin/**", "authc")
                .put("/home", "authc")
                .put("/logout", "logout")
                .put("/**", "authc");
        return mapBuilder.build();
    }

    @Bean
    public AbstractShiroFilter shiroFilter() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager());
        factoryBean.setLoginUrl("/login");
        factoryBean.setSuccessUrl("/home");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionsMap());
        try {
            return (AbstractShiroFilter) factoryBean.getObject();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot build shiroFilter", e);
        }
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public SampleSimpleExceptionResolver sampleSimpleExceptionResolver() {
        SampleSimpleExceptionResolver resolver = new SampleSimpleExceptionResolver();
        resolver.setDefaultErrorView("error500");

        Properties exceptionMappings = new Properties();
        exceptionMappings.setProperty("org.apache.shiro.authz.AuthorizationException", "error403");
        resolver.setExceptionMappings(exceptionMappings);

        return resolver;
    }
}