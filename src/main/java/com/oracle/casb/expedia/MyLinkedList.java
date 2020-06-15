package com.oracle.casb.expedia;


public class MyLinkedList {

    public static void main(String[] args) {
        MyLinkedList mll = new MyLinkedList();
        mll.addAtHead(7);
        mll.addAtHead(2);
        mll.addAtHead(1);
        mll.addAtIndex(3, 0);
        mll.deleteAtIndex(2);
        mll.addAtHead(6);
        mll.addAtTail(4);
        System.out.printf("%d\n", mll.get(4));
    }

    private ListNode head;
    /** Initialize your data structure here. */
    public MyLinkedList() {
    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        int i = 0;
        ListNode tmp = head;
        while (tmp != null && i < index) {
            tmp = tmp.getNext();
            i += 1;
        }
        return (tmp == null) ? -1 : tmp.getVal();
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        ListNode node = new ListNode(val);
        if (head == null) {
            head = node;
        } else {
            node.setNext(head);
            head.setPrev(node);
            head = node;
        }
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        ListNode node = new ListNode(val);
        if (head == null) {
            head = node;
        } else {
            ListNode tmp = head;
            ListNode nxt = tmp.getNext();
            while (nxt != null) {
                tmp = nxt;
                nxt = tmp.getNext();
            }
            tmp.setNext(node);
            node.setPrev(tmp);
        }
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        ListNode tmp = head;
        int i = 0;
        while (tmp != null && i < index) {
            tmp = tmp.getNext();
            i += 1;
        }
        if (tmp != null) {
            ListNode node = new ListNode(val);
            node.setNext(tmp);
            node.setPrev(tmp.getPrev());
            if(tmp.getPrev() != null) {
                tmp.getPrev().setNext(node);
            }
            tmp.setPrev(node);
        }
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        ListNode tmp = head;
        int i = 0;
        while (tmp != null && i < index) {
            tmp = tmp.getNext();
            i += 1;
        }
        if(tmp != null) {
            ListNode prev = tmp.getPrev();
            ListNode nxt = tmp.getNext();
            if (nxt != null) {
                nxt.setPrev(prev);
            }
            if(prev != null) {
                prev.setNext(nxt);
            }
        }
    }

    private class ListNode {
        private int val;
        private ListNode next;
        private ListNode prev;

        public ListNode(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        public ListNode getPrev() {
            return prev;
        }

        public void setPrev(ListNode prev) {
            this.prev = prev;
        }
    }
}
