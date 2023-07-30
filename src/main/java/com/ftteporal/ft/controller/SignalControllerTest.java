package com.ftteporal.ft.controller;

import com.ftteporal.ft.FtApplication;
import com.ftteporal.ft.activities.DynamicTestActivity;
import com.ftteporal.ft.activities.TestActivitiesSignal;
import com.ftteporal.ft.activities.TestActivitiesSignalImpl;
import com.ftteporal.ft.dto.WorkflowResponse;
import com.ftteporal.ft.interceptor.RetryOnSignalInterceptorListener;
import com.ftteporal.ft.interceptor.RetryOnSignalWorkerInterceptor;
import com.ftteporal.ft.model.ActionDataModel;
import com.ftteporal.ft.workflow.DynamicDSLWorkflow;
import com.ftteporal.ft.workflow.SignalWorkflow;
import com.ftteporal.ft.workflow.SignalWorkflowImpl;
import com.ftteporal.ft.workflow.TestWorkFlow;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerFactoryOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

@RestController
public class SignalControllerTest {

    private final WorkflowClient workflowClient;

    public SignalControllerTest(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @Autowired
    ApplicationContext ctx;

//    private static final WorkflowServiceStubs service =  WorkflowServiceStubs.newServiceStubs(
//            WorkflowServiceStubsOptions.newBuilder().setTarget("localhost:7233").build());
//    private static final WorkflowClient client = WorkflowClient.newInstance(service);
//    private static final WorkerFactory factory = WorkerFactory.newInstance(client);

    @PostConstruct
    void init() {

        WorkerFactoryOptions factoryOptions =
                WorkerFactoryOptions.newBuilder()
                        .setWorkerInterceptors(new RetryOnSignalWorkerInterceptor())
                        .validateAndBuildWithDefaults();

        System.out.println("started");
        final WorkerFactory factory = WorkerFactory.newInstance(this.workflowClient, factoryOptions);
        io.temporal.worker.Worker worker = factory.newWorker("Test3");
        worker.registerWorkflowImplementationTypes(SignalWorkflowImpl.class);
        worker.registerActivitiesImplementations(new TestActivitiesSignalImpl(ctx));
        factory.start();
    }

    @PostMapping("/startWF")
    public ResponseEntity<WorkflowResponse> startWorkflow(@RequestBody String request) {


        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()

                .setTaskQueue("Test3")
                .setWorkflowExecutionTimeout(Duration.ofDays(5))
                .setWorkflowId(String.valueOf(Instant.now().toEpochMilli()))
                .build();
        SignalWorkflow workflow = workflowClient.newWorkflowStub(SignalWorkflow.class, workflowOptions);


        WorkflowExecution execution = WorkflowClient.start(workflow::start, request);

        WorkflowResponse responseBody = WorkflowResponse.success(execution.getWorkflowId());

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/signal")
    public ResponseEntity<WorkflowResponse> signal(@RequestParam String request, @RequestParam String workflowid) {


       SignalWorkflow workflow = workflowClient.newWorkflowStub(SignalWorkflow.class, workflowid);

       workflow.genericSignal(request);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
//        WorkflowClient client = WorkflowClient.newInstance(service);

        // Note that we use the listener interface that the interceptor registered dynamically, not the
        // workflow interface.
        RetryOnSignalInterceptorListener w =
                workflowClient.newWorkflowStub(RetryOnSignalInterceptorListener.class, workflowid);

        // Sends "Retry" signal to the workflow.
        String[] d = {workflow.getData()};
        w.retry(d);

        WorkflowResponse responseBody = WorkflowResponse.success(workflowid);

        return ResponseEntity.ok(responseBody);
    }


}
