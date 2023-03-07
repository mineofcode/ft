package com.ftteporal.ft.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.ActionDataModel;
import com.ftteporal.ft.utils.Results;
import io.temporal.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BREAction")
public class BREActionService implements IAction{

    @Autowired
    private ObjectMapper mapper;

    @Override
    public ActResult execute(ActionDataModel input) {
        try {
            Results rs = Results.builder(input.getResults()).build();
            ObjectNode obj = rs.getActivityResult("BureauAction");
            return new ActResult(Activity.getExecutionContext().getInfo().getActivityType(), obj);
        } catch (Exception e) {
            return null;
        }
    }
}
