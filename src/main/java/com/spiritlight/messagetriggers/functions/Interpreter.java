package com.spiritlight.messagetriggers.functions;

import com.spiritlight.messagetriggers.core.TriggerFunctionManager;
import com.spiritlight.messagetriggers.exceptions.interpreter.InterpretException;
import com.spiritlight.messagetriggers.exceptions.interpreter.InvalidPatternException;
import com.spiritlight.messagetriggers.exceptions.registrar.RegisterFailureException;
import com.spiritlight.messagetriggers.objects.elements.data.DoubleElement;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.elements.data.IntegerElement;
import com.spiritlight.messagetriggers.objects.elements.data.StringElement;
import com.spiritlight.messagetriggers.objects.instructions.AbstractInstruction;
import com.spiritlight.messagetriggers.objects.instructions.internal.AddInstruction;
import com.spiritlight.messagetriggers.objects.instructions.internal.RemoveInstruction;
import com.spiritlight.messagetriggers.utils.*;

import java.util.*;

public class Interpreter {

    public static final List<Element> SUCCESS = Collections.singletonList(StringElement.of("Operation successful."));

    public static final Interpreter instance = new Interpreter();

    public Callback<List<Element>> interpret(String s) throws InterpretException {
        String[] split = s.split(" ");

        return interpret(ArrayIterator.of(split));
    }

    public Callback<List<Element>> interpret(Iterable<String> collection) throws InterpretException {
        List<Element> result = new LinkedList<>();

        final StringBuilderController controller = new StringBuilderController();

        Iterator<String> it = collection.iterator();

        while (it.hasNext()) {
            String parameter = it.next();
            int op = this.parseString(parameter);
            switch(op) {
                case LITERAL:
                    result.add(this.parseElement(this.trimAsString(parameter)));
                    break;
                case START:
                    if(!controller.isEmpty()) controller.append(" ");
                    controller.append(parameter);
                    break;
                case END:
                    if(controller.isEmptyStrict()) throw InvalidPatternException.forInputString(parameter);
                    controller.append(" ");
                    controller.append(parameter);
                    result.add(this.trim(controller.toString()));
                    controller.clear();
                    break;
                case INSTRUCTION:
                    // Hold on a second, this may not yet be an instruction!
                    // A short-circuiting check, if building is in process, this must
                    // be treated as a literal to append until we receive the END instruction.
                    if(!controller.isEmpty()) {
                        controller.append(" ");
                        controller.append(parameter);
                        continue;
                    }
                    AbstractInstruction instruction = this.parseInstruction(parameter);

                    // If this instruction is annotated as a special one,
                    // we terminate the processing and pass all the remaining
                    // parameters to it.
                    if(instruction.isSpecial()) {
                        List<String> remaining = new LinkedList<>();
                        it.forEachRemaining(remaining::add);
                        Result res = processSpecial(instruction, remaining);
                        if(res == Result.FAIL) throw new InterpretException("Failed processing special instruction " + instruction + ": with remaining args" + remaining);
                        return Callback.of(SUCCESS);
                    }

                    // Now with that out of the way, we can actually parse the instructions!
                    result.add(this.parseInstruction(parameter));
                    break;
                default:
                    throw new InterpretException("Found unexpected opcode " + op);
            }
        }

        return Callback.of(result);
    }

    private static Result processSpecial(AbstractInstruction instruction, List<String> parameters) throws InterpretException {
        List<Element> parsed = Interpreter.instance.interpret(parameters).get();

        if(parsed.size() == 0) throw new InterpretException("A special instruction need at least 1 argument!");

        Iterator<Element> it = parsed.iterator();

        Element element = it.next();
        List<Element> remaining = new LinkedList<>();
        it.forEachRemaining(remaining::add);
        if(instruction instanceof AddInstruction) {
            try {
                StringElement phrase = (StringElement) element;
                return TriggerFunctionManager.register(TriggerFunction.of(phrase.getValue()), remaining).getResult();
            } catch (ClassCastException ex) {
              throw new InterpretException("expected a String");
            } catch (RegisterFailureException e) {
                ExceptionHandler.handleException(e);
                throw new InterpretException(e.getMessage());
            }
        }
        if(instruction instanceof RemoveInstruction) {
            try {
                TriggerFunctionManager.unregister(((StringElement) element).getValue());
            } catch (ClassCastException e) {
                throw new InterpretException("expected a String");
            }
            return Result.FAIL;
        }
        return Result.FAIL;
    }

    private AbstractInstruction parseInstruction(String s) throws InterpretException {
        AbstractInstruction ai = Instructions.instance.get(s);
        if(ai == null) throw new InterpretException("Cannot find instruction for input " + s);
        return ai;
    }

    /**
     * Tries to parse the element
     * @param s The input string
     * @return An {@link IntegerElement} if the string is a valid integer, or
     * a {@link DoubleElement} if the string can be parsed as a double, or
     * ultimately a {@link StringElement} if everything else fails.
     */
    private Element parseElement(String s) {
        // Parse integer first as a double may also represent an integer
        try {
            return IntegerElement.of(s);
        } catch (NumberFormatException ignored) {}
        try {
            return DoubleElement.of(s);
        } catch (NumberFormatException ignored) {
            return StringElement.of(s);
        }
    }

    private String trimAsString(String s) {
        return s.substring(1, s.length() - 1);
    }

    private Element trim(String s) {
        return StringElement.of(s.substring(1, s.length() - 1));
    }

    // Expected to only pass in one word, without space whatsoever
    public static final int LITERAL = 0; // String literal
    public static final int START = 1; // Start of a literal, seek next
    public static final int END = 2; // End of a literal
    // Indicates that this is an instruction and should be parsed accordingly
    public static final int INSTRUCTION = 3;

    /**
     * Parses the string. The supplied string must not contain any space
     * characters.
     * @param singleton The singleton string
     * @return
     * {@link Interpreter#LITERAL} if the string is wrapped by double quotes
     * {@link Interpreter#START} if the string starts with a double quote
     * {@link Interpreter#END} if the string ends with a double quote
     * {@link Interpreter#INSTRUCTION} if none of the above matches
     * @apiNote
     * Literal should be treated as a string literal, while start should be
     * appending to a builder regardless, and end should be dumping the builder
     * content after appending itself. If builder is empty, an {@link InvalidPatternException}
     * should be thrown to indicate faulty parsing.
     * If instruction is received, this indicates that parsing instructions is required.
     */
    private int parseString(String singleton) {
        if(singleton.contains(" ")) throw new IllegalArgumentException("Cannot contain spaces");
        boolean start = singleton.startsWith("\"");
        boolean end = singleton.endsWith("\"");
        if(start && end) return LITERAL;
        if(start) return START;
        if(end) return END;
        return INSTRUCTION;
    }
}
