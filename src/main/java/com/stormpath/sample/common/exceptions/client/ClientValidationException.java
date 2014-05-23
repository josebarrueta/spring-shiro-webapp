package com.stormpath.sample.common.exceptions.client;

/**
 * ClientValidationException
 *
 * @since 1.0.1
 */
public class ClientValidationException extends ClientException {

    private final int code;

    public ClientValidationException(com.stormpath.sample.common.Error error, String... parameters) {
        super(String.format(error.getErrorMessageTemplate(), parameters));
        this.code = error.getCode();
    }

    public int getCode() {
        return this.code;
    }
}
