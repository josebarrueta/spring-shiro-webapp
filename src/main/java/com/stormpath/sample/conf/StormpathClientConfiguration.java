package com.stormpath.sample.conf;

import com.stormpath.sdk.client.Client;

/**
 * @since 1.0.RC
 */
public interface StormpathClientConfiguration {

    public Client getClient();
}
