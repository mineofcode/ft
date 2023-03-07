package com.ftteporal.ft.cache;

import io.serverlessworkflow.api.Workflow;
import utils.DslWorkflowUtils;

import java.util.HashMap;
import java.util.Map;

public class DslWorkflowCache {
    private static class WorkflowHolder {
        static final Map<String, Workflow> dslWorkflowMap = new HashMap<>();

//        static {
//            try {
//                Workflow customerApplicationWorkflow =
//                        Workflow.fromSource(DslWorkflowUtils.getFileAsString("dsl/customerapplication/workflow.json"));
////                Workflow bankingTransactionsWorkflow =
////                        Workflow.fromSource(DslWorkflowUtils.getFileAsString("dsl/bankingtransactions/workflow.yml"));
//                Workflow applicantWorkflow =
//                        Workflow.fromSource(DslWorkflowUtils.getFileAsString("dsl/customerapproval/applicantworkflow.json"));
//                Workflow approvalWorkflow =
//                        Workflow.fromSource(DslWorkflowUtils.getFileAsString("dsl/customerapproval/approvalworkflow.json"));
////                Workflow bankingParentWorkflow =
////                        Workflow.fromSource(
////                                DslWorkflowUtils.getFileAsString("dsl/bankingtransactionssubflow/parentworkflow.json"));
////                Workflow bankingChildWorkflow =
////                        Workflow.fromSource(
////                                DslWorkflowUtils.getFileAsString("dsl/bankingtransactionssubflow/childworkflow.json"));
//
//                dslWorkflowMap.put(
//                        customerApplicationWorkflow.getId() + "-" + customerApplicationWorkflow.getVersion(),
//                        customerApplicationWorkflow);
////                dslWorkflowMap.put(
////                        bankingTransactionsWorkflow.getId() + "-" + bankingTransactionsWorkflow.getVersion(),
////                        bankingTransactionsWorkflow);
//                dslWorkflowMap.put(
//                        applicantWorkflow.getId() + "-" + applicantWorkflow.getVersion(), applicantWorkflow);
//                dslWorkflowMap.put(
//                        approvalWorkflow.getId() + "-" + approvalWorkflow.getVersion(), approvalWorkflow);
////                dslWorkflowMap.put(
////                        bankingParentWorkflow.getId() + "-" + bankingParentWorkflow.getVersion(),
////                        bankingParentWorkflow);
////                dslWorkflowMap.put(
////                        bankingChildWorkflow.getId() + "-" + bankingChildWorkflow.getVersion(),
////                        bankingChildWorkflow);
//            } catch (Exception e) {
//                System.out.println("Exception: " + e.getMessage());
//            }
//        }
    }

    public static void addWorkflow(String workflowId, Workflow workflow ) {
         WorkflowHolder.dslWorkflowMap.put(workflowId, workflow);
    }

    public static Workflow getWorkflow(String workflowId) {
        return WorkflowHolder.dslWorkflowMap.get(workflowId);
    }
}
