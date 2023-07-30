package com.ftteporal.ft.tasks_workers;

import com.ftteporal.ft.activities.DynamicTestActivity;
import com.ftteporal.ft.activities.TestActivitiesImpl;
import com.ftteporal.ft.common.Constants;
import com.ftteporal.ft.workflow.TestWorkFlowImpl;
import com.ftteporal.ft.workflow.TestWorkFlowImpl2;
import com.google.protobuf.util.Durations;
import io.temporal.api.namespace.v1.NamespaceConfig;
import io.temporal.api.namespace.v1.UpdateNamespaceInfo;
import io.temporal.api.workflowservice.v1.*;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Component
public class TestQWorkerStarter {
    public static final String TASK_QUEUE = "Test2";

    @Autowired
    ApplicationContext ctx;

    private final WorkflowServiceStubs workflowService;
    private final WorkerFactory workerFactory;
    private final WorkerOptions workerOptions;


    public TestQWorkerStarter(
            WorkflowServiceStubs workflowService,
                         WorkerFactory workerFactory,
                         WorkerOptions workerOptions
            ) {
        this.workflowService = workflowService;
        this.workerFactory = workerFactory;
        this.workerOptions = workerOptions;
    }

    @PostConstruct
    public void startWorkerFactory() {
        if (!domainExists()) {
            registerDomain();
        }
        createWorkers();
//        log.info("Starting Cadence Worker Factory");
        workerFactory.start();
    }

    @PreDestroy
    public void shutdownWorkerFactory() {
        //log.info("Shutdown Cadence Worker Factory");
        workerFactory.shutdown();
    }

    private void registerDomain()  {
        RegisterNamespaceRequest request =
                RegisterNamespaceRequest.newBuilder()
                        .setNamespace(Constants.Namespace_Default)
                        .setWorkflowExecutionRetentionPeriod(Durations.fromDays(90))
                        .build();
        workflowService.blockingStub().registerNamespace(request);

    }

    private void createWorkers() {
        Worker worker = workerFactory.newWorker(TASK_QUEUE, workerOptions);
        worker.registerWorkflowImplementationTypes(TestWorkFlowImpl2.class);
        worker.registerActivitiesImplementations(new TestActivitiesImpl(ctx),new DynamicTestActivity(ctx) );
    }



    private boolean domainExists()   {
        try {
            ListNamespacesResponse response = workflowService.blockingStub().listNamespaces(
                    ListNamespacesRequest.newBuilder().build()
            );
            List<DescribeNamespaceResponse> domains = response.getNamespacesList();

            return domains.stream()
                    .anyMatch(d -> d.getNamespaceInfo().getName().equals(Constants.Namespace_Default));
        } catch (UnsupportedOperationException e) {
//            log.warn("Listing or registering domains is not supported when using a local embedded test server, " +
//                    "these steps will be skipped");
            return true; // evaluate as true so domain won't be registered.
        }
    }
}
