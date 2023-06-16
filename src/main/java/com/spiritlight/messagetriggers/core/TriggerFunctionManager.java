package com.spiritlight.messagetriggers.core;

import com.spiritlight.messagetriggers.collections.ConcurrentTimedSet;
import com.spiritlight.messagetriggers.exceptions.functions.AcceptException;
import com.spiritlight.messagetriggers.exceptions.interpreter.InterpretException;
import com.spiritlight.messagetriggers.exceptions.registrar.RegisterFailureException;
import com.spiritlight.messagetriggers.functions.Interpreter;
import com.spiritlight.messagetriggers.functions.TriggerFunction;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.utils.Callback;
import com.spiritlight.messagetriggers.utils.ExceptionHandler;
import com.spiritlight.messagetriggers.utils.Result;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public final class TriggerFunctionManager {

    private static final Map<TriggerFunction, List<Element>> functionMap = new ConcurrentHashMap<>();

    public static boolean empty() {
        return functionMap.isEmpty();
    }

    // you can trigger an infinite recursion if you do it correctly
    private static final Set<String> antiRecursion = new ConcurrentTimedSet<>(500, TimeUnit.MILLISECONDS);

    public static void printMap() {
        System.out.println(antiRecursion);
    }

    public static void trigger(String phrase) {
        if (!containsPhrase(phrase)) {
            System.out.println("Phrase " + phrase + " does not exist");
            return;
        }
        // returns if and only if a phrase already exists
        if(!antiRecursion.add(phrase)) {
            System.out.println("Phrase " + phrase + " exists");
            return;
        }

        for (Map.Entry<TriggerFunction, List<Element>> entry : functionMap.entrySet()) {
            TriggerFunction function = entry.getKey();
            if (function.getKeywordPattern().matcher(phrase).find()) {
                try {
                    function.accept(entry.getValue());
                } catch (AcceptException e) {
                    ExceptionHandler.handleException(e);
                }
            }
        }
    }

    public static Callback<String> register(TriggerFunction function, String instruction) throws RegisterFailureException {
        if (containsPhrase(function.getKeyword()))
            throw new RegisterFailureException("The phrase " + function.getKeyword() + " is already registered!");

        try {
            Callback<List<Element>> element = Interpreter.instance.interpret(instruction);
            if(element.getResult() == Result.SUCCESS) {
                return Callback.of("Operation successful.");
            }
            functionMap.put(function, element.get());
        } catch (InterpretException e) {
            e.printStackTrace();
            throw new RegisterFailureException("Cannot interpret instruction " + instruction + ": " + e.getMessage());
        }
        return Callback.of("Operation successful.");
    }

    public static Callback<String> register(TriggerFunction function, List<Element> instruction) throws RegisterFailureException {
        if (containsPhrase(function.getKeyword()))
            throw new RegisterFailureException("The phrase " + function.getKeyword() + " is already registered!");

        functionMap.put(function, instruction);
        return Callback.of("Operation successful.");
    }

    public static void unregister(String phrase) {
        functionMap.entrySet().removeIf(key -> key.getKey().getKeyword().equals(phrase));
    }

    private static boolean containsPhrase(String ph) {
        return functionMap.keySet().stream()
                .map(TriggerFunction::getKeywordPattern)
                .anyMatch(p -> p.matcher(ph).find());

    }
}
