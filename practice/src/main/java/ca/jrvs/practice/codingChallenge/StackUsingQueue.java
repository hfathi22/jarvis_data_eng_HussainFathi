package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ticket: https://www.notion.so/jarvisdev/Implement-Stack-using-Queue-e4e70cdd8a634c6683362019f43d8934
 */

/**
 * This class implements a tack using 1 Queues, the two methods used are for:
 * push - Has a time complexity of O(1)
 * pop - Has a time complexity of O(n)
 */
public class StackUsingQueue {
    Queue<Integer> queue = new LinkedList<>();

    public void push (int num){
        queue.add(num);
        for(int i=1; i< queue.size(); i++){
            int temp = queue.remove();
            queue.add(temp);
        }
    }

    public int pop(){
        return queue.remove();
    }
}
