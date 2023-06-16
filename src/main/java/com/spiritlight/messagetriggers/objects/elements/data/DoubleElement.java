package com.spiritlight.messagetriggers.objects.elements.data;

import com.spiritlight.messagetriggers.collections.MapPair;

public final class DoubleElement extends NumericElement {

    public static final String VALUE = "value";

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public DoubleElement(String name, String value) {
        super(name, MapPair.of(VALUE, value));
        Double.parseDouble(value);
    }

    /**
     * Creates an anonymous element with given value
     * @param value The string value to be parsed as an integer
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public DoubleElement(String value) {
        super("", MapPair.of(VALUE, value));
        Double.parseDouble(value);
    }

    public DoubleElement(double value) {
        super("", MapPair.of(VALUE, String.valueOf(value)));
    }

    public static DoubleElement of(String value) {
        return new DoubleElement(value);
    }

    public static DoubleElement of(double value) {
        return new DoubleElement(value);
    }

    public double getValue() {
        return this.doubleValue();
    }

    @Override
    public String stringValue() {
        return String.valueOf(this.getValue());
    }

    @Override
    public String toString() {
        return "DoubleElement" + super.toString();
    }
}
