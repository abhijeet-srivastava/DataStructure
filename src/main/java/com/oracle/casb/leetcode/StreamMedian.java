package com.oracle.casb.leetcode;


import java.util.PriorityQueue;
import java.util.Queue;

public class StreamMedian {

    private Queue<Integer> leftHeap;
    private Queue<Integer> rightHeap;

    public StreamMedian() {
        this.leftHeap = new PriorityQueue<>((a, b) -> b.compareTo(a));
        this.rightHeap = new PriorityQueue<>();
    }

    public int getMedian() {
        if(leftHeap.size() == rightHeap.size()) {
            return (leftHeap.peek() + rightHeap.peek())/2;
        }
        return rightHeap.peek();
    }


    public void insertNext(int num) {
        if(leftHeap.isEmpty() && rightHeap.isEmpty()) {
            leftHeap.add(num);
        }
        if (num > leftHeap.peek()) {
            rightHeap.add(num);
            if (rightHeap.size() - leftHeap.size() > 1) {
                leftHeap.add(rightHeap.poll());
            }
        } else {
            leftHeap.add(num);
            if (leftHeap.size() - rightHeap.size() > 1) {
                rightHeap.add(leftHeap.poll());
            }
        }
    }
}
