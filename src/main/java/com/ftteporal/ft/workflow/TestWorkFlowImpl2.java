package com.ftteporal.ft.workflow;

import com.ftteporal.ft.activities.TestActivities;
import com.ftteporal.ft.model.ActionDataModel;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestWorkFlowImpl2 implements  TestWorkFlow{

    private final Logger workflowLogger = Workflow.getLogger(TestWorkFlowImpl2.class);

    private final HashMap<String, Object> modelSession = new HashMap<>();

    private List<String> eventList = new ArrayList<>();
    TestActivities activities = Workflow.newActivityStub(TestActivities.class,
            ActivityOptions.newBuilder()
                    .setScheduleToCloseTimeout(Duration.ofSeconds(30))
                     //.setStartToCloseTimeout(Duration.ofSeconds(12))
                    .setRetryOptions(RetryOptions.newBuilder().setInitialInterval(Duration.ofSeconds(4)).build())
                    .build());

    @Override
    public void start(ActionDataModel dt) {
        workflowLogger.info("Crm Create Lead >>>> ");
        activities.createCRMLead(dt);
        workflowLogger.info("After Crm Create Lead >>>> ");

        Workflow.await(()->
                {
                    if( eventList.size() > 0) {
                        workflowLogger.info(modelSession.get("data").toString());
                    }
                 return    eventList.size() >= 10;

                }
        );
        workflowLogger.info("Workflow completed");

    }

    @Override
    public void genericSignal(HashMap action) {


        workflowLogger.info("Add signal ");
        eventList.add("test");
        modelSession.put("data", eventList.size());
    }

    @Override
    public HashMap<String, Object>  getSession() {
        return modelSession;
    }
}
