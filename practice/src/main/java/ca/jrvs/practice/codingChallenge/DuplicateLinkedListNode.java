package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;
import java.util.Set;

public class DuplicateLinkedListNode {

    static class Node<E>{
        Node next;
        E value;

        Node(E value){
            this.value = value;
            this.next = null;
        }
    }

    public Node removeDuplicates (Node head){
        Set nodes = new HashSet();
        Node curr = head;
        Node prev = null;
        while (curr != null){
            if (!nodes.contains(curr.value)){
                nodes.add(curr.value);
                curr = curr.next;
                prev = curr;
            }
            else {
                prev.next = curr.next;
                curr = curr.next;
            }
        }
        return head;
    }

}
