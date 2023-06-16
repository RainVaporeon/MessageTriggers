package com.spiritlight.messagetriggers.objects.elements.data;

import com.spiritlight.messagetriggers.collections.MapPair;

public abstract class NumericElement extends DataElement {
    public static final String VALUE = "value";

    private static final String EXCEPTION = "unexpected exception occurred: ";

    @SafeVarargs
    public NumericElement(String name, MapPair<String, String>... mapping) {
        super(name, mapping);
    }

    public long longValue() {
        try {
            return Long.parseLong(this.getMappedVariable(VALUE));
        } catch (NumberFormatException e) {
            throw new IllegalStateException(EXCEPTION, e);
        }
    }

    public int intValue() {
        try {
            return Integer.parseInt(this.getMappedVariable(VALUE));
        } catch (NumberFormatException e) {
            throw new IllegalStateException(EXCEPTION, e);
        }
    }

    public double doubleValue() {
        try {
            return Double.parseDouble(this.getMappedVariable(VALUE));
        } catch (NumberFormatException e) {
            throw new IllegalStateException(EXCEPTION, e);
        }
    }
}
