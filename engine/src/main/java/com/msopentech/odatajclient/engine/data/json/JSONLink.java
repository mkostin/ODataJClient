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
package com.msopentech.odatajclient.engine.data.json;

import com.msopentech.odatajclient.engine.data.LinkResource;
import org.w3c.dom.Element;

/**
 * Link from an entry, represented via JSON.
 */
public class JSONLink implements LinkResource {

    private String title;

    private String rel;

    private String href;

    public JSONLink(String title, String rel, String href) {
        this.title = title;
        this.rel = rel;
        this.href = href;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getRel() {
        return rel;
    }

    @Override
    public void setRel(String rel) {
        this.rel = rel;
    }

    @Override
    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void setType(String type) {
    }

    @Override
    public Element getContent() {
        return null;
    }

    @Override
    public void setContent(Element content) {
        
    }
}
