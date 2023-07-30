package com.ftteporal.ft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftteporal.ft.FtApplication;
import com.ftteporal.ft.activities.DslActivitiesImpl;
import com.ftteporal.ft.activities.DynamicTestActivity;
import com.ftteporal.ft.cache.DslWorkflowCache;
import com.ftteporal.ft.dto.WorkflowResponse;
import com.ftteporal.ft.model.ActionDataModel;
import com.ftteporal.ft.workflow.DynamicDSLWorkflow;
import com.ftteporal.ft.workflow.TestWorkFlow;
import io.serverlessworkflow.api.Workflow;
import io.serverlessworkflow.utils.WorkflowUtils;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import io.temporal.workflow.ExternalWorkflowStub;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.DslWorkflowUtils;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;

import static utils.DslWorkflowUtils.*;

// @RestController
public class TestWorkFlowController {
    private final WorkflowClient workflowClient;

    public TestWorkFlowController(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }



    private static final WorkflowServiceStubs service =  WorkflowServiceStubs.newServiceStubs(
            WorkflowServiceStubsOptions.newBuilder().setTarget("localhost:7233").build());

    private static final WorkflowClient client = WorkflowClient.newInstance(service);
    private static final WorkerFactory factory = WorkerFactory.newInstance(client);


    @Autowired
    ApplicationContext ctx;


    @PostConstruct
    void init() {
        System.out.println("started");



        io.temporal.worker.Worker worker = factory.newWorker(FtApplication.DEFAULT_TASK_QUEUE_NAME);
        worker.registerWorkflowImplementationTypes(DynamicDSLWorkflow.class);
        worker.registerActivitiesImplementations(new DynamicTestActivity(ctx));

        factory.start();
    }

    @PostMapping("/startWF")
    public ResponseEntity<WorkflowResponse> startWorkflow(@RequestBody ActionDataModel request) {
        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()

                .setTaskQueue(TestWorkFlow.TASK_LIST)
                .setWorkflowExecutionTimeout(Duration.ofDays(5))
                .setWorkflowId(request.getId())
                .build();
        TestWorkFlow workflow = workflowClient.newWorkflowStub(TestWorkFlow.class, workflowOptions);

        request.setPersistData(new HashMap<>());
        WorkflowExecution execution = WorkflowClient.start(workflow::start, request);

        WorkflowResponse responseBody = WorkflowResponse.success(execution.getWorkflowId());

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/signal")
    public ResponseEntity<WorkflowResponse> signal(@RequestParam String wid) {

        TestWorkFlow workflow = workflowClient.newWorkflowStub(TestWorkFlow.class, wid);
//        if(workflow.getSession().getPersistData().containsKey("issignal")){
//            WorkflowResponse responseBody = WorkflowResponse.error(wi, "Invalid stage");
//            return ResponseEntity.ok(responseBody);
//        }
        //      System.out.println(workflow.getSession());
//        ActionDataModel dt = workflow.getSession();
        HashMap map = new HashMap();
        map.put("issignal", "true");
        workflow.genericSignal(map);
        WorkflowResponse responseBody = WorkflowResponse.success(wid);

        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/uploadFile")
    public ResponseEntity<WorkflowResponse> uploadFile(@RequestParam String wi, @RequestParam String file) {
        TestWorkFlow workflow = workflowClient.newWorkflowStub(TestWorkFlow.class, wi);
//        if(workflow.getSession().getPersistData().containsKey("issignal")){
//            WorkflowResponse responseBody = WorkflowResponse.error(wi, "Invalid stage");
//            return ResponseEntity.ok(responseBody);
//        }
        //      System.out.println(workflow.getSession());
//        ActionDataModel dt = workflow.getSession();
        HashMap map = new HashMap();
        map.put("issignal", "true");
        workflow.genericSignal(map);
//        dt.getPersistData().putAll(map);
//
//        ExternalWorkflowStub externalWorkflowStub =
//                Workflow.newUntypedExternalWorkflowStub(wi);
//        externalWorkflowStub.signal("genericSignal", dt);


        //WorkflowExecution execution = WorkflowClient..start(workflow::start);

        WorkflowResponse responseBody = WorkflowResponse.success(wi);

        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/terminate")
    public ResponseEntity<WorkflowResponse> terminate(@RequestParam String wi, @RequestParam String reason) {
        TestWorkFlow workflow = workflowClient.newWorkflowStub(TestWorkFlow.class, wi);
        WorkflowStub untyped = WorkflowStub.fromTyped(workflow);
        untyped.terminate(reason);
        //WorkflowExecution execution = WorkflowClient..start(workflow::start);

        WorkflowResponse responseBody = WorkflowResponse.success(wi);

        return ResponseEntity.ok(responseBody);
    }


    @PostMapping("/startDSLWF")
    public ResponseEntity<WorkflowResponse> startWorkflowDSL(@RequestBody String request) throws Exception {
        JsonNode n = getWorkflowInput(request);
        Workflow wf = DslWorkflowCache.getWorkflow(n.get("workflow_code").asText());
        runWorkflow(n.get("id").asText(), request, true, wf);
        WorkflowResponse responseBody = WorkflowResponse.success("id");
        return ResponseEntity.ok(responseBody);
    }


    @PostMapping("/startDSLWFSignal")
    public ResponseEntity<WorkflowResponse> startWorkflowDSLSignal(@RequestBody String request, @RequestParam String id, @RequestParam String signal) {
        signalWorkflow(request,signal, id);
         WorkflowResponse responseBody = WorkflowResponse.success("id");

        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/uploadWorkflow")
    public ResponseEntity<String> uploadWorkflow(@RequestBody String workflow) {
        try {

            Workflow workflow1 = Workflow.fromSource(workflow);
            if(workflow1.getId() == null){
                throw  new Exception("Invalid workflow");
            }
            DslWorkflowCache.addWorkflow( workflow1.getVersion(), workflow1);


        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.ok("Ok");
    }




    private static void signalWorkflow(String request, String signalname,String id){
        WorkflowStub workflowStub =
                client.newUntypedWorkflowStub(id);
//        String str = "{\n" +
//                "  \"customer\": {\n" +
//                "    \"name\": \""+name+"\",\n" +
//                "    \"age\": 22\n" +
//                "  },\n" +
//                "  \"results\": []\n" +
//                "}";
        ObjectMapper map = new ObjectMapper();
        try {
            JsonNode node = map.readTree(request);
            workflowStub.signal(
                    signalname,
                    node);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


    private static void runWorkflow(
            String workflowId, String dataInputFileName, boolean doQuery, Workflow dslWorkflow) {
        try {
            // Get the workflow dsl from cache
           // io.serverlessworkflow.api.Workflow dslWorkflow = DslWorkflowCache.getWorkflow(workflowId+"-"+workflowVersion);


            WorkflowOptions workflowOptions = getWorkflowOptions(workflowId, dslWorkflow);

            WorkflowStub workflowStub =
                    client.newUntypedWorkflowStub(dslWorkflow.getName(), workflowOptions);

            System.out.println(
                    "Starting workflow with id: " + dslWorkflow.getId() + " and version: " + dslWorkflow.getVersion());
            // Start workflow execution
            DslWorkflowUtils.startWorkflow(workflowId, workflowStub, dslWorkflow, getWorkflowInput(dataInputFileName));

            // Wait for workflow to finish
           // JsonNode result = workflowStub.getResult(JsonNode.class);

//            if (doQuery) {
//                // Query the customer name and age
//                String customerName = workflowStub.query("QueryCustomerName", String.class);
//                int customerAge = workflowStub.query("QueryCustomerAge", Integer.class);
//
//                System.out.println("Query result for customer name: " + customerName);
//                System.out.println("Query result for customer age: " + customerAge);
//            }

            // Print workflow results
          //  System.out.println("Workflow results: \n" + result.toPrettyString());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
    }
}