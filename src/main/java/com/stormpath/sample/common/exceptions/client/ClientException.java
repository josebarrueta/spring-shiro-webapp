package com.stormpath.sample.common.exceptions.client;

/**
 * ClientException
 *
 * @since 1.0.1
 */
public class ClientException extends RuntimeException {

    public ClientException(String message) {
        super(message);
    }
}
