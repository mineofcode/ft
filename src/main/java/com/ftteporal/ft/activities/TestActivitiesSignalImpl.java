package com.ftteporal.ft.activities;

import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.ActionDataModel;
import com.ftteporal.ft.services.DataService;
import io.temporal.failure.ApplicationFailure;
import io.temporal.workflow.Workflow;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;

public class TestActivitiesSignalImpl implements TestActivitiesSignal {

    ApplicationContext ctx;

    public TestActivitiesSignalImpl(ApplicationContext context)
    {
        this.ctx = context;
    }


    @Override
    public ActResult initiateActivity() {
        return null;
    }

    @Override
    public ActResult createCRMLead(String input) {

        if(input.equals("1")){
            //System.out.println(Workflow.getInfo().getWorkflowId());
            throw ApplicationFailure.newFailure("applicationFailer", "type1");

        }
        return null;
    }

    @Override
    public ActResult updateCRMLead(String input) {
        return null;
    }

    @Override
    public ActResult bureauCall(String input) {
        return null;
    }
}
