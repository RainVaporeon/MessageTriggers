package com.spiritlight.messagetriggers.exceptions.functions;

import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.elements.data.DataElement;
import com.spiritlight.messagetriggers.objects.instructions.AbstractInstruction;

/**
 * Thrown when parsing fails, or any of the instructions parsed with
 * {@link com.spiritlight.messagetriggers.functions.TriggerFunctionImpl#parseInstruction(AbstractInstruction, Element...)}
 * returned {@code false} (often due to failing to complete specified conditions)
 */
public class ParseFailureException extends AcceptException {
}
