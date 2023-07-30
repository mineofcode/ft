package com.ftteporal.ft.workflow;

import com.ftteporal.ft.activities.TestActivities;
import com.ftteporal.ft.activities.TestActivitiesSignal;
import com.ftteporal.ft.interceptor.RetryOnSignalInterceptorListener;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.CancellationScope;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class SignalWorkflowImpl implements SignalWorkflow{


    TestActivitiesSignal activities = Workflow.newActivityStub(TestActivitiesSignal.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(30))
                    //.setStartToCloseTimeout(Duration.ofSeconds(12))
                    .setRetryOptions(RetryOptions.newBuilder().setBackoffCoefficient(1).setInitialInterval(Duration.ofSeconds(5)).setMaximumAttempts(0).build())
                    .build());

    String sessionData = "";
    CancellationScope c;
    @Override
    public void start(String data) {
        sessionData = data;
        activities.initiateActivity();
        System.out.println("Retry");

        c = createCrmLead();
        c.run();

        activities.bureauCall(getData());

        activities.updateCRMLead(getData());

    }


    CancellationScope createCrmLead(){
        return Workflow.newCancellationScope(()->{
            activities.createCRMLead(getData());
        });
    }

    @Override
    public void genericSignal(String data) {
        sessionData = data;
        if(!c.isCancelRequested()){
            c.cancel();
        };


    }

    @Override
    public String getData() {
        return sessionData;
    }
}
