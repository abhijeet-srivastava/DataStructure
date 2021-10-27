package com.oracle.casb;

public class TrieNode {
    private static final int R = 26;

    private TrieNode[] links;

    private boolean isEnd;

    public TrieNode() {
        links = new TrieNode[R];
    }

    public boolean containsKey(char ch) {
        return links[ch - 'a'] != null;
    }
    public TrieNode get(char ch) {
        return links[ch - 'a'];
    }
    public void put(char ch, TrieNode node) {
        links[ch - 'a'] = node;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd() {
        isEnd = true;
    }
}
