package com.spiritlight.messagetriggers.core;

import com.spiritlight.messagetriggers.utils.game.Message;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MessageListener {
    public static final MessageListener instance = new MessageListener();

    private MessageListener() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void ensureInitialized() {}

    @SubscribeEvent
    public void onMessage(ClientChatReceivedEvent event) {
        if(TriggerFunctionManager.empty()) return;
        Message.info("Found ClientChatReceivedEvent type " + event.getType() +", message " + event.getMessage().getUnformattedText());
        String content = event.getMessage().getUnformattedText();

        TriggerFunctionManager.trigger(content);
    }
}
