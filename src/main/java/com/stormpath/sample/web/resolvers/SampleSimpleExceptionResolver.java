package com.stormpath.sample.web.resolvers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * Class SampleSimpleExceptionResolver is a resolver class to get the exception reason and message.
 *
 * @author josebarrueta
 * @since 1.0.1
 */
public class SampleSimpleExceptionResolver  extends SimpleMappingExceptionResolver{

    private static final Logger logger = LoggerFactory.getLogger(SampleSimpleExceptionResolver.class);

    @Override
    protected ModelAndView getModelAndView(String viewName, Exception ex) {
        logger.error(String.format("Error of type [%s]. Exception reason: [%s]",ex.getClass().getSimpleName(), ex.getMessage()), ex);
        return super.getModelAndView(viewName, ex);
    }
}
