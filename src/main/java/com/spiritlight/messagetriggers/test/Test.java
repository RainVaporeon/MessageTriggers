package com.spiritlight.messagetriggers.test;

import com.spiritlight.messagetriggers.core.TriggerFunctionManager;
import com.spiritlight.messagetriggers.functions.Interpreter;
import com.spiritlight.messagetriggers.functions.TriggerFunction;
import com.spiritlight.messagetriggers.internal.Core;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.elements.data.DoubleElement;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        try {
            // init first, **REMOVE THIS LINE ON PRODUCTION!!!**
            Core.init();

            // Test case: String literal, Literal with begin-end
            System.out.println(Interpreter.instance.interpret("\"Example\" \"Object-Oriented Programming\""));
            // Test case: Literal with begin-end, Literal with begin-end, with something in the middle
            System.out.println(Interpreter.instance.interpret("\"Example 2\" \"This is fine\""));
            // Test case: Double, Integer and String element parsing
            System.out.println(Interpreter.instance.interpret("\"123.45\" \"42069\" \"String Element\""));
            // Test case: Double, and fetch value from it
            List<Element> singletonElement = Interpreter.instance.interpret("\"1234.56789\"").get();
            DoubleElement element = (DoubleElement) singletonElement.get(0);
            System.out.println(element.doubleValue());

            // Test case: Stateless (STLTest)
            List<Element> stateless = Interpreter.instance.interpret("STLTest").get();
            // Test case: Stateful
            List<Element> statefulString = Interpreter.instance.interpret("STFTest \"test\"").get();
            // Test case: Stateful with number
            List<Element> statefulInteger = Interpreter.instance.interpret("STFTest \"69\"").get();

            TriggerFunction testStateless = TriggerFunction.of("test");

            TriggerFunction testStateful = TriggerFunction.of("testStateful");

            TriggerFunction testStatefulInteger = TriggerFunction.of("testStatefulInteger");

            testStateless.accept(stateless);
            testStateful.accept(statefulString);
            testStatefulInteger.accept(statefulInteger);

            List<Element> eventTest = Interpreter.instance.interpret("await event.test print \"Test Completed!\" \"100.00\" \"0\"").get();

            TriggerFunction event = TriggerFunction.of("testEvent");

            event.accept(eventTest);

            System.out.println(" - + - Firing TestEvent - + -");
            MinecraftForge.EVENT_BUS.post(new TestEvent());

            List<Element> addTest = Interpreter.instance.interpret("add \"[(example)]\" print \"Example Test Completed!\" \"123\" \"12.3456\"").get();

            TriggerFunction testAdd = TriggerFunction.of("testAdd");

            testAdd.accept(addTest);

            System.out.println("Fired fake message");

            TriggerFunctionManager.printMap();

            FakeTrigger.sendTrigger("example");

            TriggerFunctionManager.printMap();

            System.out.println("Waiting 1s");
            Thread.sleep(1000);

            System.out.println("Fired fake message again");

            TriggerFunctionManager.printMap();

            FakeTrigger.sendTrigger("example");

            TriggerFunctionManager.printMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
