package com.oracle.casb.leetcode;

import com.google.common.collect.ImmutableList;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created By : abhijsri
 * Date  : 07/08/18
 **/
public class LeetCodeContest96 {
    public static void main(String[] args) {
        LeetCodeContest96 contest = new LeetCodeContest96();
        //contest.testEncodedString();
        //contest.testNumBoats();
        //System.out.println(contest.surfaceArea(new int[][]{{1,2},{3,4}}));
        //contest.testMinBaseYears();
        //contest.testIsEquivalent();
        contest.testLargeSum();
    }

    private void testLargeSum() {
        List<Integer[]> numList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src/main/resources/LargeNumbers.txt"))))) {
            reader.lines().forEach(
                    line -> numList.add(createIntegerArray(line))
            );
            System.out.printf("List length %d\n", numList.size());
            List<Integer> sum = getSum(numList);
            System.out.printf("%s\n", sum.subList(0,10).stream().map(String::valueOf).collect(Collectors.joining("")));
            System.out.printf("%s\n", sum.stream().map(String::valueOf).collect(Collectors.joining("")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getSum(List<Integer[]> numList) {
        List<Integer> result = new ArrayList<>();
        int sum = 0;
        for (int i = 1; i <= numList.get(0).length; i++) {
            for (Integer[] arr : numList) {
                sum += arr[arr.length - i];
            }
            result.add(0, sum%10);
            sum /= 10;
        }
        while(sum > 0) {
            result.add(0, sum%10);
            sum /= 10;
        }
        return result;
    }

    private Integer[] createIntegerArray(String line) {
        Integer[] array = new Integer[line.length()];
        int i = 0;
        for (char ch : line.toCharArray()) {
            array[i++] = Character.getNumericValue(ch);
        }
        return array;
    }

    private void testIsEquivalent() {
        //System.out.println(isEqual("bjoxmkktjsmxzemnrfjr", "jgdbdjiasgzyksjkidau"));
        System.out.println(numSpecialEquivGroups(new String[] {"isqc","owem","wted","rinx","kgwo","dnfz","arjn","osbl","bsxf","dqng","ebyt","jgwe","ugez","bnnv","mcli","mdcs","lfxv","zdfp","rmen","grnz","dwbp","wxvg","denc","foee","tuse","bxcu","azog","kmln","jkpl","favw","nsgn","yust","rzki","msid","wojf","miiu","amyl","tahc","jsdo","xjlk","rqmo","xajm","ajvq","gacb","uzax","pfdv","yzyl","pgbl","tsyh","bkwc","lhhm","bicc","ulug","ruxn","wurr","yrni","pjdm","wmbx","xzvh","fbzx","oeuy","rxhw","fyua","bmna","iewf","fmax","wvhn","bcom","fers","zrvo","cneh","xekb","mqwu","tmdo","apjb","kjso","zcdq","hdlw","eqtt","exdm","mxel","ykgs","lumw","uret","blvl","opal","bkep","ttxa","lzrr","cqsa","yhfl","typs","djxh","yrep","ywlr","ccnt","sgrb","ttqe","ttue","nmfj","ftig","itsi","zvhu","meag","xvvw","efoc","twfn","qxae","wmjg","amzg","mpja","bdai","frso","ipyc","ifdr","lazp","hyup","vhkt","nmxd","icfs","cfqp","fkko","mmio","byqa","ndgv","grrh","ekal","vgid","lcli","zwcg","diap","lxdh","wyim","qxnp","ncww","grnx","voht","hvuy","jjth","nwjh","xfdj","envd","aafo","qmxd","kzfb","qnwy","vpcl","ouez","hzqa","ezvy","fciu","boei","paau","npxh","cicu","xdat","bmny","xkwz","tiks","hios","ovgr","hdrj","fcja","tlor","ylnr","lehi","oprm","gzcl","xguq","tmjv","aaxz","xueg","nxbx","rfgu","worx","itfi","kkid","vhmh","upui","ofrz","ilfu","qsna","mqsg","ebup","pzsz","nxxq","utef","gbqc","fiue","rjnt","fkwc","glfz","zgna","xgik","ajyr","figf","eait","bwbf","kcvf","uelc","gkzi","bzxg","zosj","pcqy","nmpd","tqzx","kxkt","mmpp","pusv","lqhk","tygg","zvsj","bzir","dimj","hcna","qowh","pbwe","awvl","vqtu","rnjc","wala","ahzx","dkir","ifjg","ehmv","spfu","jhgb","xzen","kjzz","mqfd","ngcf","ombk","uryd","ukug","mfqf","cckq","iirz","zpon","umim","wexl","oqen","pmbz","ncnp","gaom","hlkd","ansi","sdgr","skgj","ectt","scoi","glxi","hpsw","zqqr","bpmy","qizb","ooyp","krsh","fjzp","xxeb","cxrd","atdr","gkpa","whuj","efco","chyz","wkqu","lbft","fdck","baaf","ldnj","fymt","rbrn","jqhe","vqsm","btzp","uyor","sqdc","wrab","bcak","yjgc","cftq","yjwu","tdcn","ezan","flls","cvid","pifn","foxo","gwhl","quhe","mxij","enph","hiww","elbs","wgnl","jghd","tjbc","erpf","wvsj","xcon","airp","cnvq","snwr","bheb","nzxy","oqps","wybc","mevi","xmrz","erim","grpo","xkkb","nvjl","djdu","wwem","vjrd","ygtu","izdc","cjzv","xrgo","wqtw","lcmm","scts","ekic","xfog","girv","zqzz","emvq","uxoa","ptpr","bqyr","ewyz","soie","agyc","youk","nbhl","yhkr","jtgc","cfsu","ahcd","zgvt","udap","hkzt","gvxo","dlyb","tbeb","uqpy","tqqx","excv","hmtl","wuoq","dqmf","vgja","whsv","bpck","mjjh","kudv","rosx","cxpc","oski","lmyi","rzhu","hfsi","cpjb","winr","sxwe","woww","liri","vqjr","zygh","nxrl","zifj","hqjn","arfr","sbpl","vdcx","tbvj","jjca","pflg","pdff","snmi","eqfi","jqav","zeqz","errg","xkxr","ldds","fzyl","awmt","sveo","ahhc","byoh","nihj","rrbh","jmkg","otlg","ridf","bqza","yusc","glyz","mcvx","iacb","rtrz","quld","aouq","knuj","gbfr","gonp","icrz","vfsp","avfh","wznr","fwfb","jwmd","dnih","rltd","pndr","rwzi","myfx","aodv","pjbm","lnop","fvxv","rxto","zssv","wtbq","fgld","rkgv","fpuj","elwn","fmla","djub","cokq","ckxh","zvau","qoxe","ampt","hztz","rgxf","dulf","yihb","yxmw","zmcu","tago","fvcs","xbaz","dqnz","dtzb","dycj","puvo","oxls","ktdu","tluw","aquq","upsh","tbkf","boqk","dxuo","vcut","oybv","mrak","nhyq","arfp","ffcm","spdo","fmes","vwaj","yzut","lgez","mgrj","vghp","hvjy","oyld","mdcx","drry","tzic","lgot","olnm","bnhc","lsap","hvtc","xrit","kkig","kuec","faof","xeqz","zroq","enzv","riim","jzzw","jszh","wsru","zoiy","rlui","wjmq","sjdh","izwv","qvlk","nppz","fbop","sane","wmtx","ogdq","xjmd","zidh","bmgh","ywav","rylj","uuzs","ihkz","dyap","iqcb","maze","qbvq","zoqb","czti","dcee","lgqd","teji","beug","tkui","sxji","hvrc","dqct","qaoq","natr","aega","lusm","miaa","nill","bmuw","mohc","gvku","ecpz","wxct","kdlu","nlui","rgxn","mdtg","hrcy","bkgc","qslc","wjav","bexk","dqsh","yqbg","mvgy","aejh","ohka","zbms","dqry","mqvo","ersg","mxjt","csyo","qcdy","ppth","ckaj","lfrf","ykbv","wmxy","wkek","vhui","svyz","tvom","qbci","vtue","gajp","qonc","xwlh","ojwv","uzkd","mmeh","zvzk","cyaj","ndbh","crma","mqok","iwco","dpzc","zwny","ssaz","bkok","ivlf","dyfv","sjwr","qokw","doul","vqtg","vqkz","llgi","nqqu","izwg","ndvj","dpkw","kzma","hcak","hkaa","atzs","ofxg","dyju","xvkc","kazi","bkps","aipf","fart","nmng","gstc","cnig","mqwz","srcr","eerw","gksq","uycm","qxxp","xqnv","voxk","qfta","eujl","rugd","sgqn","liwq","fsoq","dcxd","pgbm","hyqo","ksnp","cfmu","gwjd","fmkh","qull","rxlp","uljf","xibv","jdqv","leba","qrsy","wfeg","jcnj","cfzf","nvid","biiy","bkuz","jqdb","dcjd","dqgs","iwwx","zwho","zliq","ufwy","ynlk","cmtl","vziz","zyvf","njmn","gtlk","xcml","dofz","hhsd","iqeb","nljd","wlto","nblt","xtpd","dqqk","duil","uzio","rlwf","celu","fdtr","txaa","qvaz","qrhe","chxq","vsas","kemy","xyvd","ltel","wmlr","ibhs","cunn","pygi","uvvr","ajcg","dova","vnns","zhsl","avqo","bafp","cbuy","ycdn","ugft","zftd","spsc","lbbg","tmvw","sexd","nnir","xpfn","estp","hmwl","htty","zgzn","wvif","imgb","xyfu","ikjz","knon","pqhg","crjs","pffo","cboq","ypkg","oxpg","esei","tcin","poul","ayil","wwsr","trom","pypj","hibh","nkqr","nmtn","ytxd","vhzs","wbbd","xydy","wmzg","qujl","wzdo","ijyw","nody","piqh","wahv","hsob","eurz","zpco","gnic","jcil","icmu","xoqe","ogdh","xfjt","azmx","vxex","shws","rrua","cocu","cvfl","oltk","xion","agxm","zexz","flyx","dlox","yudp","ksfn","vpjz","cxtf","hwit","guie","hceb","umox","nsfz","fjpa","uuym","hwqw","htxl","plgv","vhdx","teez","eamt","bvim","mjqq","epir","njva","jtwj","gjtf","xlpg","xdwt","pxwq","wntk","jawv","xcdo","qduj","cdce","lepv","pcgo","mjpx","jzcn","ctli","avxi","uskh","rjzh","huek","nebq","vkqv","bumb","evxb","xcln","higc","uobl","coje","pijw","ggnm","qloq","zkyn","wvct","cnfk","zoal","jghf","pdco","lfjs","maqw","srwn","vysx","xlqe","juua","cdee","mgsi","weez","icue","efwa","ihkv","xqlc","eltc","iswi","mfao","ajni","bldw","wmhj","lmom","pyur","hjyi","bvyo","hkcx","gvgm","dzex","kitt","nfwv","ultg","lyzw","nnib","yxsb","ygmp","nkcb","rqhh","aoua","qkhy","xxet","hqgx","enxu","tzzt","dpmu","miwl","nlqo","proo","ijou","qlmt","jdnf","hznf","qkib","vocv","dwcg","hpqr","cbvb","uyya","oemu","vfag","qqsk","dtmn","nptt","szmj","mgqm","lpwb","mttq","oieq","kaut","jmve","unza","hxob","dveh","jxbj","pyuf","kluc","gwbw","rpuw","tdit","zmqq","rlly","pmno","oqqk","hyfp","iyxb","utkz","vutq","tdoq","fnkq","cgjf","byap","gyqj","vgql","zhia","vdfn","brpf","nvqa","zfmg","dkcr","aglr","bjfa","ngor","kghd","uhnq","ioid","ylcd","whvu","zqlg","adic","rqkj","lsnr","uvgd","qcue","wbbn","vyad","mcyt","girt","hnuj","guuf","nrjx","nwbg","iddb","piez","ztvs","tzwx","oyfz","eech","tczp","ytzy","guel","byht","lyjn","nwvg","pgno","iudl","ugft","onkn","mohc","yzew","cftx","lbbg","ssaz","jnar","phtp","cxwt","ksfn","hoqy","dosp","guel","girv","dwbp","ridf","eyvz","tsep","chen","cyub","wzdo","sijx","sihf","gmgv","mscd","wvsh","vwtm","izwg","vckf","skgj","zzzq","wsru","okbm","spuh","upsh","agzm","qgsn","lbbg","bjpm","cqxh","ntcc","lukd","txaa","cgjf","jwpi","cyub","shkr","amzg","vtuc","uuzs","syqr","vxcd","mzsj","ersg"}));
        System.out.println(numSpecialEquivGroups(new String[] {"a","b","c","a","c","c"}));

    }

    private void testMinBaseYears() {

        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/A-large-practice.in"))) {
            AtomicInteger i = new AtomicInteger(1);
            stream.skip(1).forEach(
                    line -> System.out.printf("Case #%d: %s\n" , i.getAndIncrement(), getMaxValue(line))
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src/main/resources/A-large-practice.in"))))) {
            reader.lines().skip(1).forEach(
                    line -> System.out.println(line + "<==>" + getMaxValue("abcdefghijklmno"))
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private void testEncodedString() {
        System.out.println(decodeAtIndex("leet2code3", 10));
    }

    private void testNumBoats() {
        int count = numRescueBoats(new int[]{3,5,3,4}, 5);
        System.out.println("Total number of boats :" + count);
    }

    private int numRescueBoats(int[] people, int limit) {
        Integer[] wrappedInts = Arrays.stream(people)
                .boxed()
                .toArray(Integer[]::new);
        Arrays.sort(wrappedInts, Comparator.reverseOrder());
        int numBoat = 0;
        int start = 0, end = wrappedInts.length -1;
        while (end >= start) {
            if ((limit - wrappedInts[start]) >= wrappedInts[end]) {
                --end;
            }
            ++start;
            numBoat += 1;
        }
        return numBoat;
    }

    public boolean stoneGame(int[] piles) {
        int N = piles.length;
        // dp[i+1][j+1] = the value of the game [piles[i], ..., piles[j]].
        int[][] dp = new int[N+2][N+2];

        for (int size = 1; size <= N; ++size) {
            for (int i = 0; i+size <= N; ++i) {
                int j = i + size - 1;
                int parity = (N - i -j) % 2;
                if (parity == 1) {//Alex
                    dp[i+1][j+1] = Math.max((piles[i] + dp[i+2][j+1]), (piles[j] + dp[i+1][j]));
                } else {// Lee
                    dp[i+1][j+1] = Math.max((-piles[i] + dp[i+2][j+1]), (-piles[j] + dp[i+1][j]));
                }

            }
        }
        return dp[1][N] > 0;
    }
    public String decodeAtIndex(String S, int K) {
        char[] array = S.toCharArray();
        if (K == 1) {
            return new String(new char[] {array[0]});
        }
        StringBuilder  sb  = new StringBuilder();
        int currentLength = 0;
        for (char ch : array) {
            if (sb.length() >= K) {
                return  new String(new char[] {sb.charAt(K-1)});
            } else if (!Character.isDigit(ch)) {
                sb.append(ch);
                currentLength += 1;
            } else  {
                String str = sb.toString();
                int value = Integer.valueOf(new String(new char[] {ch}));
                int len = sb.length();
                for (int i = 1; i < value; i++) {
                    if (sb.length() >= K) {
                        return  new String(new char[] {sb.charAt(K-1)});
                    } else {
                        sb.append(str);
                    }
                }

            }
        }
        return    new String(new char[] {sb.charAt(K-1)});
    }

    public int surfaceArea(int[][] grid) {
        int rows = grid.length;
        int columns  = grid[0].length;
        int area = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int n = grid[row][col];
                int currArea = (n * 4) + (n > 0 ? 2 : 0);
                if (row > 0) {
                    currArea -=  2 * ((grid[row][col] < grid[row-1][col]) ? grid[row][col] : grid[row-1][col]);
                }
                if (col > 0) {
                    currArea -= 2 * ((grid[row][col] < grid[row][col-1]) ? grid[row][col] : grid[row][col-1]);
                }
                area += currArea;
            }
        }
        return area;
    }

    public String getMaxValue(String s) {
        Map<Character, Integer> map = getSymbolValueMap(s);
        BigInteger result = BigInteger.ZERO;
        int base = map.size();
        int exp = 1;
        char[] arr = s.toCharArray();
        for (int index = arr.length - 1; index >= 0; index--) {
            result = result.add(BigInteger.valueOf(map.get(arr[index]) * exp));
            exp *= base;
        }
        return result.toString();
    }

    private Map<Character, Integer> getSymbolValueMap(String s) {
        Map<Character, Integer> map = new HashMap<>();
        char[] arr = s.toCharArray();
        int index = 1;
        map.put(arr[0], index++);
        int i = 1;
        while (i < arr.length) {
            if (arr[i] != arr[0]) {
                map.put(arr[i], 0);
                break;
            }
            i += 1;
        }
        for (; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                map.put(arr[i], index++);
            }
        }
        return map;
    }

    public int numSpecialEquivGroups(String[] A) {
        Set<String> seen = new HashSet();
        for (String S: A) {
            int[] count = new int[52];
            for (int i = 0; i < S.length(); ++i)
                count[S.charAt(i) - 'a' + 26 * (i % 2)]++;
            seen.add(Arrays.toString(count));
        }
        return seen.size();
    }
    public int numSpecialEquivGroups1(String[] A) {
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            insertInList(list, A[i]);
        }
        return list.size();
    }

    private void insertInList(List<List<String>> list, String s) {
        List<String> innerList = null;
        for (List<String> currList : list) {
            if (isEqual(s, currList.get(0))) {
                innerList = currList;
                break;
            }
        }
        if (innerList == null) {
            innerList = new ArrayList<>();
            list.add(innerList);
        }
        innerList.add(s);

    }

    private boolean isEqual(String s, String s1) {
        if (s == s1
                || (s!= null && s.equals(s1)))  {
            return true;
        } else if (s.length() != s1.length()) {
            return false;
        }
        List[] listArr = fetchEvenOdd(s);
        List[] listArr1 = fetchEvenOdd(s1);
        return isEqualList(listArr[0], listArr1[0])
                && isEqualList(listArr[1], listArr1[1]);
    }
    private boolean isEqualList(List<Character> list, List<Character> list1) {
        if (list.size() != list1.size()) {
            return false;
        }
        int[] arr = new int[26];
        for (char ch : list) {
            int index = ch - 'a';
            arr[index] += 1;
        }
        for (char ch : list1) {
            int index = ch - 'a';
            arr[index] -= 1;
        }

        boolean isEqual = true;
        for (int ch : arr) {
            if (ch != 0) {
                isEqual = false;
                break;
            }
        }
        return isEqual;
    }

    private List[] fetchEvenOdd(String s) {
        List<Character> even = new ArrayList<>();
        List<Character> odd = new ArrayList<>();
        boolean isEven = false;
        for (char ch : s.toCharArray()) {
            if (isEven) {
                even.add(ch);
            } else {
                odd.add(ch);
            }
            isEven = !isEven;
        }
        return new List[]{odd, even};
    }
}
