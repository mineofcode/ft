package com.ftteporal.ft.activities;

import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.ActionDataModel;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface()
public interface TestActivities {

    @ActivityMethod(name = "Create_Lead")
    ActResult createCRMLead(ActionDataModel input);

    @ActivityMethod(name = "Update_Lead")
    ActResult updateCRMLead(ActionDataModel input);

    @ActivityMethod(name = "Bureau")
    ActResult bureauCall(ActionDataModel input) ;

    @ActivityMethod(name = "BRE")
    ActResult breCall(ActionDataModel input) ;


    @ActivityMethod(name = "Hunter")
    ActResult hunterCall(ActionDataModel input) ;

    @ActivityMethod(name = "Omni")
    ActResult omnidocsCall(ActionDataModel input)  ;

}
