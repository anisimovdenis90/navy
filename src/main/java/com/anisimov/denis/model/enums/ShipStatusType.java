package com.anisimov.denis.model.enums;

public enum ShipStatusType {
    PORT, SEA;

    public static ShipStatusType getStatusTyoe(String statusInString) {
        for (ShipStatusType type : ShipStatusType.values()) {
            if (type.name().equals(statusInString)) {
                return type;
            }
        }
        return null;
    }
}
