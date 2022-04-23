package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/LinkedList-Cycle-c7d5ad391f0a44569c7da26377dcc36c
 */

public class LinkedListCycle {

    static class Node<E>{
        Node next;
        E value;

        Node(E value){
            this.value = value;
            this.next = null;
        }
    }

    /**
     * A method that solves the Linked List Cycle problem using the two pointer approach
     * This method had a time complexity of O(n)
     * @param head, pointer pointing to the head of the Linked List
     * @return ture if a cycle has been detected in the Linked List, false otherwise
     */
    public boolean twoPointer (Node head){
        if(head == null){
            return false;
        }

        Node slow = head;
        Node fast = head.next;


        while(fast != null || fast.next !=null){
            if (fast ==slow){
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;

        }
        return false;
    }
}
