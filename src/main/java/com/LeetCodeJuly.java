package com;

import com.oracle.casb.common.ListNode;

import java.util.*;

public class LeetCodeJuly {

    public static void main(String[] args) {
        LeetCodeJuly lj = new LeetCodeJuly();
        //lj.testReorderList();
        lj.testWordLadder2();
    }



    public int[] findOrder(int numCourses, int[][] prerequisites) {

        boolean isPossible = true;
        Map<Integer, List<Integer>> adjList = new HashMap<Integer, List<Integer>>();
        int[] indegree = new int[numCourses];
        int[] topologicalOrder = new int[numCourses];

        // Create the adjacency list representation of the graph
        for (int i = 0; i < prerequisites.length; i++) {
            int dest = prerequisites[i][0];
            int src = prerequisites[i][1];
            List<Integer> lst = adjList.getOrDefault(src, new ArrayList<Integer>());
            lst.add(dest);
            adjList.put(src, lst);

            // Record in-degree of each vertex
            indegree[dest] += 1;
        }

        // Add all vertices with 0 in-degree to the queue
        Queue<Integer> q = new LinkedList<Integer>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.add(i);
            }
        }

        int i = 0;
        // Process until the Q becomes empty
        while (!q.isEmpty()) {
            int node = q.remove();
            topologicalOrder[i++] = node;

            // Reduce the in-degree of each neighbor by 1
            if (adjList.containsKey(node)) {
                for (Integer neighbor : adjList.get(node)) {
                    indegree[neighbor]--;

                    // If in-degree of a neighbor becomes 0, add it to the Q
                    if (indegree[neighbor] == 0) {
                        q.add(neighbor);
                    }
                }
            }
        }

        // Check to see if topological sort is possible or not.
        if (i == numCourses) {
            return topologicalOrder;
        }

        return new int[0];
    }

    private void testReorderList() {
        ListNode head = createList(new int[] {1,2,3,4});
        reorderList(head);
        while (head != null) {
            System.out.printf("%d\n", head.val);
            head = head.next;
        }
    }
    ListNode first;
    public void reorderList(ListNode head) {
        first = head;
        reverse(head);
        //return reverse(head);
    }

    public ListNode reverse(ListNode head) {
        if(head == null) {
            return null;
        } else  {
            ListNode rev = reverse(head.next);
            if(first == head || first.next == head) {
                head.next = null;
            } else {
                ListNode tmp = first.next;
                first.next = head;
                if (rev != null) {
                    rev.next = first;
                }
                first = tmp;
            }
            return head;
        }
    }
    private ListNode createList(int[] nums) {
        ListNode head = null;
        ListNode previous = head;
        for(int i : nums) {
            ListNode current = new ListNode(i);
            if(head == null) {
                head = current;
                previous = head;
            } else {
                previous.next = current;
                previous = current;
            }
        }
        return head;
    }

    public void testWordLadder2() {
        List<String> wordList = Arrays.asList(new String[]{"hot","dot","dog","lot","log","cog"});
        List<List<String>> ladders = findLadders("hit", "cog", wordList);
        System.out.println(" " + ladders.size());
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        List<List<String>> paths = new ArrayList<>();
        if(!dict.contains(endWord)) {
            return paths;
        }
        Map<String, Integer> mdfs = new HashMap<>();
        for(String word : wordList) {
            mdfs.put(word, Integer.MAX_VALUE);
        }
        mdfs.put(beginWord, 0);
        List<String> path = new ArrayList<>();
        path.add(beginWord);
        Map<Integer, List<List<String>>> map = new HashMap<>();
        dfs(beginWord, endWord, dict, mdfs, map, path);
        if(mdfs.get(endWord) == Integer.MAX_VALUE) {
            return Collections.EMPTY_LIST;
        }
        return map.get(mdfs.get(endWord));
        /*Stack<Object[]> stack = new Stack<>();
        stack.push(new Object[]{beginWord, 1});
        Set<String>  visited = new HashSet<>();
        visited.add(beginWord);
        int depth = Integer.MAX_VALUE;
        Map<Integer, List<List<String>>> map = new HashMap<>();
        while (!stack.isEmpty())  {
            Object[] currentObj = stack.pop();
            String currentWord = currentObj[0].toString();
            int currDepth = (Integer) currentObj[1];
            visited.add(currentWord);
            if(currentWord.equals(endWord)) {
                map.computeIfAbsent(currDepth+1, ArrayList::new).add(new ArrayList<>(visited));
                depth = Math.max(depth, currDepth);
            }
            Set<String> neighbours = getNeighbours(currentWord, visited, dict);
            if(neighbours.isEmpty()) {
                visited.remove(currentWord);
            } else {
                for(String neighbour : neighbours) {
                    stack.push(new Object[]{neighbour, currDepth+1});
                }
            }
        }
        return depth < Integer.MAX_VALUE ?  map.get(depth) : new ArrayList<>();*/
    }

    private void dfs(String current, String endWord, Set<String> dict, Map<String, Integer> mdfs, Map<Integer, List<List<String>>> map, List<String> path) {
        if(current.equals(endWord)) {
            path.add(current);
            int depth = path.size()+1;
            map.computeIfAbsent(depth, ArrayList::new).add(path);
            int maxDepthEnd = Math.max(depth, mdfs.get(endWord));
            mdfs.put(endWord, maxDepthEnd);
        }
        Set<String> neighbours =  getNeighbours(current, path, dict);
        List<String> paths = new ArrayList<>(neighbours);
        if(neighbours.isEmpty()) {
            return;
        }
        path.add(current);
        for(String neighbour : neighbours) {
            //List<String> newPath =
        }
    }

    private Set<String> getNeighbours(String currentWord, List<String> visited, Set<String> dict) {
        Set<String> result = new HashSet<>();
        char[] word = currentWord.toCharArray();
        for(int i = 0; i < word.length; i++) {
            char ch = word[i];
            for(char rep = 'a';  rep <= 'z'; rep++) {
                if(rep == ch) {
                    continue;
                }
                word[i] = rep;
                String newWord = String.valueOf(word);
                if(!visited.contains(newWord) && dict.contains(newWord)) {
                    result.add(newWord);
                }
            }
            word[i] = ch;
        }
        return result;
    }
}
