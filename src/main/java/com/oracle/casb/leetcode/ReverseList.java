package com.oracle.casb.leetcode;

import com.oracle.casb.common.ListNode;

import java.util.Random;

public class ReverseList {
    private static final Integer MAX_VALUE = 99;
    private static final Integer MIN_VALUE = 1;
    private static final Random RAND_GEN = new Random(50);

    public static void main(String[] args) {
        ReverseList rl = new ReverseList();
       // rl.testReverseRecursive();
        rl.testReverseBetween();
    }

    private void testReverseBetween() {
        ListNode head = createLinearList(5);
        printList(head);
        ListNode reverse = reverseBetween(head, 2, 4);
        printList(reverse);
    }

    private void testReverseRecursive() {
        ListNode head = createList(10);
        printList(head);
        ListNode reverse = reverse(head);
        System.out.println("After reversal ---");
        printList(reverse);
    }

    private void printList(ListNode head) {
        while (head != null && head.next != null) {
            System.out.printf("%d, ", head.val);
            head = head.next;
        }
        if(head != null) {
            System.out.printf("%d\n", head.val);
        }
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode tmp = head;
        int curr = 1;
        while (curr < m && tmp != null) {
            tmp = tmp.next;
            curr += 1;
        }
        if (tmp == null) {
            return head;
        }
        ListNode[] lftArr = {tmp};
        reverseAndRecurse(tmp, lftArr, n, curr);
        return head;
    }

    private void reverseAndRecurse(ListNode right, ListNode[] lftArr, int n, int curr) {
        if (n == curr || lftArr[0] == right || lftArr[0].next == right) {
            return;
        }
    }

    public ListNode reverse(ListNode head) {
        ListNode[] arr = new ListNode[1];
        reverseList(head, arr);
        head.next = null;
        return arr[0];
    }

    private ListNode reverseList(ListNode head, ListNode[] arr) {
        if (head.next == null) {
            arr[0] = head;
            return head;
        }
        ListNode tmp = reverseList(head.next, arr);
        tmp.next = head;
        return head;
    }

    private ListNode createLinearList(int count) {
        ListNode head = new ListNode(1);
        ListNode tmp = head;
        for(int i = 2; i <= count; i++) {
            ListNode node = new ListNode(i);
            tmp.next = node;
            tmp = tmp.next;
        }
        return head;
    }
    private ListNode createList(int count) {
        ListNode head = new ListNode(MIN_VALUE + RAND_GEN.nextInt(MAX_VALUE));
        ListNode tmp = head;
        for(int i = 1; i < count; i++) {
            ListNode node = new ListNode(MIN_VALUE + RAND_GEN.nextInt(MAX_VALUE));
            tmp.next = node;
            tmp = tmp.next;
        }
        return head;
    }

}
