package com.ftteporal.ft.services;

import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.ActionDataModel;
import org.springframework.stereotype.Service;

@Service("HunterAction")
public class HunterActionService implements IAction{
    @Override
    public ActResult execute(ActionDataModel input) {
        return null;
    }
}
