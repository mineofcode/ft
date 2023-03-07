package com.ftteporal.ft.workflow;

import com.ftteporal.ft.model.ActionDataModel;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.util.HashMap;

@WorkflowInterface
public interface TestWorkFlow {
    String TASK_LIST = "Test2";

    @WorkflowMethod()
    void start(ActionDataModel dt) ;

    @SignalMethod
    void genericSignal(HashMap action);

    @QueryMethod
    ActionDataModel getSession();

}
