package com.spiritlight.messagetriggers.objects.elements.data;

import com.spiritlight.messagetriggers.collections.MapPair;
import com.spiritlight.messagetriggers.objects.elements.Element;

/**
 *
 */
public abstract class DataElement extends Element {
    @SafeVarargs
    public DataElement(String name, MapPair<String, String>... mapping) {
        super(name, mapping);
    }

    public abstract String stringValue();
}
