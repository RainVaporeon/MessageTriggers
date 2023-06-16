package com.spiritlight.messagetriggers.objects.instructions.predicates;

import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.objects.elements.data.DataElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

public class ContainerHasItemPredicate extends GamePredicate {

    public ContainerHasItemPredicate() {
        super("container.hasItem");
    }

    @Override
    public boolean test(DataElement... args) throws ArgumentMismatchException {
        if(!(Minecraft.getMinecraft().currentScreen instanceof GuiContainer)) return false;
        GuiContainer container = (GuiContainer) Minecraft.getMinecraft().currentScreen;
        return container.inventorySlots.inventorySlots.stream().anyMatch(Slot::getHasStack);
    }
}
