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
