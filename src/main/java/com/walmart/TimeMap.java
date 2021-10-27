package com.walmart;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeMap {
    Map<String, TreeMap<Integer, String>> map;

    /** Initialize your data structure here. */
    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        if(!map.containsKey(key)) {
            TreeMap<Integer, String> tm = new TreeMap<>((a,b) -> a.compareTo(b));
            map.put(key, tm);
        }
        map.get(key).put(Integer.valueOf(timestamp), value);
    }

    public String get(String key, int timestamp) {
        return  map.containsKey(key) ? map.get(key).floorEntry(timestamp).getValue()  : "";
    }
}
