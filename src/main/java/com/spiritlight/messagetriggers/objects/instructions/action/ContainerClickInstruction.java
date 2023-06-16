package com.spiritlight.messagetriggers.objects.instructions.action;

import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.exceptions.instructions.ExecutionException;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.elements.data.IntegerElement;
import com.spiritlight.messagetriggers.objects.instructions.StatefulInstruction;
import com.spiritlight.messagetriggers.objects.instructions.StatelessInstruction;
import com.spiritlight.messagetriggers.utils.ExceptionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;

import java.util.Collections;
import java.util.Set;

public class ContainerClickInstruction extends StatefulInstruction {

    public ContainerClickInstruction() {
        super("container.clickSlot", 1);
    }

    @Override
    public Set<Class<? extends Element>> compatibleWith() {
        return Collections.singleton(IntegerElement.class);
    }

    @Override
    public boolean accept(Element... parameters) throws ArgumentMismatchException {
        if(!compatibleWith(parameters[0])) throw new ArgumentMismatchException("Expected an integer, got element " + parameters[0]);
        if(!(Minecraft.getMinecraft().currentScreen instanceof GuiContainer)) return false;
        GuiContainer container = (GuiContainer) Minecraft.getMinecraft().currentScreen;

        try {
            Minecraft.getMinecraft().playerController.windowClick(
                    container.inventorySlots.windowId,
                    ((IntegerElement) parameters[0]).getValue(),
                    0, ClickType.PICKUP,
                    Minecraft.getMinecraft().player
            );
            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
            return false;
        }
    }
}
