package com.spiritlight.messagetriggers.objects.elements.data;

import com.spiritlight.messagetriggers.collections.MapPair;

public final class IntegerElement extends NumericElement {

    public static final String VALUE = "value";

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public IntegerElement(String name, String value) {
        super(name, MapPair.of(VALUE, value));
        Integer.parseInt(value);
    }

    /**
     * Creates an anonymous element with given value
     * @param value The string value to be parsed as an integer
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public IntegerElement(String value) {
        super("", MapPair.of(VALUE, value));
        Integer.parseInt(value);
    }

    public IntegerElement(int value) {
        super("", MapPair.of(VALUE, String.valueOf(value)));
    }

    public static IntegerElement of(String value) {
        return new IntegerElement(value);
    }

    public static IntegerElement of(int value) {
        return new IntegerElement(value);
    }

    public int getValue() {
        return this.intValue();
    }

    @Override
    public String stringValue() {
        return String.valueOf(this.getValue());
    }

    @Override
    public String toString() {
        return "IntegerElement" + super.toString();
    }
}
