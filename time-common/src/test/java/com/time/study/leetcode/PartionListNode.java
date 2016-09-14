package com.time.study.leetcode;

/**
 * @description: 分割链表使得小于某个值X的所有链表在左边
 * @author: yang.zhou
 * @version: 1.0.0
 * @since: 2016-09-14 11:51 AM
 */

public class PartionListNode {

    public static void main(String[] args){

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(4);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(2);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(2);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;

        int x = 3;

        ListNode result = partition(node1,x);

        System.out.println(result.val);

    }

    public static ListNode partition(ListNode head, int x) {

        if(head == null){
            return null;
        }

        ListNode smallDummy = new ListNode(0);
        ListNode bigDummy = new ListNode(0);
        ListNode small = smallDummy, big = bigDummy;

        while (head != null) {
            if (head.val < x) {
                small.next = head;
                small = head;
            } else {
                big.next = head;
                big = head;
            }
            head = head.next;
        }

        big.next = null;
        small.next = bigDummy.next;
        return smallDummy.next;

    }
}


class ListNode{

    int val;

    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
