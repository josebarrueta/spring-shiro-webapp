package com.stormpath.sample.impl.converters;

import com.stormpath.sample.api.converters.MapValueRetriever;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * MapToAccountConverter converts a map body to a new account object.
 *
 * @since 0.42
 */
@Component
public class MapToAccountConverter implements Converter<Map, Account> {

    private final Client stormpathClient;
    private final MapValueRetriever mapValueRetriever;

    @Autowired
    public MapToAccountConverter(Client stormpathClient, MapValueRetriever mapValueRetriever) {
        this.mapValueRetriever = mapValueRetriever;
        this.stormpathClient = stormpathClient;
    }

    @Override
    public Account convert(Map map) {
        Assert.notNull(map, "map cannot be null.");

        String email = mapValueRetriever.getRequiredValue(map, "email", String.class);
        String password = mapValueRetriever.getRequiredValue(map, "password", String.class);
        String firstName = mapValueRetriever.getRequiredValue(map, "firstName", String.class);
        String lastName = mapValueRetriever.getRequiredValue(map, "lastName", String.class);
        AccountStatus status = mapValueRetriever.getOptionalValue(map, "status", AccountStatus.class);

        Account account = stormpathClient.instantiate(Account.class);
        account.setUsername(email);
        account.setEmail(email);
        account.setPassword(password);
        account.setSurname(lastName);
        account.setGivenName(firstName);

        if (status != null) {
            account.setStatus(status);
        }
        return account;
    }
}
