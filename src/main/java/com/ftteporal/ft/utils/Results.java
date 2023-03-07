package com.ftteporal.ft.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ftteporal.ft.model.ActResult;
import lombok.Builder;

import java.util.Arrays;
import java.util.HashMap;

@Builder
public class Results {
    private HashMap<String, ObjectNode> _results;

    public static ResultsBuilder builder(HashMap<String, ObjectNode> results) {
        return new ResultsBuilder()._results(results);
    }

    public ObjectNode getActivityResult(String actResultName){
       return _results.get(actResultName);
    }

}
