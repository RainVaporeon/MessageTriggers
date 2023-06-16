package com.spiritlight.messagetriggers.functions;

import com.spiritlight.messagetriggers.exceptions.functions.AcceptException;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.instructions.AbstractInstruction;

import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TriggerFunctionImpl implements TriggerFunction {

    private final String keyword;
    private final Pattern pattern;
    private final BiFunction<AbstractInstruction, Element[], Boolean> function;

    /**
     * @param keyword The keyword to trigger this function
     * @param function The function to process other instructions that may be otherwise
     *                 not included or created by others.
     * @apiNote The function, by contract, should always return a non-null value.
     * If a null value was passed, an {@link NullPointerException} will be thrown.
     */
    public TriggerFunctionImpl(String keyword, BiFunction<AbstractInstruction, Element[], Boolean> function) {
        this.keyword = keyword;
        this.pattern = safePatternParse(keyword);
        this.function = function;
    }

    public TriggerFunctionImpl(String keyword) {
        this.keyword = keyword;
        this.pattern = safePatternParse(keyword);
        this.function = null;
    }

    static Pattern safePatternParse(String pattern) {
        try {
            return Pattern.compile(pattern);
        } catch (PatternSyntaxException ex) {
            return Pattern.compile(pattern, Pattern.LITERAL);
        }
    }

    @Override
    public Pattern getKeywordPattern() {
        return pattern;
    }

    @Override
    public BiFunction<AbstractInstruction, Element[], Boolean> function() {
        return this.function;
    }

    @Override
    public String getKeyword() {
        return keyword;
    }

    @Override
    public void accept(List<Element> instructions) throws AcceptException {
        this.defaultAccept(instructions);
    }
}
