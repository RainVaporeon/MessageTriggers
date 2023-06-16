package com.spiritlight.messagetriggers.commands;

import com.spiritlight.messagetriggers.Main;
import com.spiritlight.messagetriggers.exceptions.functions.AcceptException;
import com.spiritlight.messagetriggers.exceptions.interpreter.InterpretException;
import com.spiritlight.messagetriggers.functions.Instructions;
import com.spiritlight.messagetriggers.functions.Interpreter;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.instructions.AbstractInstruction;
import com.spiritlight.messagetriggers.utils.Callback;
import com.spiritlight.messagetriggers.utils.game.Message;
import com.spiritlight.messagetriggers.utils.Result;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
@Command(name = "mt", permission = 0)
public class MainCommand extends AbstractCommand {

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        String value = String.join(" ", args);

        try {
            Callback<List<Element>> cb = Interpreter.instance.interpret(value);

            if(cb.getResult() == Result.FAIL) {
                Message.warn("Callback returned FAIL on parsing value `" + value + "`");
                return;
            }

            Main.function.accept(cb.get());
        } catch (InterpretException e) {
            Message.warn("Failed to interpret exception: " + e.getMessage());
        } catch (AcceptException e) {
            Message.warn("Failed to accept supplied instructions: " + e.getMessage());
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length == 0) return Collections.emptyList();
        String idx = args[args.length - 1];

        AbstractInstruction instruction = Instructions.instance.get(idx);

        if(instruction == null) {
            if(args.length == 1) return Collections.emptyList();
            String idx2 = args[args.length - 2];
            instruction = Instructions.instance.get(idx2);
            if(instruction == null) return Collections.emptyList();
            return Instructions.instance.getCompatibleNames(instruction.getClass())
                    .stream().filter(s -> s.startsWith(idx2)).collect(Collectors.toList());
        }

        return Instructions.instance.getCompatibleNames(instruction.getClass());
    }
}
