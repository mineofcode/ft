package com.ftteporal.ft.enums;

public enum EWorkFlowItemType {
    Action ("Action"),
    Sleep ("Sleep");

    private final String name;

    private EWorkFlowItemType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}
