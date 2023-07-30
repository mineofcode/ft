package com.ftteporal.ft.activities;

import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.ActionDataModel;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface()
public interface TestActivitiesSignal {

    @ActivityMethod(name = "initiate")
    ActResult initiateActivity() ;
    @ActivityMethod(name = "Create_Lead")
    ActResult createCRMLead(String input);

    @ActivityMethod(name = "Update_Lead")
    ActResult updateCRMLead(String input);

    @ActivityMethod(name = "Bureau")
    ActResult bureauCall(String input) ;


}
