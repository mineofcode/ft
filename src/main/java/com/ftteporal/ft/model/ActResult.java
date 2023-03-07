package com.ftteporal.ft.model;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class ActResult {
    private String type;
    private ObjectNode output;


    public ActResult() {}

    public ActResult(String type, ObjectNode result) {
        this.type = type;
        this.output = result;
    }

    public ObjectNode getOutput() {
        return output;
    }

    public void setOutput(ObjectNode output) {
        this.output = output;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}