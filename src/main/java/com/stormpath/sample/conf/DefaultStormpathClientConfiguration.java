package com.stormpath.sample.conf;

import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.shiro.cache.ShiroCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * DefaultStormpathClientConfiguration
 *
 * @since 1.0.1
 */
@Configuration("stormpathClientConfiguration")
@Import(PropertiesConfiguration.class)
@Profile({"default", "production"})
public class DefaultStormpathClientConfiguration implements StormpathClientConfiguration {

    @Autowired
    private CacheConfiguration cacheConfiguration;

    @Value("${stormpath.apiKeyFileLocation}")
    private String apiKeyFileLocation;

    @Override
    @Bean
    public Client getClient() {
        ClientBuilder builder = Clients.builder().setApiKey(ApiKeys.builder().setFileLocation(apiKeyFileLocation).build())
                .setCacheManager(new ShiroCacheManager(cacheConfiguration.cacheManager()));

        return builder.build();
    }
}
