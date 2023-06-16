package com.spiritlight.messagetriggers.test;

import com.spiritlight.messagetriggers.core.TriggerFunctionManager;

public class FakeTrigger {

    public static void sendTrigger(String phrase) {
        TriggerFunctionManager.trigger(phrase);
    }
}
