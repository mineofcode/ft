package com.ftteporal.ft.model;

import com.ftteporal.ft.enums.EWorkFlowItemType;
import lombok.Data;

@Data
public class WorkFlowItem {
    private String action;
    private String input;
    private String output;
    private String value;
    private String type;
    private String expression;
    private String uniqId;
    private boolean isCompleted;
}
