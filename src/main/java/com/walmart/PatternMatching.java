package com.walmart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternMatching {
    public static void main(String[] args) {
        PatternMatching pm = new PatternMatching();
        pm.testPatterns();
    }

    private void testPatterns() {
        String str = "adceb", pattern = "*a*b";
        if(isMatch(str, pattern)) {
            System.out.printf("%s matches pattern %s\n", str, pattern);
        } else {
            System.out.printf("%s does not matches pattern %s\n", str, pattern);
        }
    }

    public boolean isMatch(String str, String pattern) {
        char[] strArr = str.toCharArray();
        char[] pArr = pattern.toCharArray();
        Map<Integer, List<Tuple>> fsa = new HashMap<>();
        int currState = 0;
        for(char ch : pArr) {
            if(ch == '*') {
                fsa.computeIfAbsent(currState, e -> new ArrayList<Tuple>()).add(new Tuple(ch, currState));
            } else {
                fsa.computeIfAbsent(currState, e -> new ArrayList<Tuple>()).add(new Tuple(ch, currState+1));
                currState += 1;
            }
        }
        int acceptedState = currState;
        return transition(0, 0, fsa, acceptedState, strArr);
    }
    private class Tuple {
        Character ch;
        Integer nextState;

        public Tuple(Character ch, Integer nextState) {
            this.ch = ch;
            this.nextState = nextState;
        }
    }

    private boolean transition(int currState, int currIndex,
                               Map<Integer, List<Tuple>> fsa, int acceptedState, char[] str) {
        if(currIndex == str.length) {
            return currState == acceptedState;
        } else if(!fsa.containsKey(currState)) {
            return false;
        }
        List<Tuple> nxtTransitions = fsa.get(currState);
        boolean isMatch = false;
        for(Tuple nxtTransition: nxtTransitions) {
            if(nxtTransition.ch == '*'
                    || nxtTransition.ch == '?'
                    || nxtTransition.ch == str[currIndex]) {
                isMatch |= transition(nxtTransition.nextState, currIndex+1, fsa, acceptedState, str);
            } else {
                return false;
            }
            if(isMatch) {
                break;
            }
        }
        return isMatch;
    }
    private boolean transition1(int currState, int currIndex, Map<Integer, Tuple> fsa, int acceptedState, char[] str) {
        if(currIndex == str.length-1) {
            return currState == acceptedState;
        } else if(!fsa.containsKey(currState)) {
            return false;
        }
        Tuple nxtTransition = fsa.get(currState);
        if(nxtTransition.ch == '*') {
            return transition1(currState, currIndex+1, fsa, acceptedState, str)
                    || transition1(currState+1, currIndex+1, fsa, acceptedState, str);
        } else if(nxtTransition.ch == '?') {
            return transition1(currState+1, currIndex+1, fsa, acceptedState, str);
        } else if(str[currIndex] == nxtTransition.ch){
            return transition1(currState+1, currIndex+1, fsa, acceptedState, str);
        } else {
            return false;
        }

    }
}
