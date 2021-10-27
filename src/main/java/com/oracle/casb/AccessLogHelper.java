package com.oracle.casb;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created By : abhijsri
 * Date  : 17/07/18
 **/
public class AccessLogHelper {

    public static void main(String[] args) {
        String str = "2018-07-17T10:14:06.000+00:00 localhost dome-cni[10062]: [info] 1531822446452 \"src_ip\":\"Windows\",\"dst_host\":\"www.i2ifunding.com\",\"status\":\"Blocked\",\"url\":\"https://www.i2ifunding.com/\",\"dst_ip\":\"52.66.84.230\",\"domain_category\":\"Peer-to-Peer\",\"domain_sub_category\":\"UNKNOWN\",\"user\":\"\",\"internal_ip\":\"172.31.27.55\",\"computer_name\":\"WINDOWS-1\",\"policy\":\"Weapons,Text-Audio only,Tasteless & Offensive,Spam Related Sites,Proxies,Pornography,Peer-to-Peer,Nudity,Malware Related Sites,Illegal Software,Illegal Drugs,Game-Cartoon Violence,Game Playing & Game Media,Gambling,Advertisements & Popups,Adult Content\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36\",\"msg\":\"URL belongs to a denied category in policy\"";

        String[] array = str.split("\\s+");


        for (int i = 0; i < array.length; i++) {
            System.out.printf("%d <=> %s %c", i+1, array[i], '\n');
        }
    }
    private String getRelationStr (String unitStr, List<String> relationList) {
        Map<Unit, Integer> unitDependencyMap = getRelationShip(unitStr, relationList);
        StringBuilder sb = new StringBuilder();
        Queue<Unit> queue = new LinkedList<>();
        for (Unit unit : unitDependencyMap.keySet())  {
            if (unitDependencyMap.get(unit) == 0) {
                queue.offer(unit);
                queue.add(unit);
            }
        }
        while (!queue.isEmpty()) {
            Unit unit = queue.poll();

        }
        return null;
    }

    /**
     *
     *day,hour,second,minute
     * day = 24 hour
     * hour = 60 minute
     * minute = 60 second
     * 1day = 24hour = 1440minute = 86400second
     */
    private Map<Unit, Integer> getRelationShip(String unitStr, List<String> relationList) {
        Map<Unit, Integer> unitDependencyMap = new HashMap<>();
        String[] unitNames = unitStr.split("\\s");
        for (String name : unitNames) {
            Unit unit = new Unit(name);
            unitDependencyMap.put(unit, 0);
        }
        for (String relationStr : relationList) {
            String[] relationArr = relationStr.split(" = ");
            Object[] leftVal = parseRelation(relationArr[0]);
            Object[] rightVal = parseRelation(relationArr[1]);
            if ((Integer) leftVal[0] < (Integer) rightVal[0]) {
                updateDependencyMap(unitDependencyMap, leftVal, rightVal);
            } else {
                updateDependencyMap(unitDependencyMap, rightVal, leftVal);
            }
        }
        return unitDependencyMap;
    }

    private void updateDependencyMap(Map<Unit, Integer> unitDependencyMap,
            Object[] leftVal, Object[] rightVal) {
        int conversionValue = ((Integer) rightVal[0]) / ((Integer) leftVal[0]);
        Unit leftUnit = (Unit) leftVal[1];
        Unit rightUnit = (Unit) rightVal[1];
        leftUnit.setRelationUnit(rightUnit);
        leftUnit.setRelation(conversionValue);
        unitDependencyMap.put(rightUnit, 1);
    }

    private Object[] parseRelation(String value) {
        int intVal = 0;
        StringBuilder unitStr = new StringBuilder();
        for (char ch : value.toCharArray()) {
            if (Character.isDigit(ch)) {
                intVal = (intVal * 10 + Character.getNumericValue(ch));
            } else {
                unitStr.append(ch);
            }
        }
        if (intVal == 0) {
            intVal = 1;
        }
        return new Object[] {Integer.valueOf(intVal), new Unit(unitStr.toString())};
    }

    private class Unit {
        String name;
        Unit relationUnit;
        Integer relation;

        public Unit(String name) {
            this.name = name;
        }

        public void setRelationUnit(Unit relationUnit) {
            this.relationUnit = relationUnit;
        }

        public void setRelation(Integer relation) {
            this.relation = relation;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Unit unit = (Unit) o;
            return Objects.equals(name, unit.name);
        }

        @Override
        public int hashCode() {

            return Objects.hash(name);
        }
    }

    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(id);
        while(!queue.isEmpty() && level-- > 0) {
            int n = queue.size();
            while(n-- > 0) {
                int current = queue.poll();
                visited.add(current);
                if(current < friends.length) {
                    int[] friendList = friends[current];
                    for(int friend : friendList) {
                        if(!visited.contains(friend)) {
                            queue.offer(friend);
                        }
                    }
                }
            }
        }
        Map<String, Integer> frequencyMap = new HashMap<>();
        for(int kLevelfriend : queue) {
            if(kLevelfriend < watchedVideos.size()) {
                List<String> videos = watchedVideos.get(kLevelfriend);
                for(String video : videos) {
                    int frequency = frequencyMap.getOrDefault(video, 0);
                    frequencyMap.put(video, frequency+1);
                }
            }
        }
        return frequencyMap.entrySet().stream().sorted((a, b) -> a.getValue() != b.getValue() ? a.getValue().compareTo(b.getValue()) : a.getKey().compareTo(b.getKey())).map(Map.Entry::getKey).collect(Collectors.toList());

    }
}
