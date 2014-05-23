package com.stormpath.sample.api.converters;

import com.stormpath.sample.common.exceptions.client.ClientValidationException;

import java.util.Map;

/**
 * MapValueRetriever represents
 *
 * @since 1.0.1
 */
public interface MapValueRetriever {

    <T> T getRequiredValue(Map body, String propertyName, Class<T> targetClass) throws ClientValidationException;

    <T> T getOptionalValue(Map body, String propertyName, Class<T> targetClass) throws ClientValidationException;

}
