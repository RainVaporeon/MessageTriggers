package com.spiritlight.messagetriggers.utils.game;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class Message {

    public static void warn(String content) {
        send(new TextComponentString(TextFormatting.RED + content));
    }

    public static void success(String content) {
        send(new TextComponentString(TextFormatting.GREEN + content));
    }

    private static void send(ITextComponent component) {
        if(Minecraft.getMinecraft().player == null) {
            System.out.println("Player is null, ignoring message");
            System.out.println("Discarded content: " + component.getUnformattedText());
            return;
        }
        Minecraft.getMinecraft().player.sendMessage(component);
    }
}
