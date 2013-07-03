/*
 * Copyright 2013 MS OpenTech.
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
package com.msopentech.odatajclient.engine.data.json.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.msopentech.odatajclient.engine.data.ODataError;
import com.msopentech.odatajclient.engine.data.json.AbstractJSONObject;

public class JSONODataError extends AbstractJSONObject implements ODataError {

    private static final long serialVersionUID = -3476499168507242932L;

    static class Message extends AbstractJSONObject {

        private static final long serialVersionUID = 2577818040815637859L;

        private String lang;

        private String value;

        public String getLang() {
            return lang;
        }

        public void setLang(final String lang) {
            this.lang = lang;
        }

        public String getValue() {
            return value;
        }

        public void setValue(final String value) {
            this.value = value;
        }
    }

    static class InnerError extends AbstractJSONObject {

        private static final long serialVersionUID = -3920947476143537640L;

        private String message;

        private String type;

        private String stacktrace;

        public String getMessage() {
            return message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(final String type) {
            this.type = type;
        }

        public String getStacktrace() {
            return stacktrace;
        }

        public void setStacktrace(final String stacktrace) {
            this.stacktrace = stacktrace;
        }
    }

    private String code;

    @JsonProperty(value = "message")
    private Message message;

    @JsonProperty(value = "innererror", required = false)
    private InnerError innererror;

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @JsonIgnore
    @Override
    public String getMessageLang() {
        return this.message == null ? null : this.message.getLang();
    }

    @JsonIgnore
    @Override
    public String getMessageValue() {
        return this.message == null ? null : this.message.getValue();
    }

    @JsonIgnore
    @Override
    public String getInnerErrorMessage() {
        return this.innererror == null ? null : this.innererror.getMessage();
    }

    @JsonIgnore
    @Override
    public String getInnerErrorType() {
        return this.innererror == null ? null : this.innererror.getType();
    }

    @JsonIgnore
    @Override
    public String getInnerErrorStacktrace() {
        return this.innererror == null ? null : this.innererror.getStacktrace();
    }
}
