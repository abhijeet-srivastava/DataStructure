package com.oracle.casb;

import java.util.Stack;

public class StockSpannerFreq {

    private Stack<Integer> stocks;

    public StockSpannerFreq() {
        stocks = new Stack<>();
    }
    public int next(int price) {
        int i = 1;
        while (!stocks.isEmpty() && stocks.peek() <= price) {
            stocks.pop();
            i += 1;
        }
        stocks.push(price);
        return i;
    }
}
