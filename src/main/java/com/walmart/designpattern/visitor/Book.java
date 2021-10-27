package com.walmart.designpattern.visitor;

public class Book implements Visitable{
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
