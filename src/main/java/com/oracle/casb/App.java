package com.oracle.casb;

import com.google.common.collect.ImmutableSet;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.ValidationException;
import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.Field;
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
        app.testCharSorted();
        app.testAnagram();
        app.testImmutableSet();
        try {
            app.testQuarterFromDates();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
}
