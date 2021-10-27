package com.oracle.casb;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.ValidationException;
import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        App app = new App();
        //app.getPivotAndStats(false, false, true);
        //app.testSmallestSubstring();
        app.testRollingHash();
        /*app.testCandies();
        app.testMin();
        app.testIteniary();
        app.testUniqueName();
        int[] nums = {};

        app.testTopologicalSort();
        app.testLargestDuplicateSubStr();
        app.testKthAncestor();
        //app.testLeastFrequent();

        app.testCharSorted();
        app.testAnagram();
        app.testImmutableSet();
        try {
            app.testQuarterFromDates();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        //app.testMaxLength();
        //app.readNumberDigits();
        //app.testRegionConcat();
        //app.testDepartmentConcat();
        //app.testUnsafe();
        //app.checkZipFile("/Users/abhijsri/tools/tomcat9/webapps/SecloreWebApp/WEB-INF/lib");
         //app.checkZipFile("/Users/abhijsri/odcs/SecloreWebApp/src/main/webapp/WEB-INF/lib");
       // app.checkZipFile("/Users/abhijsri/odcs/SecloreWebApp/target/SecloreWebApp/WEB-INF/lib");

        //app.checkValidZip("fs-ws-client.jar");
        /*app.getCount();
        try {
            String retVal = app.encodeHtml("<<SCRIPT>alert(\\\"XSS\\\");//<</SCRIPT>");
            System.out.println(retVal  );
        } catch (ValidationException e) {
            e.printStackTrace();
        }*/
    }
    public String restoreString(String s, int[] indices) {
        int[][] array = new int[indices.length][2];
        for(int i = 0; i < indices.length; i++) {
            array[i][0] = s.charAt(i);
            array[i][1] = indices[i];
        }
        Arrays.sort(array, (a, b) -> a[1] - b[1]);
        StringBuilder sb = new StringBuilder();
        for(int[] arr : array) {
            sb.append((char) arr[1]);
        }
        return sb.toString();
    }
    private void testRollingHash() {
        String str = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
        List<String> result = findRepeatedDnaSequences(str);
        for(String res: result) {
            System.out.printf("Repeated seq: %s\n", res);
        }
    }
    //private static Map<Character, Integer> map = ImmutableMap.of('A', 1, 'C', 2, 'G', 3, 'T', 4);
    public List<String> findRepeatedDnaSequences(String s) {
        if(s == null || s.length() < 20) {
            return Collections.EMPTY_LIST;
        }
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 1);
        map.put('C', 2);
        map.put('G', 3);
        map.put('T', 4);
        int len = s.length();
        char[] array = s.toCharArray();
        Map<Long, Integer> rollingHashToLastIndex = new HashMap<>();
        List<String> result = new ArrayList<>();
        long rollingHash = 0;
        int power = 1;
        for(int i = 0; i < len; i++) {
            char ch = array[i];
            int value = map.get(ch);
            if(i < 10) {
                rollingHash = (rollingHash*power) + value;
                power *= 4;
            } else {
                rollingHash -= map.get(array[i-10]);
                rollingHash /= 4;
                rollingHash += (value*power);
            }
            if(rollingHashToLastIndex.containsKey(rollingHash)
                    && rollingHashToLastIndex.get(rollingHash) < i) {
                result.add(s.substring(i, i+10));
            }
            if(i >= 9) {
                rollingHashToLastIndex.put(rollingHash, i);
            }
        }
        return result;
    }



    private void testSmallestSubstring() {
        String smallest = smallestSubsequence("cbacdcbc");
        System.out.println(smallest);
    }


    public String smallestSubsequence(String s) {
        if(s == null || s.isEmpty()) {
            return "";
        }
        char[] arr = s.toCharArray();
        int[] lastIndexOf = new int[26];
        for(int i = 0; i < arr.length; i++) {
            lastIndexOf[arr[i]-'a'] = i;
        }
        boolean[] used = new boolean[26];
        Deque<Character> stack = new ArrayDeque<>();
        for(int i = 0; i < arr.length; i++) {
            int index = arr[i] - 'a';
            if(used[index]) {
                continue;
            }
            while(!stack.isEmpty() && stack.peek() > arr[i] && lastIndexOf[index] > i) {
                char popped = stack.pop();
                used[popped - 'a'] = false;
            }
            stack.push(arr[i]);
            used[arr[i] - 'a'] = true;
        }
        List<Character> list = new ArrayList();
        while(!stack.isEmpty()) {
            list.add(0, stack.pop());
        }
        return String.valueOf(list.stream().map(Character::charValue).toArray());
    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> GRAPH = new HashMap<>();
        for(int i = 0; i <  equations.size(); i++) {
            List<String> equation = equations.get(i);
            double value = values[i];
            GRAPH.computeIfAbsent(equation.get(0), key -> new HashMap<String, Double>()).put(equation.get(1), value);
            GRAPH.computeIfAbsent(equation.get(1), key -> new HashMap<String, Double>()).put(equation.get(0), 1/value);
        }
        double[] result = new double[queries.size()];
        int i = 0;
        for(List<String> query : queries) {
            result[i++] = getPathCost(query.get(0), query.get(1), GRAPH);
        }
        return result;
    }

    private double getPathCost(String source, String dest, Map<String, Map<String, Double>> GRAPH) {
        if(source.equals(dest)) {
            return GRAPH.containsKey(source) ? 1.0d : -1.0d;
        } else if(!GRAPH.containsKey(source)) {
            return -1.0d;
        } else if(GRAPH.get(source).containsKey(dest)) {
            return GRAPH.get(source).get(dest);
        }
        Stack<String> path = findPath(source, dest, GRAPH);
        if(path == null || path.isEmpty()) {
            return -1.0d;
        }
        double weight = 1.0d;
        String last = dest;
        while (!path.isEmpty()) {
            String secLast = path.pop();
            weight *= GRAPH.get(secLast).get(last);
            last = secLast;
        }
        return weight;
    }

    private Stack<String> findPath(String source, String dest, Map<String, Map<String, Double>> GRAPH) {
        Set<String> visited = new HashSet();
        Stack<String> path = new Stack<>();
        dfs(source,  dest,  GRAPH, path, visited);
        return path;
    }

    private boolean dfs(String source, String dest, Map<String, Map<String, Double>> GRAPH,
                        Stack<String> path, Set<String> visited) {

        if(source.equals(dest)) {
            return true;
        }
        path.add(source);
        Map<String, Double> src = GRAPH.get(source);
        if(src == null) {
            path.clear();
            return false;
        }
        visited.add(source);
        for(String next : src.keySet()) {
            if(visited.contains(next)) {
                continue;
            }
            boolean isReachable = dfs(next,  dest,  GRAPH, path, visited);
            if(isReachable) {
                return true;
            }
        }
        path.pop();
        return true;
    }
    public int deleteAndEarn(int[] nums) {

        //  2 -4
        //  3 - 9
        //  4 -4
        //  2 [4,-5]
        //  3 [9 , 1]
        //  4 [4, -5]
        // 4 - 9 = -5
        // 9 - 8 = 1
        // 4 - 9 = -5
        Map<Integer, Long> frequency
                = IntStream.of(nums).boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<int[]> list = new ArrayList<>();
        Set<Integer> eliminated = new HashSet<>();
        for(Map.Entry<Integer, Long> entry : frequency.entrySet()) {
            int counts = Long.valueOf(entry.getValue()).intValue();
            int gain = entry.getKey()*counts;
            int loss = 0;
            if(frequency.containsKey(entry.getKey()+1)) {
                loss += (entry.getKey()+1) * Long.valueOf(frequency.get(entry.getKey()+1)).intValue();
            }
            if(frequency.containsKey(entry.getKey()-1)) {
                loss += (entry.getKey()-1) * Long.valueOf(frequency.get(entry.getKey()-1)).intValue();
            }
            int[] arr = {entry.getKey(), gain, gain-loss};
            list.add(arr);
        }

        Collections.sort(list, (a,b) -> a[2] - b[2]);
        int total = 0;
        for(int[] arr : list) {
            if (eliminated.contains(arr[0])) {
                continue;
            }
            total += arr[1];
            eliminated.add(arr[0] - 1);
            eliminated.add(arr[0] + 1);
        }
        return total;
    }
    public int countSquares(int[][] matrix) {
        int count = 0;

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] == 1) {
                    count += 1;
                    if(i > 0 && j > 0) {
                        int width = Math.min(matrix[i-1][j-1],
                                Math.min(matrix[i-1][j], matrix[i][j-1]));
                        matrix[i][j] = width;
                        count += width;
                    }

                }
            }
        }
        return count;
    }
    private void testCandies() {
        int[] res = distributeCandies(10, 3);
        System.out.println(IntStream.of(res)
                .boxed().map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }

    public int[] distributeCandies(int candies, int num_people) {
        int[] res = new int[num_people];
        //int index = 0;
        int candy = 1;
        while(candies > 0) {
            for(int i = 0; i < num_people && candies > 0 ; i++) {
                if(candy > candies) {
                    res[i] += candies;
                    candies = 0;
                    break;
                } else {
                    res[i] += candy;
                    candies -= candy;
                    candy += 1;
                }
            }
        }
        return res;
    }


    private void testCharSorted() {
        String str = "tree";
        String res = frequencySort(str);
        System.out.printf("%s\n", res);
    }

    private int maxSumCircular(int[] A) {
        int len = A.length;
        int ans = Integer.MIN_VALUE;
        int curr = A[0];
        //Kadane for One Interval
        for (int i = 1; i < len; i++) {
            curr = A[i] + Math.max(0, curr);
            ans = Math.max(ans, curr);
        }
        /**
         * For 2 Interval
         * For all i find maximum of sum A[j:] for all j >= i+1
         */
        int[] rightsum = new int[len];
        rightsum[len - 1] = A[len-1];
        for(int i = len-2; i >= 0; i--) {
            rightsum[i] = A[i] + rightsum[i+1];
        }
        int[] maxRightSum = new int[len];
        maxRightSum[len-1] = A[len-1];
        for(int i = len-2; i >= 0; i--) {
            maxRightSum[i] = Math.max(maxRightSum[i+1], rightsum[i+1]);
        }
        int leftSum = 0;
        for(int i = 0; i < len; i++) {
            leftSum += A[i];
            ans = Math.max(ans, leftSum + maxRightSum[i+2]);
        }
        return ans;
    }

    public int maxSubarraySumCircular(int[] A) {
        int len = A.length;
        int sum = 0;
        for(int i : A) {
            sum += i;
        }
        int ans = kadane(A, 0, len-1, 1);
        int ans1 = sum + kadane(A, 1, len-1, -1);
        int ans2 = sum + kadane(A, 0, len-2, -1);
        return Math.max(ans, Math.max(ans1, ans2));
    }
    private int kadane(int[] A, int start, int end, int sign) {
        int curr = Integer.MIN_VALUE;
        int ans = Integer.MIN_VALUE;
        for(int i = start; i <= end; i++) {
            // The maximum non-empty subarray for array
            // [sign * A[i], sign * A[i+1], ..., sign * A[j]]
            curr = sign*A[i] + Math.max(curr, 0);
            ans = Math.max(curr, ans);
        }
        return ans;
    }
    public int maxSubarraySumCircular1(int[] A) {
        //Max = kadane(A, 0, len-1), sum - kadan-min(A, 0, len-2), sum - kadane-min(A, 1, len-1)
        int len = A.length;
        int sum = 0;
        for(int i : A) {
            sum += i;
        }
        int ans1 = Integer.MIN_VALUE;
        int curr = Integer.MIN_VALUE;
        for(int i = 0; i < len; i++) {
            curr = A[i] + Math.max(curr, 0);
            ans1 = Math.max(ans1, curr);
        }
        int ans2 = sum - kadana_min(A, 0, len-2);
        int ans3 = sum - kadana_min(A, 1, len-1);
        return Math.max(ans1, Math.max(ans2, ans3));
    }
    private int kadana_min(int[] A, int start, int end) {
        int curr = Integer.MAX_VALUE;
        int ans = Integer.MAX_VALUE;
        for(int i = start; i <= end; i++) {
            curr = A[i] + Integer.min(curr, 0);
            ans = Math.min(ans, curr);
        }
        return ans;
    }

    public String frequencySort(String s) {
        Map<Character, Long> count = charStream(s)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        String str = count.entrySet().stream()
                .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                .map(Map.Entry::getKey)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return charStream(s)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                .map(e -> createStr(e.getKey(), e.getValue()))
                .collect(
                        Collector.of(
                                StringBuilder::new,
                                StringBuilder::append,
                                StringBuilder::append,
                                StringBuilder::toString)
                );
    }

    private String createStr(Character ch, Long value) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < value.intValue(); i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    private Stream<Character> charStream(String s) {
        Character[] chArr = new Character[s.length()];
        int i = 0;
        for(char ch : s.toCharArray()) {
            chArr[i++] = Character.valueOf(ch);
        }
        return Arrays.stream(chArr);
    }

    private void testAnagram() {
        List<Integer> list = findAnagrams("abab", "ab");
        System.out.println(list.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

    public List<Integer> findAnagrams(String s, String p) {
        Map<Character, Integer> patternMap = createCharMap(p);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < s.length() - p.length() ; i++) {
            if(isAnagram(s.substring(i, i+p.length()), patternMap)) {
                list.add(i);
            }
        }
        return list;
    }

    private boolean isAnagram(String subStr, Map<Character, Integer> patternMap) {
        Map<Character, Integer> subMap = createCharMap(subStr);
        boolean res = true;
        for(Map.Entry<Character, Integer> entry : patternMap.entrySet()) {
            if(!subMap.containsKey(entry.getKey()) || subMap.get(entry.getKey()) != entry.getValue()) {
                res = false;
                break;
            }
        }
        for(Map.Entry<Character, Integer> entry : subMap.entrySet()) {
            if(!patternMap.containsKey(entry.getKey()) || patternMap.get(entry.getKey()) != entry.getValue()) {
                res = false;
                break;
            }
        }
        return res;
    }

    private Map<Character, Integer> createCharMap(String subStr) {
        /*return Stream.of(subStr.toCharArray())
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));*/

        Map<Character, Integer> charMap = new HashMap<>();
        for (char ch : subStr.toCharArray()) {
            charMap.merge(ch,                  // key = char
                    1,                  // value to merge
                    Integer::sum);
        }
        return charMap;
    }

    private int minDays(int count) {
        if(count == 1) {
            return 0;//or 1
        }
        if(count % 2 == 0) {
            //Even Number
            return 1+ minDays(count/2);
        } else {
            //Odd
            return 1 + minDays(count-1);
        }
    }

    private void testImmutableSet() {
        ImmutableSet.Builder<String> builder = ImmutableSet.<String>builder();
        builder.add("DC_ID");
        builder.add("CHAMBER");
        builder.add("CHAMBER");
        builder.add("WH_AREA");

        System.out.println(builder.build().stream().collect(Collectors.joining(",", "[", "]")));
    }

    private void testQuarterFromDates() throws ParseException {
        for (int month = 1; month <= 12; month++) {
            StringBuilder sb  = new StringBuilder();
            sb.append("2019").append('-').append("01").append('-');
            if(month < 10) {
                sb.append('0');
            }
            sb.append(month);
            //System.out.printf("Date  %s%c", sb.toString(), '\n');
            Date date = new SimpleDateFormat("yyyy-dd-MM").parse(sb.toString());

            System.out.printf("For date %s Quarter of week %d\n", date.toString(), getCurrentYearQuarterNum(date));
        }
    }

    public static Integer getCurrentYearQuarterNum(Date date) {
        int currentMonthNum = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
        return (currentMonthNum/3) + ((currentMonthNum%3) == 0 ? 0 : 1);
    }

    private void testRegionConcat() {
        Set<String> regions = ImmutableSet.of("CENTRAL", "NORTH EAST", "SOUTH EAST", "WEST");
        String concatenatedStr = regions.stream()
                .map(e -> String.format("%c%s%c", '"', e, '"'))
                .collect(Collectors.joining(" OR ", "region_name:(", ")"));
        System.out.printf("%s\n", concatenatedStr);
    }
    private void testDepartmentConcat() {
            Set<Integer> departments = ImmutableSet.of(1024, 2048);
            String concatenatedStr = departments.stream().map(String::valueOf)
                    .collect(Collectors.joining(" OR ", "department_id:(", ")"));
            System.out.printf("%s\n", concatenatedStr);
        }

    private void readNumberDigits() {
        String number = "854559745684320697549060368131279814466643179689928095831053239604130293492672614469791533133321";
        int sum = 0;
        for (char ch : number.toCharArray()) {
            sum += Character.getNumericValue(ch);
        }
        System.out.printf("Length %d, Sum %d%c", number.length(), sum, '\n');
    }

    private void testMaxLength() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/abhijsri/github/casb-test/src/main/resources/ESAPI.properties"))))) {
            String longestLine
                    = reader.lines()
                    .min(Comparator.comparingInt(s -> s.length()))
                    .get();
            System.out.println(longestLine);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void testUnsafe() {
        Field f = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);
            //unsafe.arrayIndexScale()
            System.out.println(unsafe.allocateInstance(FieldType.class));
            System.out.println(unsafe.pageSize());
        } catch (NoSuchFieldException |IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private boolean checkValidZip(String fileName) {
        Path zipfile = Paths.get(fileName);
        boolean isValid = true;
        try {
            FileSystem fs = FileSystems.newFileSystem(zipfile, App.class.getClassLoader());
        } catch (IOException e) {
            isValid = false;
            e.printStackTrace();
        } catch (Error error) {
            System.out.println(fileName + " is not valid");
            error.printStackTrace();
            isValid = false;
        }
        System.out.println(isValid ? fileName + " is Valid" : fileName + " is inValid");
        return isValid;
    }

    private void checkZipFile(String directoryName) {
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
           // System.out.println(file.getName());
            if (checkValidZip(directoryName, file)) {
                System.out.println(file.getName() +" is valid zip file");
            } else {
                System.out.println(file.getName() +" is not a valid zip file");
            }
        }

    }

    private boolean checkValidZip(String directoryName, File filename) {
        boolean isValid = true;
        String file = directoryName + '/' + filename.getName();
        System.out.println("File : " + file);
        Path zipfile = Paths.get(file);
        try {
            FileSystem fs = FileSystems.newFileSystem(zipfile, App.class.getClassLoader());
        } catch (IOException e) {
            isValid = false;
            e.printStackTrace();
        } catch (Error error) {
            System.out.println(file + " is not valid");
            error.printStackTrace();
            isValid = false;
        }
        return isValid;
    }

    private String encodeHtml(String value) throws ValidationException {
        /*String safeValue = ESAPI.validator().getValidInput("An input parameter", value,
                "SafeStringRelaxed", 5000, true, false);*/
        return ESAPI.encoder().encodeForHTML(value);
    }

    private void getCount() {

        String line = "10";
        int n = Integer.parseInt(line);
        line = "203 204 205 206 207 208 203 204 205 206";

        Map<Integer, Integer> mapN = createFrequencyMap(line);
        line = "203 204 204 205 206 207 205 208 203 206 205 206 204";
        Map<Integer, Integer> mapM = createFrequencyMap(line);
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : mapM.entrySet()) {
            if (mapN.get(entry.getKey()) == null || mapN.get(entry.getKey()) < mapM.get(entry.getKey())) {
                list.add(entry.getKey());
            }
        }
        String result = list.stream().map(e -> e.toString()).collect(Collectors.joining(" "));
        System.out.println(result);
    }

    private Map<Integer, Integer> createFrequencyMap(String line) {
        String[] nArr = line.split("\\s");
        Map<Integer, Integer> mapN = new TreeMap<>();
        for (String numStr : nArr) {
            int num = Integer.parseInt(numStr);
            if (mapN.containsKey(num)) {
                mapN.put(num, mapN.get(num) + 1);
            } else {
                mapN.put(num, 1);
            }
        }
        return mapN;
    }

    public int topVotedCandidate(int[] persons, int[] times, int q) {
        TreeMap<Integer, Integer> timeVote = new TreeMap<>();
        Map<Integer, Integer> personVote = new HashMap<>();
        int[] topVoted = new int[]{0, 0};
        for (int i = 0; i < persons.length ; i++) {
            if (persons[i] > 0) {
                int votes = personVote.containsKey(i) ? personVote.get(i) : 0;
                votes += 1;
                personVote.put(i, votes);
                if (votes >= topVoted[1]) {
                    topVoted[0] = i;
                    topVoted[1] = votes;
                }
            }
            timeVote.put(times[i], topVoted[0]);
        }

        return timeVote.floorEntry(q).getValue();

    }

    private void testLeastFrequent() {
        int[] arr = {5,5,4};
        int res = findLeastNumOfUniqueInts(arr, 1);
    }

    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        List<Map.Entry<Integer, Long>> sortedList = Arrays.stream(arr).boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue())).collect(Collectors.toList());
        int count = 0;
        for(Map.Entry<Integer, Long> entry : sortedList) {
            int numCount = entry.getValue().intValue();
            if(k > 0) {
                int effectiveCount = numCount - k;
                count += (effectiveCount > 0 ? effectiveCount : 0);
                k -= numCount;
            } else {
                count += numCount;
            }
        }
        return count;
    }

    private void testKthAncestor() {
        int[] arr = {-1,0,0,1,1,2,2};
        Map<Integer, List<Integer>> tree = TreeAncestor(7, arr);
        int node = 3, k = 1;
        int h = getKthAncestor(tree, 3, 1);
        System.out.printf("%d parent of %d is : %d\n", k, node, h);
    }

    public Map<Integer, List<Integer>> TreeAncestor(int n, int[] parent) {
       Map<Integer, List<Integer>> TREE = new HashMap<>();
        for(int i = 0; i < parent.length; i++) {
            List<Integer> path = new ArrayList<>();
            if(TREE.containsKey(parent[i])) {
                path.addAll(TREE.get(parent[i]));
            }
            if(parent[i] != -1) {
                path.add(0, parent[i]);
            }
            TREE.put(i, path);
        }
        return TREE;
    }
    public int getKthAncestor(Map<Integer, List<Integer>> TREE, int node, int k) {
        return (TREE.containsKey(node) && TREE.get(node).size() > k-1) ? TREE.get(node).get(k-1) : -1;
    }

    private void testLargestDuplicateSubStr() {
        String str = longestDupSubstring1("banana");
        System.out.printf("%s\n", str);
    }
    private String lcp1(String source, int s, int t) {
        StringBuilder sb = new StringBuilder();
        while (s < source.length() && t < source.length() && source.charAt(s) == source.charAt(t)) {
            sb.append(source.charAt(s));
            s += 1;
            t += 1;
        }
        return sb.toString();
    }

    public String longestDupSubstring1(String S) {
        List<Integer> sorted
                = IntStream.range(0, S.length())
                .mapToObj(Integer::valueOf)
                .sorted((e1, e2) -> compare(S, e1, e2))
                .collect(Collectors.toList());

        String largestDup = "";
        for(int i = 0; i < sorted.size()-1; i++) {
            String lcp = lcp1(S, sorted.get(i), sorted.get(i+1));
            if(lcp.length() > largestDup.length()) {
                largestDup = lcp;
            }
        }
        return largestDup;

    }

    private Integer compare(String s, Integer e1, Integer e2) {
        int result = 0;
        while (e1 < s.length() && e2 < s.length() && result == 0) {
            result = Character.valueOf(s.charAt(e1)).compareTo(Character.valueOf(s.charAt(e2)));
            e1 += 1;
            e2 += 1;
        }
        return result == 0 ? e2.compareTo(e1) : result;
    }

    private String lcp(String source, String target) {
        StringBuilder sb = new StringBuilder();
        for(int s = 0, t = 0; s < source.length() && t < target.length() && source.charAt(s) == target.charAt(t); s++,t++) {
            sb.append(source.charAt(s));
        }
        return sb.toString();
    }

    public String longestDupSubstring(String S) {
        List<String> prefixList = new ArrayList<>();
        for(int i = 0; i < S.length(); i++) {
            prefixList.add(S.substring(i));
        }
        List<String> sorted
                = prefixList.stream().sorted((e1, e2) -> e1.compareTo(e2))
                .collect(Collectors.toList());
        int largest = Integer.MIN_VALUE;
        String largestDup = "";
       for(int i = 0; i < sorted.size()-1; i++) {
           String lcp = lcp(sorted.get(i), sorted.get(i+1));
           if(lcp.length() > largestDup.length()) {
               largestDup = lcp;
           }
       }
       return largestDup;
    }
    private void testTopologicalSort() {
        Map<Integer, Set<Integer>> courseDependencies
                = ImmutableMap.<Integer, Set<Integer>>builder()
                .put(0, Sets.newHashSet(1,2,3))
                .put(1, Sets.newHashSet(2))
                .put(3, Sets.newHashSet(2))
                .build();
        int count = getNumberOfSemesters(4, courseDependencies);
        System.out.printf("Count Semesters : %d\n", count);
    }
    private int  getNumberOfSemesters(int n, Map<Integer, Set<Integer>> courseDependencies) {
        int countSems = 0;
        int totalCourse = 0;
        boolean[] completed = new boolean[n];
        Set<Integer> independentCourses = fetchIndepnedentCourses(n, courseDependencies, completed);
        Queue<Integer> queue = new LinkedList<>();
        queue.addAll(independentCourses);
        while (!queue.isEmpty()) {
            countSems += 1;
            int count = queue.size();
            for(int i = 0; i < count; i++) {
                int course = queue.poll();
                if(completed[course]) {
                    continue;
                }
                completed[course] = true;
                totalCourse += 1;
                removeDependency(courseDependencies, course);
                Set<Integer> indCourses = fetchIndepnedentCourses(n, courseDependencies, completed);
                for(Integer indCourse : indCourses) {
                    if(!queue.contains(indCourse)) {
                        queue.add(indCourse);
                    }
                }
            }
        }
        return (totalCourse == n) ? countSems : -1;
    }

    private void removeDependency(Map<Integer, Set<Integer>> courseDependencies, int course) {
        for(Set<Integer> dependencies : courseDependencies.values()) {
            dependencies.remove(Integer.valueOf(course));
        }
    }

    private Set<Integer> fetchIndepnedentCourses(int n, Map<Integer, Set<Integer>> courseDependencies, boolean[] completed) {
        Set<Integer> course = new HashSet<>();
        for(int i = 0; i < n; i++) {
            if(!completed[i] && !courseDependencies.containsKey(i)) {
                course.add(i);
            } else if(!completed[i]){
                boolean isDependent = false;
                for(int depCourse : courseDependencies.get(i)) {
                    if(!completed[depCourse]) {
                        isDependent = true;
                        break;
                    }
                }
                if(!isDependent) {
                    course.add(i);
                }
            }
        }
        return course;
    }
    private void testUniqueName() {
        //String[] names = {"gta","gta(1)","gta","avalon"};
        String[] names = {"pes","fifa","gta","pes(2019)"};
        String[] res = getFolderNames(names);
        Arrays.stream(res).forEach(System.out::println);
    }
    public String[] getFolderNames(String[] names) {
        String[] result = new String[names.length];
        Map<String, Integer> map = new HashMap<>();
        for(int i = 0; i < names.length; i++) {
            String name = names[i];
            int num = 0;
            int bracesStart = name.lastIndexOf('(');
            int bracesEnd = name.lastIndexOf(')');
            if(bracesStart != -1 && bracesStart < bracesEnd+1) {
                String countStr = name.substring(bracesStart + 1, bracesEnd);
                try {
                    num = Integer.valueOf(countStr);
                } catch(Exception ex) {}
            }
            name = (bracesStart == -1) ? name : name.substring(0, bracesStart);
            if(!map.containsKey(name)) {
                result[i] = names[i];
                map.put(name, num+1);
            } else {
                num = (num == 0) ? map.get(name) : num;
                result[i] = name.concat(String.format("(%d)", num));
                map.put(name, num+1);
            }

        }
        return result;
    }

    private void testIteniary() {
        String[][] array = {
                    {"EZE","AXA"},
                {"TIA","ANU"},
                {"ANU","JFK"},
                {"JFK","ANU"},
                {"ANU","EZE"},
                {"TIA","ANU"},
                {"AXA","TIA"},
                {"TIA","JFK"},
                {"ANU","TIA"},
                {"JFK","TIA"}};
        List<List<String>> tickets = new ArrayList<>();
        for(String[] arr : array) {
            tickets.add(ImmutableList.of(arr[0], arr[1]));
        }
        List<String> iteneary = findItinerary(tickets);
        iteneary.stream().forEach(System.out::println);
    }

    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, List<String>> GRAPH = new HashMap<>();
        for(List<String> ticket : tickets) {
            GRAPH.computeIfAbsent(ticket.get(0), e -> new ArrayList<>()).add(ticket.get(1));
        }
        List<String> result = new ArrayList<>();
        String city = "JFK";
        List<String> flights = GRAPH.get(city);
        result.add(city);
        while(Objects.nonNull(flights) && !flights.isEmpty()) {
            Collections.sort(flights);
            for(String destCity : flights) {
                city = destCity;
                if(GRAPH.containsKey(city) && !GRAPH.get(city).isEmpty()) {
                    break;
                }
            }
            //city = flights.first();
            result.add(city);
            flights.remove(city);
            flights = GRAPH.get(city);
        }
        return result;
    }

    public void testMin() {
        int[] arr = {56,-21,56,35,-9};
        int val = shortestSubarray(arr, 61);
    }
    public int shortestSubarray(int[] A, int K) {
        int p1=0;
        int p2=0;
        int n = A.length;
        int res = Integer.MAX_VALUE;
        while(p1 < n && p2 < n) {
            int sum = 0;
            while(p2 < n && sum < K) {
                sum += A[p2++];
            }
            if(p2 == n && sum < K) {
                return res < Integer.MAX_VALUE ? res : -1;
            }
            int len = p2-p1;
            res = Math.min(res, len);
            while(p1 < p2) {
                sum -= A[p1];
                p1++;
                if(sum >= K) {
                    len = p2-p1;
                    res = Math.min(res, len);
                }
            }
            if(res == 1) {
                break;
            }
        }
        return res;
    }

    public int shortestSubarray1(int[] A, int K) {
        return 0;
    }


    private void getPivotAndStats(boolean isWeekly, boolean isSbu, boolean isDept) {
        Map<String, String> map = getFacetPivots(isWeekly, isSbu, isDept);
        List<String> facetPivots = map.entrySet().stream().map(e -> String.format("{!stats=%s}%s", e.getKey(), e.getValue())).collect(Collectors.toList());
        facetPivots.stream().forEach(System.out::println);
    }

    private Map<String, String> getFacetPivots(boolean isWeekly, boolean isSbu, boolean isDept) {
        Map<String, String> filters = new HashMap<>();
        String str = isWeekly ? "WM_WEEK" : "TARGET_DATE";

        if(isSbu) {
            filters.put("piv1", String.valueOf(str.concat(",").concat("SBU")));
        }
        if(isDept) {
            filters.put("piv2", String.valueOf(str.concat(",").concat("DEPARTMENT")));
        }
        filters.put("piv3", String.valueOf(str.concat(",").concat("CATEGORY")));
        return filters;
    }
}
