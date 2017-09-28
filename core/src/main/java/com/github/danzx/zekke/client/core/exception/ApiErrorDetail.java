/*
 * Copyright 2017 Daniel Pedraza-Arcega
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.danzx.zekke.client.core.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * API Error response object.
 * 
 * @author Daniel Pedraza-Arcega
 */
public class ApiErrorDetail {

    public enum ErrorType { PARAM_VALIDATION, NOT_FOUND, SERVER_ERROR, AUTHORIZATION, OTHER }

    private int statusCode;
    private String errorDetail;
    private ErrorType errorType;
    private Map<String, String> paramErrors;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public Map<String, String> getParamErrors() {
        if (paramErrors == null) paramErrors = new HashMap<>();
        return paramErrors;
    }

    public void setParamErrors(Map<String, String> paramErrors) {
        this.paramErrors = paramErrors;
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, errorDetail, errorType, paramErrors);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return isErrorMessageEqualTo((ApiErrorDetail) obj);
    }

    /**
     * Use this method to complete your equals method.
     *
     * @see Object#equals(Object)
     * @param other the reference object with which to compare.
     * @return {@code true} if this object is the same as the argument; {@code false} otherwise.
     */
    protected boolean isErrorMessageEqualTo(ApiErrorDetail other) {
        return Objects.equals(statusCode, other.statusCode) &&
                Objects.equals(errorDetail, other.errorDetail) &&
                Objects.equals(errorType, other.errorType) &&
                Objects.equals(paramErrors, other.paramErrors);
    }

    @Override
    public String toString() {
        return "{ statusCode:" + statusCode + ", errorDetail:" + errorDetail + ", errorType:" + errorType + ", paramErrors:" + paramErrors + " }";
    }
}
