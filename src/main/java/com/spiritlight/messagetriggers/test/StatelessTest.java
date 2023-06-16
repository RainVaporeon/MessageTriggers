package com.spiritlight.messagetriggers.test;

import com.spiritlight.messagetriggers.objects.instructions.StatelessInstruction;

public class StatelessTest extends StatelessInstruction {

    public StatelessTest() {
        super("STLTest", 0);
    }

    @Override
    public void run() {
        System.out.println("StatelessTest ran");
    }
}
