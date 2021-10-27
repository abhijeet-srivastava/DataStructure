package com.walmart.designpattern.visitor;

public interface Visitable {
    public void accept(Visitor visitor);
}
