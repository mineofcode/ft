package com.ftteporal.ft.activities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.ActionDataModel;
import com.ftteporal.ft.services.CRMActionService;
import com.ftteporal.ft.services.DataService;
import org.springframework.context.ApplicationContext;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestActivitiesImpl implements TestActivities {

    ApplicationContext ctx;

    public TestActivitiesImpl(ApplicationContext context)
    {
        this.ctx = context;
    }

    @Override
    public ActResult createCRMLead(ActionDataModel param) {
        ActResult output = null;
        output = new ActResult();
        output.setType("TEST");
        output.setOutput(null);

        //output = ctx.getBean(CRMActionService.class).execute(param);
        return output;
    }

    @Override
    public ActResult updateCRMLead(ActionDataModel param) {
        HashMap Data = ctx.getBean(DataService.class).getData();
        HashMap perData = (param.getPersistData() == null ?  new HashMap<>(): param.getPersistData());
        perData.put("CRM_UPDATE", ((ArrayList<Object>)Data.get("data")).get(1).toString());
        return null;
    }

    @Override
    public ActResult bureauCall(ActionDataModel param)  {
//        return null;
        HashMap Data = ctx.getBean(DataService.class).getData();
        HashMap perData = (param.getPersistData() == null ?  new HashMap<>(): param.getPersistData());
        perData.put("BUREAU", ((ArrayList<Object>)Data.get("data")).get(2).toString());
        return null;
    }

    @Override
    public ActResult breCall(ActionDataModel param)  {

        try {
            Integer.parseInt("1");
        }catch (Exception ex){
            throw ex;
        }
        HashMap Data = ctx.getBean(DataService.class).getData();
        HashMap perData = (param.getPersistData() == null ?  new HashMap<>(): param.getPersistData());
        perData.put("BRE", ((ArrayList<Object>)Data.get("data")).get(3).toString());
        return null;
    }

    @Override
    public ActResult hunterCall(ActionDataModel param)  {

        HashMap Data = ctx.getBean(DataService.class).getData();
        HashMap perData = (param.getPersistData() == null ?  new HashMap<>(): param.getPersistData());
        perData.put("HUNTER", ((ArrayList<Object>)Data.get("data")).get(4).toString());
        return null;

    }

    @Override
    public ActResult omnidocsCall(ActionDataModel param)  {
        HashMap Data = ctx.getBean(DataService.class).getData();
        HashMap perData = (param.getPersistData() == null ?  new HashMap<>(): param.getPersistData());
        perData.put("OMNI", ((ArrayList<Object>)Data.get("data")).get(5).toString());
        return null;
    }
}
