package com.ftteporal.ft.services;

import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.ActionDataModel;

import java.net.URISyntaxException;

public interface IAction {
    ActResult execute(ActionDataModel input) throws URISyntaxException;


}
