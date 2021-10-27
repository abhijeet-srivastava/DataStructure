package com.walmart.java.challenge;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

public class StringJoinerSupplier {
    private final StringJoiner sj;

    public StringJoinerSupplier(String seprator, String prefix, String suffix) {
        this.sj = new StringJoiner(seprator, prefix, suffix);
    }

    public static void main(String[] args) {
        StringJoinerSupplier sjs = new StringJoinerSupplier(" : ", "{", "}");
        sjs.testStringJoin();
    }

    private void testStringJoin() {
        Consumer<String> stringConsumer = (str) -> this.sj.add(str);
        List<String>  params = Arrays.asList("shyam", "bindu", "chitra", "manish", "shipra", "shishir", "abhijeet", "priyanka", "asheesh", "neha");
        params.stream().forEach((name) ->stringConsumer.accept(name));
        System.out.printf("%s\n",this.sj);




    }
}
