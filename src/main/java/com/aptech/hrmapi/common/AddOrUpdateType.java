package com.aptech.hrmapi.common;

public enum AddOrUpdateType {
    UPDATE(false), ADD(true);

    private final boolean type;

    private AddOrUpdateType(Boolean type) {
        this.type = type;
    }

    public static AddOrUpdateType getNameFromType(Boolean type) {
        if (type == null) {
            return null;
        } else if (type) {
            return ADD;
        }
        return UPDATE;
    }

    public static Boolean getValueFromName (AddOrUpdateType type){
        return type.getType();
    }

    public Boolean getType() {
        return type;
    }
}
