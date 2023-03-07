package com.ftteporal.ft.activities;
import com.fasterxml.jackson.databind.JsonNode;
import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.Customer;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface DslActivities {
    ActResult checkCustomerInfo(JsonNode customer);

    ActResult approveApplication(JsonNode customer);

    ActResult rejectApplication(JsonNode customer);

    ActResult updateApplicationInfo(JsonNode customer);

    ActResult invokeBankingService(JsonNode customer);
}