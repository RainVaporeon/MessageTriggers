package com.spiritlight.messagetriggers;

import com.spiritlight.messagetriggers.commands.MainCommand;
import com.spiritlight.messagetriggers.functions.TriggerFunction;
import com.spiritlight.messagetriggers.internal.Core;
import com.spiritlight.messagetriggers.test.Test;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main
{
    public static final String MODID = "messagetriggers";
    public static final String NAME = "MessageTriggers";
    public static final String VERSION = "1.0";

    public static final TriggerFunction function = TriggerFunction.of("main");
    

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Core.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        ClientCommandHandler.instance.registerCommand(new MainCommand());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
