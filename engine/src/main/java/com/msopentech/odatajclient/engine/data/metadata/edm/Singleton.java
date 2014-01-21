package com.msopentech.odatajclient.engine.data.metadata.edm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Singleton extends AbstractAnnotatedEdm {

    private static final long serialVersionUID = -4997942740582963190L;

    @JsonProperty(value = "Name", required = true)
    private String name;
    
    @JsonProperty(value = "Type", required = true)
    private String type;
    
    // this class also contains list of NavigationPropertyBindings, let ODataJClient library team implement it

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
