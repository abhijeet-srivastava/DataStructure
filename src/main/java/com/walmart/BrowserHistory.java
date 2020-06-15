package com.walmart;

import java.util.ArrayList;
import java.util.Arrays;

public class BrowserHistory {
    public static void main(String[] args) {
        BrowserHistory bw = new BrowserHistory("leetcode.com");
        bw.visit("google.com");
        bw.visit("facebook.com");
        bw.visit("youtube.com");
        System.out.println(bw.back(1));
        System.out.println(bw.back(1));
        System.out.println(bw.forward(1));
        bw.visit("linkedin.com");
        System.out.println(bw.forward(2));
        System.out.println(bw.back(2));
        System.out.println(bw.back(7));

    }
    private Node current;
    public BrowserHistory(String homepage) {
        current = new Node(homepage);
    }

    public void visit(String url) {
        Node visited = new Node(url);
        current.next = visited;
        visited.prev = current;
        current = visited;
    }

    public String back(int steps) {
        while(current.prev != null && steps > 0) {
            current = current.prev;
            steps -= 1;
        }
        return current.url;
    }

    public String forward(int steps) {
        while(current.next != null && steps > 0) {
            current = current.next;
            steps -= 1;
        }
        return current.url;
    }

    class Node{
        private String url;
        private Node prev, next;
        public Node(String url){
            this.url = url;
        }
    }
}
