package com.ftteporal.ft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ftteporal.ft.enums.EActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class ActionDataModel {
    public String id;
    public HashMap<String,Object> global;
    public HashMap<String,Object> data;
    public HashMap<String,Object> persistData;
    private String workflow;
    private HashMap<String, ObjectNode> results;

}
