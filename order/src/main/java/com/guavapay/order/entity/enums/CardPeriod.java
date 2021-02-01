package com.guavapay.order.entity.enums;

public enum CardPeriod {
    YEAR(12,"12"),
    TWO_YEARS(24,"24"),
    THREE_YEARS(36,"36");

    private final int intValue;
    private final String stringValue;

    CardPeriod(final int newIntValue, final String newStringValue) {
        intValue = newIntValue;
        stringValue = newStringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static CardPeriod fromInteger(int x) {
        switch (x) {
            case 12:
                return YEAR;
            case 24:
                return TWO_YEARS;
            case 36:
                return THREE_YEARS;
        }
        return null;
    }
}
