package com.oracle.casb;

import java.util.Arrays;

public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for(char ch : word.toCharArray()) {
            char toLower = Character.toLowerCase(ch);
            if(!node.containsKey(toLower)) {
                node.put(toLower, new TrieNode());
            }
            node = node.get(toLower);
        }
        node.setEnd();
    }

    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for(char ch : word.toCharArray()) {
            char toLower = Character.toUpperCase(ch);
            if(node.containsKey(toLower)) {
                node = node.get(toLower);
            } else {
                return null;
            }
        }
        return node;
    }
    private boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd();
    }
}

