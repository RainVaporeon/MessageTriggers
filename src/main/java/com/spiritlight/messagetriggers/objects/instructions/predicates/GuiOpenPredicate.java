package com.spiritlight.messagetriggers.objects.instructions.predicates;

import com.spiritlight.messagetriggers.events.EventManager;
import com.spiritlight.messagetriggers.exceptions.functions.AcceptException;
import com.spiritlight.messagetriggers.functions.TriggerFunction;
import com.spiritlight.messagetriggers.objects.elements.data.DataElement;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.utils.ExceptionHandler;
import net.minecraftforge.client.event.GuiOpenEvent;

import java.util.List;

public class GuiOpenPredicate extends EventPredicate implements TriggerFunction {

    public GuiOpenPredicate() {
        super("event.GuiOpenEvent");
        EventManager.instance.addSubscriber(GuiOpenEvent.class, this);
    }

    @Override
    public void accept(List<Element> instructions) throws AcceptException {
        this.defaultAccept(instructions);
    }

    // This is not expected to be called
    @Override
    public boolean test(DataElement... args) {
        return true;
    }

    @Override
    public void onEvent() {
        this.queue.forEach((uuid, function) -> {
            try {
                function.getKey().accept(function.getValue());
                this.queue.remove(uuid);
            } catch (AcceptException e) {
                ExceptionHandler.handleException(e);
            }
        });
    }
}
