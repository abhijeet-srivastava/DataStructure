package com.oracle.casb.leetcode;

public class LinkListReorder {


    public static void main(String[] args) {
        LinkListReorder llr = new LinkListReorder();
        llr.testReorder();
    }

    private void testReorder() {
        ListNode head = createList(3);
        print(head);
        reorderList(head);
        print(head);
    }



    ListNode mainList;
    ListNode resultCurr;
    private boolean reorderListRec(ListNode head) {
        if(head.next == null) {
            ListNode tmp = mainList.next;
            mainList.next = head;
            resultCurr = head;
            mainList = tmp;
            return (mainList == head) ;
        }
        boolean ret = reorderListRec(head.next);
        if(!ret) {
            if(mainList == head) {
                resultCurr.next = head;
                head.next = null;
                ret = true;
            } else if(mainList.next == head) {
                resultCurr.next = mainList;
                head.next = null;
                ret = true;
            } else {
                ListNode tmp = mainList.next;
                resultCurr.next = mainList;
                mainList.next = head;
                mainList = tmp;
                resultCurr = head;
            }
        }
        return ret;
    }
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        mainList = head;
        reorderListRec(head);
    }
    private ListNode createList(int n) {
        ListNode head = null;
        ListNode current = null;
        for(int i = 1; i <= n; i++) {
            ListNode node = new ListNode(i);
            if(head == null) {
                head = node;
                current = node;
            } else {
                current.next = node;
                current = current.next;
            }
        }
        return head;
    }
    private void print(ListNode head) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        while(head != null) {
            System.out.printf("%d", head.val);
            head = head.next;
            if(head != null) {
                System.out.printf("->");
            }
        }
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     * Definition for singly-linked list.
     * */
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

}
