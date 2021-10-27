package com.walmart.designpattern.visitor;

public interface MonoVisitor<T, R> {
    R visit(T t);
}
