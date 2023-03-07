package com.ftteporal.ft.activities;

import com.fasterxml.jackson.databind.JsonNode;
import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.Customer;
import io.temporal.activity.Activity;

public class DslActivitiesImpl implements DslActivities {
    @Override
    public ActResult checkCustomerInfo(JsonNode customer) {
        try {
            return new ActResult(Activity.getExecutionContext().getInfo().getActivityType(), null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ActResult updateApplicationInfo(JsonNode customer) {
        try {
            return new ActResult(Activity.getExecutionContext().getInfo().getActivityType(), null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ActResult approveApplication(JsonNode customer) {
        try {
            return new ActResult("decision", null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ActResult rejectApplication(JsonNode customer) {
        try {
            return new ActResult("decision-" + customer.get("id"), null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ActResult invokeBankingService(JsonNode customer) {
        try {
            return new ActResult(Activity.getExecutionContext().getInfo().getActivityType(), null);
        } catch (Exception e) {
            return null;
        }
    }
}