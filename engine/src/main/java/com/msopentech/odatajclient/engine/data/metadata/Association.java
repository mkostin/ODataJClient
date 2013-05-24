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
package com.msopentech.odatajclient.engine.data.metadata;

/**
 * Metadata elements: <tt>&lt;Association/&gt;</tt>
 */
public class Association extends AbstractAnnotated {

    private static final long serialVersionUID = -7601778482483620788L;

    private String name;

    private AssociationEnd fromRole;

    private AssociationEnd toRole;

    private ReferentialConstraint referentialConstraint;

    public String getName() {
        return name;
    }

    public AssociationEnd getFromRole() {
        return fromRole;
    }

    public AssociationEnd getToRole() {
        return toRole;
    }

    public ReferentialConstraint getReferentialConstraint() {
        return referentialConstraint;
    }
}
