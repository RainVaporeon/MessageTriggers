package com.spiritlight.messagetriggers.internal;

import com.spiritlight.messagetriggers.core.MessageListener;
import com.spiritlight.messagetriggers.functions.Instructions;
import com.spiritlight.messagetriggers.objects.instructions.action.*;
import com.spiritlight.messagetriggers.objects.instructions.internal.AddInstruction;
import com.spiritlight.messagetriggers.objects.instructions.internal.RemoveInstruction;
import com.spiritlight.messagetriggers.objects.instructions.predicates.ContainerHasItemPredicate;
import com.spiritlight.messagetriggers.objects.instructions.predicates.GuiOpenPredicate;
import com.spiritlight.messagetriggers.test.StatefulTest;
import com.spiritlight.messagetriggers.test.StatelessTest;
import com.spiritlight.messagetriggers.test.TestInstruction;
import com.spiritlight.messagetriggers.test.TestPredicate;
import com.spiritlight.messagetriggers.utils.ExceptionHandler;
import com.spiritlight.messagetriggers.utils.game.Message;

public final class Core {

    @OneShotOperation
    public static void init() {
        assertState(InitState.INIT);

        // init here, if any is present
        ExceptionHandler.addHandler(Throwable::printStackTrace);

        ExceptionHandler.addHandler(throwable -> {
            Message.warn("An error had occurred: " + throwable.getMessage());
        });

        MessageListener.ensureInitialized();

        InitState.advanceState();

        registerInstructions();

        InitState.advanceState();

        finish();
    }


    private static void registerInstructions() {
        assertState(InitState.INSTRUCTION_REGISTER);

        Instructions.instance.register(
                /* Internal uses or has special properties */
                new AddInstruction(),
                new RemoveInstruction(),
                new StatefulTest(),
                new StatelessTest(),
                new TestPredicate(),
                new TestInstruction(),

                /* External uses, keywords */
                new AwaitInstruction(),
                new WhenInstruction(),
                new ContainerClickInstruction(),
                new RunCommandInstruction(),
                new SendMessageInstruction(),

                /* External uses, predicates */
                new GuiOpenPredicate(),
                new ContainerHasItemPredicate()
        );
    }

    private static void finish() {
        assertState(InitState.END);
    }

    private static void assertState(int state) {
        if(InitState.state != state) throw new IllegalStateException();
    }


    private static class InitState {
        private static int state = 0;

        private static final int INIT = 0,
                                 INSTRUCTION_REGISTER = 1,
                                 END = 2;
        private InitState() {

        }

        private static void advanceState() {
            checkState();
            state++;
        }

        private static void checkState() {
            if(state < INIT || state >= END) throw new AssertionError();
        }
    }
}
