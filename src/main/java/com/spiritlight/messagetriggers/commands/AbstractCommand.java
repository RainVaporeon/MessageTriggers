package com.spiritlight.messagetriggers.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

public abstract class AbstractCommand extends CommandBase implements IClientCommand {
    private final String name;
    private final int permission;
    private final boolean requirePrefix;

    public AbstractCommand() {
        if(!this.getClass().isAnnotationPresent(Command.class)) {
            throw new IllegalStateException("This class needs to be annotated with @Command!");
        }
        Command command = this.getClass().getAnnotation(Command.class);
        this.name = command.name();
        this.permission = command.permission();
        this.requirePrefix = command.requirePrefix();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + name;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return permission;
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return !requirePrefix;
    }
}
