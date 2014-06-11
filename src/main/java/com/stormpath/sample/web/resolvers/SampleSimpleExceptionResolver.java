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
