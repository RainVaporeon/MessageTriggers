package com.spiritlight.messagetriggers.objects.elements.data;

import com.spiritlight.messagetriggers.collections.MapPair;

/**
 * Represents a singleton string element
 */
public final class StringElement extends DataElement {

    public static final String VALUE = "value";

    public StringElement(String name, String value) {
        super(name, MapPair.of(VALUE, value));
    }

    /**
     * Creates an anonymous element with given value
     * @param value The string value to supply
     */
    public StringElement(String value) {
        super("", MapPair.of(VALUE, value));
    }

    public static StringElement of(String value) {
        return new StringElement(value);
    }

    public String getValue() {
        return this.getMappedVariable(VALUE);
    }

    @Override
    public String stringValue() {
        return this.getValue();
    }

    @Override
    public String toString() {
        return "StringElement" + super.toString();
    }
}
