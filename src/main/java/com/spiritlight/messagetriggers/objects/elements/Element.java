package com.spiritlight.messagetriggers.objects.elements;

import com.spiritlight.messagetriggers.collections.ImmutableMap;
import com.spiritlight.messagetriggers.collections.MapPair;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * The base class of all elements.<br><br>
 * An element represents a sort of input, and is used for supplying
 * instructions with certain arguments.
 */
public abstract class Element {
    /**
     * The name of the element, sometimes useful to fetch
     */
    protected final String name;

    /**
     * The variable mapping. A variable may only point to one exact result,
     * @apiNote Since the variable map may vary per implementation, it's
     * a good idea to provide fields to allow the users to see what valid variables
     * they are actually able to choose.
     */
    private final Map<String, String> variableMap;

    @SafeVarargs
    public Element(String name, MapPair<String, String>... mapping) {
        this.name = name;
        this.variableMap = new HashMap<>();
        for(MapPair<String, String> pair : mapping) {
            this.variableMap.put(pair.getKey(), pair.getValue());
        }
    }

    public String getName() {
        return name;
    }

    public String getMappedVariable(String in) {
        return variableMap.get(in);
    }

    public Set<String> getVariableSet() {
        return variableMap.keySet();
    }

    protected void addVariable(String key, String value) {
        this.variableMap.put(key, value);
    }

    protected void removeVariable(String key) {
        this.variableMap.remove(key);
    }

    protected Set<Map.Entry<String, String>> entrySet() {
        return this.variableMap.entrySet();
    }

    @Override
    public String toString() {
        return variableMap.toString();
    }
}
