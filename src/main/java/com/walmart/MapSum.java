package com.walmart;

import java.util.HashMap;
import java.util.Map;
//https://leetcode.com/problems/map-sum-pairs/
public class MapSum {

    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isWord;
        int value;

        public TrieNode() {
            children = new HashMap<Character, TrieNode>();
            isWord = false;
            value = 0;
        }
    }

    class TrieNode1 {
        TrieNode1[] children;
        int value;
        int sum;

        public TrieNode1() {
            children = new TrieNode1[26];
            this.value = 0;
            this.sum = 0;
        }

    }

    TrieNode root;
    TrieNode1 root1;

    /** Initialize your data structure here. */
    public MapSum() {
        root = new TrieNode();
        root1 = new TrieNode1();
    }
    public void insert1(String key, int val) {
        insertToTrie(0, key, val, root1);
    }

    private int insertToTrie(int index, String key, int val, TrieNode1 parent) {
        if(index == key.length()-1) {
            char ch = key.charAt(index);
            TrieNode1 current = parent.children[ch - 'a'];
            if(current == null) {
                parent.children[ch - 'a'] = current = new TrieNode1();
            }
            TrieNode1 current1 = parent.children[ch - 'a'] == null ? new TrieNode1() : parent.children[ch - 'a'] ;
            parent.children[ch - 'a'] = current;
            current.value = val;
            int effectiveVal = current.sum;
            current.sum = val;
        } else {

        }
        return -1;
    }


    public void insert(String key, int val) {
        TrieNode curr = root;
        for (char c : key.toCharArray()) {
            TrieNode next = curr.children.get(c);
            if (next == null) {
                next = new TrieNode();
                curr.children.put(c, next);
            }
            curr = next;
        }
        curr.isWord = true;
        curr.value = val;
    }

    public int sum(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            TrieNode next = curr.children.get(c);
            if (next == null) {
                return 0;
            }
            curr = next;
        }

        return dfs(curr);
    }

    private int dfs(TrieNode root) {
        int sum = 0;
        for (char c : root.children.keySet()) {
            sum += dfs(root.children.get(c));
        }
        return sum + root.value;
    }
}
