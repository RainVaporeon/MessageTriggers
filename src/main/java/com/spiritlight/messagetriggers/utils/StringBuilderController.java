package com.spiritlight.messagetriggers.utils;

public class StringBuilderController {
    private StringBuilder builder;

    public StringBuilderController() {
        builder = new StringBuilder();
    }

    public StringBuilderController(String content) {
        builder = new StringBuilder(content);
    }

    public boolean isEmpty() {
        return builder.length() == 0;
    }

    public boolean isEmptyStrict() {
        return builder.toString().isEmpty();
    }

    /**
     * Gets the underlying builder for more direct access
     */
    public StringBuilder getBuilder() {
        return builder;
    }

    /**
     * Appends a string into the builder
     * @param s The string
     */
    public void append(String s) {
        builder.append(s);
    }

    /**
     * Clears the builder, this replaces the old builder
     * with a new one.
     */
    public void clear() {
        builder = new StringBuilder();
    }

    /**
     * Acquires the string this builder has
     * @return The built string from the underlying StringBuilder
     */
    public String getString() {
        return builder.toString();
    }

    @Override
    public String toString() {
        return this.getString();
    }
}
