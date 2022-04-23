package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Middle-of-the-Linked-List-2ec46cd6a07541bca027b807574f2dec
 */
public class MiddleOfLinkedList {
    static class Node<E>{
        Node next;
        E value;

        Node(E value){
            this.value = value;
            this.next = null;
        }
    }

    /**
     * A method that return the middle node in a linked list
     * using a slow and fast pointers
     * This approach has a time complexity of O(n)
     * @param head first node of the linked list
     * @return the middle node
     */
    public Node getMiddleNode(Node head){
        Node slow = head;
        Node fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
