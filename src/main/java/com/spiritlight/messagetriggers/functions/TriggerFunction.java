package com.spiritlight.messagetriggers.functions;

import com.spiritlight.messagetriggers.exceptions.events.EventListening;
import com.spiritlight.messagetriggers.exceptions.events.PredicateListening;
import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.exceptions.functions.AcceptException;
import com.spiritlight.messagetriggers.exceptions.instructions.ExecutionException;
import com.spiritlight.messagetriggers.exceptions.instructions.InstructionNotFoundException;
import com.spiritlight.messagetriggers.exceptions.interpreter.ParameterMismatchException;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.elements.data.DataElement;
import com.spiritlight.messagetriggers.objects.instructions.AbstractInstruction;
import com.spiritlight.messagetriggers.objects.instructions.StatefulInstruction;
import com.spiritlight.messagetriggers.objects.instructions.StatelessInstruction;
import com.spiritlight.messagetriggers.objects.instructions.predicates.GamePredicate;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

/**
 * A base structure for a "Trigger Function", which accepts
 * a list of {@link Element}s and executes action. If one or more instructions
 * threw an unhandled exception, then {@link AcceptException} is thrown as a result.
 * <br>
 * This interface also provides several methods for convenience's sake,
 * and allows extension if the implementer provides BiFunction to {@link TriggerFunction#function()}
 * which allows parsing of other non-native instructions.
 */
public interface TriggerFunction {

    /**
     * Accepts a list of instructions to be executed
     * @param instructions Instructions to apply
     * @throws AcceptException if one or more instructions failed
     * @see TriggerFunction#defaultAccept(List)
     */
    void accept(List<Element> instructions) throws AcceptException;

    String getKeyword();

    default Pattern getKeywordPattern() {
        return Pattern.compile(this.getKeyword());
    }

    /**
     * Acquires a special processing function, useful if more instructions
     * were added, but is not internally parsed.
     * @return The function mapping, or null if none present.
     */
    default BiFunction<AbstractInstruction, Element[], Boolean> function() {
        return null;
    }

    /**
     * Default implementation of the accept function, this is the base
     * struct for parsing a sequence of instructions. Often, this method
     * is already ideal for parsing the input sequence, though if needed,
     * implementers may override {@link TriggerFunction#accept(List)} to
     * change it (though it's also possible to change this method, however
     * it's less favored.)
     * @param instructions Instructions to take
     * @throws AcceptException if any issues occurred during accepting
     */
    default void defaultAccept(List<Element> instructions) throws AcceptException {
        Iterator<Element> iterator = instructions.iterator();
        try {
            while (iterator.hasNext()) {
                Element next = iterator.next();

                if(next instanceof AbstractInstruction) {
                    AbstractInstruction instruction = (AbstractInstruction) next;
                    Element[] elements = new Element[instruction.getParameters()];
                    for(int i = 0; i < elements.length; i++) {
                        try {
                            elements[i] = iterator.next();
                        } catch (NoSuchElementException ex) {
                            throw new ParameterMismatchException("Failed to create at instruction " + instruction + ": Insufficient argument was supplied");
                        }
                    }
                    boolean success = parseInstruction(instruction, elements);
                    if(!success) throw new AcceptException("Creation failed at instruction " + instruction + " with elements " + Arrays.toString(elements));
                }
            }
        } catch (PredicateListening | EventListening listener) {
            // Special indicator to imply that one or more listener is present
            // and is listening to an event

            List<Element> remaining = new LinkedList<>();
            iterator.forEachRemaining(remaining::add);
            if(listener instanceof EventListening) {
                ((EventListening) listener).getPredicate().register(this, remaining);
            } else {
                ((PredicateListening) listener).getPredicate().register(this, remaining);
            }
        } catch (Throwable t) {
            throw new AcceptException(t);
        }
    }

    /**
     * Parses the given instruction
     * @param instruction The instruction to pass the parameters to
     * @param parameters The parameters to be passed into the instruction
     * @return {@code true} if parsing succeeded, false otherwise
     * @throws AcceptException if parsing failed by any sort of exceptions
     */
    default boolean parseInstruction(AbstractInstruction instruction, Element... parameters) throws AcceptException, InstructionNotFoundException, EventListening {
        int expectedParams = instruction.getParameters();
        int actualParams = parameters.length;
        if(expectedParams != actualParams) throw new ArgumentMismatchException(expectedParams, actualParams);
        if(instruction instanceof StatefulInstruction) {
            boolean success = ((StatefulInstruction) instruction).accept(parameters);
            if(!success) throw new AcceptException("while processing " + instruction + " with parameters " + Arrays.toString(parameters));
            return true;
        }
        if(instruction instanceof GamePredicate) {
            // Safely cast for now.
            return ((GamePredicate) instruction).test((DataElement[]) parameters);
        }
        if(instruction instanceof StatelessInstruction) {
            try {
                ((StatelessInstruction) instruction).run();
            } catch (ExecutionException e) {
                throw new AcceptException(e);
            }
            return true;
        }
        if(this.function() != null) {
            return this.function().apply(instruction, parameters);
        }
        throw new InstructionNotFoundException(instruction);
    }

    static TriggerFunction of(String name) {
        return new TriggerFunctionImpl(name);
    }

    static TriggerFunction of(String name, BiFunction<AbstractInstruction, Element[], Boolean> function) {
        return new TriggerFunctionImpl(name, function);
    }
}
