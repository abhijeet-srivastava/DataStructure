package com.walmart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Permutations {
    public static void main(String[] args) {
        Permutations pr = new Permutations();
        pr.testPermGenerate();
    }

    private void testPermGenerate() {

        int[] arr = {3,5,6,9};
        List<List<Integer>> permutations = generateAllPermutations1(arr);
        final int[] i = {1};
        permutations.forEach(e -> {
            System.out.println("Permutation: "+ i[0]);
            i[0] += 1;
            System.out.printf("%s\n", e.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        });
    }

    private List<List<Integer>> generateAllPermutations1(int[] arr) {
        List<Integer>  permutation = new ArrayList<>();
        List<List<Integer>> permutations = new ArrayList<>();
        permutations.add(permutation);
        getPermutations(arr, permutations, 0);
        return permutations;
    }

    private void getPermutations(int[] arr, List<List<Integer>> permutations, int index) {
        if(index >= arr.length) {
            return;
        }
        List<List<Integer>> currentPermutations = new ArrayList<>();
        for(List<Integer> prevPerm: permutations) {
            List<Integer> currentPerm = new ArrayList<>(prevPerm);
            currentPerm.add(arr[index]);
            currentPermutations.add(currentPerm);
        }
        permutations.addAll(currentPermutations);
        getPermutations(arr, permutations, index+1);
    }

    private List<List<Integer>> generateAllPermutations(int[] arr) {
        int len = arr.length;
        List<List<Integer>> permutations = new ArrayList<>();
        for(int i = 0; i <(1 <<len); i++) {
            List<Integer> permutation = new ArrayList<>();
            for(int j = 0; j < len; j++) {
                if((i & (1 << j)) != 0) {
                    permutation.add(arr[j]);
                }
            }
            permutations.add(permutation);
        }
        return permutations;
    }
}
