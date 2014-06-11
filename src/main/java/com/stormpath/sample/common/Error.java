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
package com.stormpath.sample.common;

/**
 *
 * @since 1.0.1
 */
public enum Error {

    REQUIRED_VALUE(1000, "%s is a required value."),
    INVALID_VALUE(1001, "%s value is invalid");

    private int code;
    private String errorMessageTemplate;

    private Error(int code, String errorMessageTemplate) {
        this.code = code;
        this.errorMessageTemplate = errorMessageTemplate;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessageTemplate() {
        return this.errorMessageTemplate;
    }
}
