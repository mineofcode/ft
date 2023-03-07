package com.ftteporal.ft.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.ActionDataModel;
import io.temporal.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BureauAction")
public class BureauActionService implements IAction {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public ActResult execute(ActionDataModel input) {
        try {
            String newString = "{\"score\": \"700\"}";
            ObjectNode newNode =  (ObjectNode) mapper.readTree(newString);
            newNode.put("prop1",  "test extended");
            newNode.put("prop2",  "test");

            return new ActResult(Activity.getExecutionContext().getInfo().getActivityType(), newNode);
        } catch (Exception e) {
            return null;
        }
    }
}
