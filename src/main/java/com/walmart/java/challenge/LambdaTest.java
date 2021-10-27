package com.walmart.java.challenge;

import java.util.function.*;

public class LambdaTest {
    static int jc = 0;

    public static void main(String[] args) {
        LambdaTest lt = new LambdaTest();
        //lt.testJokerCraziness();
        lt.DarkSide();

    }

    private void DarkSide() {
        String luke = "userSaber";
        Supplier<String> yoda = () -> "useForce";
        Consumer<String> consumeForce = System.out::println;

        UnaryOperator<String> fightEmpire = luke::concat;
        UnaryOperator<String> fightDarkSide = String::toUpperCase;

        BiFunction<UnaryOperator<String>, UnaryOperator<String>, Function<String, String>> attackDarkSide = Function::andThen;

        String finalAttack = attackDarkSide.apply(fightEmpire, fightDarkSide).apply(yoda.get());

        consumeForce.accept(finalAttack);
    }

    private void testJokerCraziness() {
        Supplier<Integer> supplier = () -> jc++;

        Consumer<Integer> consumer = (ba) -> System.out.println(ba + jc++);

        System.out.println(jc + supplier.get());
        consumer.accept(1);
    }
}
