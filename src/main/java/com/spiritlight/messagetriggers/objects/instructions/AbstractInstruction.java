package com.spiritlight.messagetriggers.objects.instructions;

import com.spiritlight.messagetriggers.collections.MapPair;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.elements.data.DataElement;
import com.spiritlight.messagetriggers.objects.instructions.annotations.SpecialInstruction;

import java.util.Set;

public abstract class AbstractInstruction extends Element {
    protected final int parameters;

    @SafeVarargs
    public AbstractInstruction(String name, int parameters, MapPair<String, String>... mapping) {
        super(name, mapping);
        this.parameters = parameters;
    }

    @SafeVarargs
    public AbstractInstruction(String name, MapPair<String, String>... mapping) {
        super(name, mapping);
        this.parameters = 0;
    }

    /**
     * Gets the parameter count. The trigger function interpreter will
     * intake this many arguments regardless whether the instruction is stateful or stateless.
     * <p>
     * If the instruction is stateful, and let {@code x} be the parameters returned,
     * then {@link StatefulInstruction#accept(Element...)} will be invoked
     * with the next {@code x} elements provided.
     * @return The parameters to be consumed
     */
    public int getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return name + ":" + getClass().getCanonicalName();
    }

    public final boolean isSpecial() {
        return this.getClass().isAnnotationPresent(SpecialInstruction.class);
    }

    /**
     * Finds the elements that this instruction is compatible with.<br>
     * This is usually used for auto-completion on input
     * @return A set of elements that this instruction can use, or {@code null} for none
     * @apiNote This method is optionally called, and if the instruction accepts any sort
     * of object, return {@link Element}, for instructions, {@link AbstractInstruction}.
     */
    public abstract Set<Class<? extends Element>> compatibleWith();

    /**
     * Checks whether this given object is compatible with
     * this instruction
     * @param object The object to check
     * @return Whether this object is applicable for this instruction
     */
    public boolean compatibleWith(Object object) {
        return this.compatibleWith(object.getClass());
    }

    public boolean compatibleWith(Class<?> clazz) {
        for(Class<?> cls : this.compatibleWith()) {
            if(cls.isAssignableFrom(clazz)) return true;
        }
        return false;
    }
}
