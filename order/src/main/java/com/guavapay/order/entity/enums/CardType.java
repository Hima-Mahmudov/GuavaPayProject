package com.guavapay.order.entity.enums;

public enum CardType {
    MC(0,"MC"),
    VISA(1,"VISA"),
    UNION_PAY(2,"UNION_PAY");

    private final int intValue;
    private final String stringValue;

    CardType(final int newIntValue, final String newStringValue) {
        intValue = newIntValue;
        stringValue = newStringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static CardType fromInteger(int x) {
        switch (x) {
            case 0:
                return MC;
            case 1:
                return VISA;
            case 2:
                return UNION_PAY;
        }
        return null;
    }
}
