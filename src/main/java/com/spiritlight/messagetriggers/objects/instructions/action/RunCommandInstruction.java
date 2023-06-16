package com.spiritlight.messagetriggers.objects.instructions.action;

import com.spiritlight.messagetriggers.collections.MapPair;
import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.elements.data.DataElement;
import com.spiritlight.messagetriggers.objects.instructions.StatefulInstruction;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;

import java.util.Collections;
import java.util.Set;

public class RunCommandInstruction extends StatefulInstruction {
    public RunCommandInstruction() {
        super("run-command", 1);
    }

    @Override
    public Set<Class<? extends Element>> compatibleWith() {
        return Collections.singleton(DataElement.class);
    }

    @Override
    public boolean accept(Element... parameters) throws ArgumentMismatchException {
        Element e = parameters[0];
        if(!(e instanceof DataElement)) throw new ArgumentMismatchException("Expected DataElement, got " + e);
        DataElement element = (DataElement) e;
        if(Minecraft.getMinecraft().player == null) return false;
        int i = ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().player, element.stringValue());
        if(i != 1) {
            if(element.stringValue().startsWith("/")) {
                Minecraft.getMinecraft().player.sendChatMessage(element.stringValue());
            } else {
                Minecraft.getMinecraft().player.sendChatMessage("/" + element.stringValue());
            }
        }
        return true;
    }
}
