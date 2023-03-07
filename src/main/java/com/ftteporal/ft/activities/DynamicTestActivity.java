package com.ftteporal.ft.activities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftteporal.ft.enums.EActionType;
import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.ActionDataModel;
import com.ftteporal.ft.services.IAction;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.DynamicActivity;
import io.temporal.common.converter.EncodedValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.net.URISyntaxException;


public class DynamicTestActivity implements DynamicActivity {

    ApplicationContext ctx;

    ObjectMapper objectMapper = new ObjectMapper();

    public DynamicTestActivity(ApplicationContext context)
    {
        this.ctx = context;
    }


    @Override
    public ActResult execute(EncodedValues encodedValues) {
        String activityType = Activity.getExecutionContext().getInfo().getActivityType();
        IAction i = (IAction) ctx.getBean(activityType);
        try {
            JsonNode n =  encodedValues.get(0, JsonNode.class);
            ActionDataModel act = objectMapper.convertValue(n.get("value"), ActionDataModel.class);
            return i.execute(act);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


//        System.out.println(encodedValues);
//        System.out.println(activityType);
//        try {
//            Integer.parseInt("1");
//        }catch (Exception ex){
//            throw ex;
//        }
//        return ActionDataModel.builder().actionType(EActionType.UPDATE).build();
    }
}
