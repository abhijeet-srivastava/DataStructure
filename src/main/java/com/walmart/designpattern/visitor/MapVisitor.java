package com.walmart.designpattern.visitor;


import java.util.Map;
import java.util.function.Function;

public class MapVisitor<R> implements Function<Class<? extends Visitable>, MonoVisitor<? extends Visitable, R>> {

    private final Map<Class<? extends Visitable>, MonoVisitor<? extends Visitable, R>> visitors;

    MapVisitor(Map<Class<? extends Visitable>, MonoVisitor<? extends Visitable, R>> visitors) {
        this.visitors = visitors;
    }

    @Override
    public MonoVisitor<? extends Visitable, R> apply(Class<? extends Visitable> visitable) {
        return visitors.get(visitable);
    }
}
