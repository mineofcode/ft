package com.ftteporal.ft;

import com.ftteporal.ft.activities.DslActivitiesImpl;
import com.ftteporal.ft.model.WorkFlowItem;
import com.ftteporal.ft.workflow.DynamicDSLWorkflow;
import com.google.gson.Gson;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class FtApplication {
	public static final String DEFAULT_TASK_QUEUE_NAME = "dsltaskqueue";
	public static void main(String[] args) {




//		Map<String, Object> global = new HashMap<>();
//		global.put("a", "abc");
//		global.put("b", "");
//
//
//
//
//		Map<String, Object> client = new HashMap<>();
//		client.put("name", "ByJus");
//		client.put("product", "p001");
//		client.put("callbackUrl", "https://dfdf.com/asda");
//		client.put("delar_code", "001");
//
//		Map<String, Object> request = new HashMap<>();
//		request.put("first_name", "Mr Pratik");
//		request.put("last_name", "Naik");
//
//		global.putAll(client);
//		global.putAll(request);
//
//
//
//		Map<String, Object> objParams = new HashMap<>();
//		objParams.put("request", global);
//
//
//		CompiledExpression expression = new ExpressionCompiler("return request;").compile();
//		System.out.println(MVEL.executeExpression(expression, objParams));
//		System.out.println(MVEL.eval("$a > 0 && $b > 0 && $map.?dog == \"DOG\" ", params));

		 SpringApplication.run(FtApplication.class, args);

	}

}
