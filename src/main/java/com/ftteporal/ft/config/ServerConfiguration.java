package com.ftteporal.ft.config;

import com.ftteporal.ft.common.Constants;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerFactoryOptions;
import io.temporal.worker.WorkerOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfiguration {


    @Bean
    @ConditionalOnMissingBean(WorkflowServiceStubs.class)
    public WorkflowServiceStubs serviceStub() {
        return  WorkflowServiceStubs.newServiceStubs(
                WorkflowServiceStubsOptions.newBuilder().setTarget("localhost:7233").build()
        );
    }

    @Bean
    @ConditionalOnMissingBean(WorkflowClientOptions.class)
    public WorkflowClientOptions cadenceClientOptions() {
        return WorkflowClientOptions.newBuilder()

                .setIdentity(Constants.Namespace_Default)
                .build();
    }


    @Bean
    @ConditionalOnMissingBean(WorkflowClient.class)
    public WorkflowClient workflowClient(WorkflowServiceStubs workflowService,
                                         WorkflowClientOptions workflowClientOptions) {
        return WorkflowClient.newInstance(workflowService, workflowClientOptions);
    }


    @Bean
    @ConditionalOnMissingBean(WorkerFactoryOptions.class)
    public WorkerFactoryOptions workerFactoryOptions() {
        return WorkerFactoryOptions.newBuilder().build();
    }

    @Bean
    @ConditionalOnMissingBean(WorkerFactory.class)
    public WorkerFactory workerFactory(WorkflowClient workflowClient,
                                       WorkerFactoryOptions workerFactoryOptions) {
        return WorkerFactory.newInstance(workflowClient, workerFactoryOptions);
    }


    @Bean
    @ConditionalOnMissingBean(WorkerOptions.class)
    public WorkerOptions workerOptions() {
        return WorkerOptions.newBuilder().build();
    }
}
