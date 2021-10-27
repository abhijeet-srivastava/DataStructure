package com.walmart;

import java.util.*;
import java.util.stream.IntStream;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.apache.commons.collections4.multiset.HashMultiSet;

public class SlidingWindow {
    public static final int CHAR_RANGE = 128;
    public static void main(String[] args) {
        SlidingWindow sw = new SlidingWindow();
        //sw.testLongestAtMost();
        sw.testAnagram();
    }

    private void testAnagram() {
        String str = "cbaebabacd";
        String pattern = "abc";
        //List<Integer> res = getAllAnagrams(str, pattern);
        List<Integer> res = getAllAnagarams(str, pattern);
        res.stream().forEach(e -> System.out.println(e));
    }

    private void testLongestAtMost() {
        //String str = "eeecccaacccccc";
        int[] A = {1,1,2,2,3,4,4};
        System.out.printf("Unique Number %d\n", singleNumber(A));
        String str = "abcbdbdbbdcdabd";
        int k = 3;
        String res = findLongestSubstring(str, k);
        System.out.println(res);
    }

    private String longestWithAtMostK(String str, int k){
        char[] arr = str.toCharArray();
        int left = 0;
        int right = 0;
        int len = 0;
        String maxSubStr=null;
        Map<Character, Integer> freqMap = new HashMap<>();
        while(right < str.length()) {
            while(right < str.length()
                    && (freqMap.containsKey(arr[right])
                    ||  freqMap.size() < k )) {
                freqMap.put(arr[right], right);
                right += 1;
            }
            if(len < (right-left)) {
                len = right-left;
                maxSubStr = str.substring(left, right);
            }
            if(right >= str.length()) {
                break;
            }
            int minIndex = right;
            char minIndexChar = arr[right];
            for(Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
                if(entry.getValue() < minIndex) {
                    minIndex = entry.getValue();
                    minIndexChar = entry.getKey();
                }
            }
            left = freqMap.get(minIndexChar) + 1;
            freqMap.remove(minIndexChar);
        }
        return maxSubStr;
    }
    private String findLongestSubstring(String str, int k) {
        int end = 0,  begin = 0;
        Set<Character> window = new HashSet<>();
        int[] freq = new int[CHAR_RANGE];
        for (int low = 0, high = 0; high < str.length(); high++) {
            window.add(str.charAt(high));
            freq[str.charAt(high)] += 1;
            while (window.size() > k) {
                freq[str.charAt(low)] -= 1;
                if(freq[str.charAt(low)] == 0) {
                    window.remove(str.charAt(low));
                }
                low += 1;
            }
            if((end - begin) < (high -low)) {
                end = high;
                begin = low;
            }
        }
        return str.substring(begin, end+1);
    }

    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        // Write your code here
        Map<Integer, List<Integer>> parents = new HashMap<>();
        for(int i = 0; i < pid.size(); i++) {
            parents.computeIfAbsent(ppid.get(i), k -> new ArrayList<>()).add(pid.get(i));
        }
        List<Integer> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(kill);
        while(!queue.isEmpty()) {
            Integer process = queue.remove();
            result.add(process);
            List<Integer> childs = parents.get(process);
            queue.addAll(childs);
        }
        return result;
    }
    public int singleNumber(int[] A) {
        return IntStream.of(A).reduce((a, b) -> a^b).getAsInt();
    }

    private List<Integer> getAllAnagrams(String str, String pattern) {
        List<Integer> result = new ArrayList<>();
        if(str.length() < pattern.length()) {
            return result;
        }
        int[] pmap = new int[26];
        int[] smap = new int[26];
        for(char ch : pattern.toCharArray()) {
            pmap[ch - 'a'] += 1;
        }
        int i = 0;
        for(; i <= str.length() - pattern.length() + 1; i++) {
            if(i < pattern.length()) {
                smap[str.charAt(i) - 'a'] += 1;
                continue;
            }
            if(matches(smap, pmap)) {
                result.add(i-pattern.length());
            }
            smap[str.charAt(i-pattern.length()) - 'a'] -= 1;
            smap[str.charAt(i) - 'a'] += 1;
        }
        if(matches(smap, pmap)) {
            result.add(i-pattern.length());
        }
        return result;
    }

    private boolean matches(int[] smap, int[] pmap) {
        boolean result = true;
        for(int i = 0; i < 26; i++) {
            result &= (smap[i] == pmap[i]);
            if(!result) {
                break;
            }
        }
        return result;
    }

    private boolean isEqual(Map<Character, Integer> window, Map<Character, Integer> stillNeeded) {
        return window.size() == stillNeeded.size()
                && window.keySet().containsAll(stillNeeded.keySet())
                && stillNeeded.keySet().containsAll(window.keySet())
                && isEqualCharCounts(window, stillNeeded);
    }

    private boolean isEqualCharCounts(Map<Character, Integer> window, Map<Character, Integer> stillNeeded) {
        boolean result = true;
        for(Map.Entry<Character, Integer> windowEntry : window.entrySet()) {
            result &= (windowEntry.getValue() == stillNeeded.get(windowEntry.getKey()));
            if(!result) {
                return result;
            }
        }
        return result;
    }
    private List<Integer> getAllAnagarams(String str, String pattern) {
        List<Integer> result = new ArrayList<>();
        if(str.length() < pattern.length()) {
            return result;
        }
        Multiset<Character> patternSet =  HashMultiset.create();
        Multiset<Character> window =  HashMultiset.create();
        for(char ch : pattern.toCharArray()) {
            patternSet.add(ch);
        }
        for(int i = 0; i < str.length(); i++) {
            if(i < pattern.length()) {
                window.add(str.charAt(i));
                continue;
            }
            if(window.containsAll(patternSet)) {
                result.add(i - pattern.length());
            }
            window.remove(str.charAt(i-pattern.length()));
            window.add(str.charAt(i));
        }
        return result;
    }
}
