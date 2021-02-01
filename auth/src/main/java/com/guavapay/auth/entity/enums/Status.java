package com.guavapay.auth.entity.enums;

public enum Status {
    ACTIVE(0,"ACTIVE"),
    NOT_ACTIVE(1,"NOT_ACTIVE"),
    DELETED(2,"DELETED");

    private final int intValue;
    private final String stringValue;

    Status(final int newIntValue, final String newStringValue) {
        intValue = newIntValue;
        stringValue = newStringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static Status fromInteger(int x) {
        switch (x) {
            case 0:
                return ACTIVE;
            case 1:
                return NOT_ACTIVE;
            case 2:
                return DELETED;
        }
        return null;
    }
}