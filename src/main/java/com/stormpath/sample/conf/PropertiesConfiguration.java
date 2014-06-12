package com.stormpath.sample.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

/**
 * PropertiesConfiguration
 * @since 1.0.1
 */
@Configuration
public class PropertiesConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        String propertiesLocation = System.getProperty("defaultProperties.location");

        PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setLocation(new FileSystemResource(propertiesLocation));
        ppc.setIgnoreUnresolvablePlaceholders(true);

        return ppc;
    }
}
