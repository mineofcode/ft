package com.ftteporal.ft.workflow;


import com.ftteporal.ft.model.ActionDataModel;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.util.HashMap;

@WorkflowInterface
public interface SignalWorkflow {

    @WorkflowMethod()
    void start(String data) ;

    @SignalMethod
    void genericSignal(String data);

    @QueryMethod
    String getData();

}
