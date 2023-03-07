package com.ftteporal.ft.workflow;


import com.ftteporal.ft.model.ActionDataModel;
import com.ftteporal.ft.model.WorkFlowItem;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.ActivityStub;
import io.temporal.workflow.DynamicSignalHandler;
import io.temporal.workflow.Workflow;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;
import org.slf4j.Logger;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.*;

public class TestWorkFlowImpl implements TestWorkFlow{

    private static final Logger logger = Workflow.getLogger(TestWorkFlowImpl.class);
//
//    private final TestActivities testActivity = Workflow.newActivityStub(
//            TestActivities.class,
//            ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(15)
//
//            ).setRetryOptions(RetryOptions.newBuilder()
//                    .setInitialInterval(Duration.ofSeconds(20)).build()) .build());
//

    ActivityStub activity =
            Workflow.newUntypedActivityStub(
                    ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(15)).build());


    private HashMap<String,Object> session = new HashMap<>();
    private HashMap<String, WorkFlowItem> workflow = new LinkedHashMap<>();

    private ActionDataModel sessionOp;


    @Override
    public void start(ActionDataModel dt)   {

        Workflow.registerListener(
                (DynamicSignalHandler)
                        (signalName, encodedArgs) -> {

                           logger.info(signalName, encodedArgs);

                        });



        sessionOp = dt;
        // session.putAll(dt.getData());
        Gson g = new Gson();
        Type listType = new TypeToken<ArrayList<WorkFlowItem>>(){}.getType();
        List<WorkFlowItem> wfConvert =  g.fromJson(dt.getWorkflow(), listType);
        Integer i =0;
        for (WorkFlowItem workflos :wfConvert
        ) {
            i++;
            workflow.put(i.toString(), workflos);
        }




        for(Map.Entry<String, WorkFlowItem> entry : workflow.entrySet()){
            if(entry.getValue().isCompleted()){
                continue;
            }
            logger.info(entry.getValue().toString());
            System.out.println("here");
            switch (entry.getValue().getType().toLowerCase()) {
                case "action":
                    try {
                        sessionOp = activity.execute(entry.getValue().getAction(), ActionDataModel.class, sessionOp);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    break;
                case "sleep":
                    try {
                        Workflow.sleep(Integer.valueOf(entry.getValue().getValue()));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    entry.getValue().setCompleted(true);
                    break;
                case "wait":
                    if(entry.getValue().getExpression() == null || entry.getValue().getExpression().isEmpty()){
                        try {
                            throw new Exception("Expression should not be blank for wait type");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
//                    while (true) {
//                        logger.info("while sessionOp", sessionOp);

                        // Block current thread until the unblocking condition is evaluated to true
                        CompiledExpression expression = new ExpressionCompiler(entry.getValue().getExpression()).compile();
                        boolean isSuccess = (Boolean) MVEL.executeExpression(expression, sessionOp);
                        logger.info("locking ", isSuccess);
                        Workflow.await(() -> isSuccess);
                        logger.info("locked ", isSuccess);
                        printHashmap(sessionOp.getPersistData());
                        entry.getValue().setCompleted(true);
//                        if (isSuccess) {
//                            break;
//                        }

                    // }
                    break;
            }


        }

      // String json = "[{\"input\": {},\"activity\": \"CRMLead\",\"condition\": \"\",\"nextActivity\":\"BRE\",\"output\": {}},{\"input\": {},\"activity\": \"CRMLead\",\"condition\": \"\",\"nextActivity\":\"BRE\",\"output\": {}}]";
//        workflow.put("a", "Create_Lead");
//        workflow.put("b", "Update_Lead");
//
//        workflow.put("d", "Update_Lead");
//        workflow.put("e", "BRE");
//        workflow.put("f", "Update_Lead");
//        workflow.put("g", "Hunter");
//        workflow.put("h", "Update_Lead");
//        workflow.put("i", "Hunter");
//
//        workflow.put("c", "Bureau");


//        ActionDataModel op1 = activity.execute("DynamicActtest", ActionDataModel.class, dt);
//        System.out.println(op1);
//       // ActionDataModel op =  testActivity.createCRMLead(dt);
//
//        ActionDataModel op = testActivity.createCRMLead(dt);
//
//        Workflow.sleep(1000);
//        op =  testActivity.bureauCall(op);
//        op = testActivity.updateCRMLead (op);
//        Workflow.sleep(1000);
//        op = testActivity.breCall (op);
//        op = testActivity.updateCRMLead(op);
//        Workflow.sleep(1000);
//        op = testActivity.hunterCall(op);
//        op = testActivity.updateCRMLead(op);



//        Workflow.retry( new RetryOptions.Builder()
//                .setInitialInterval(java.time.Duration.ofSeconds(5L))
//                .setExpiration(java.time.Duration.ofMinutes(1L))
//                .setDoNotRetry(IllegalArgumentException.class)
//                .build(),
//                ()->{
//                    try {


//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//        _isWaitingForFileupload = true;
//        System.out.println("Waiting now for file upload");
//        while (true) {
//            // Block current thread until the unblocking condition is evaluated to true
//            Workflow.await(() -> isAllFileUploaded);
//            if(isAllFileUploaded){
//                break;
//            }
//        }
//        Workflow.sleep(1000);
//        op = testActivity.omnidocsCall(op);
//        op = testActivity.updateCRMLead(op);

        System.out.println("All File uploaded");

    }


    void printHashmap(HashMap<String, Object> map){
        map.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
    }

    @Override
   public ActionDataModel getSession(){
        logger.info("getSession sessionOp", sessionOp);
        printHashmap(sessionOp.getPersistData());
        return sessionOp;

    }

    @Override
    public void genericSignal(HashMap map) {
        ActionDataModel _sessionOp = sessionOp;
        HashMap<String, Object> session =  sessionOp.getPersistData();
        session.putAll(map);
        _sessionOp.setPersistData(session);

        this.sessionOp = _sessionOp;
        logger.info("genericSignal sessionOp", sessionOp);
        printHashmap(sessionOp.getPersistData());

//           String[] arr = mergeUniqueValues(fileUploaded, files);
//           fileUploaded = arr;
//           isAllFileUploaded = Arrays.equals(fileUploaded, filesRequired);
//           System.out.println("all file uploaded == > "  + isAllFileUploaded);
    }

    public  String[] mergeUniqueValues(String[] arr1, String[] arr2) {
        Set noDuplicateSet = new HashSet();
        noDuplicateSet.addAll(Arrays.asList(arr1));
        noDuplicateSet.addAll(Arrays.asList(arr2));

        String[] noDuplicateArray = new String[noDuplicateSet.size()];
        noDuplicateSet.toArray(noDuplicateArray);

        return noDuplicateArray;

    }


}
