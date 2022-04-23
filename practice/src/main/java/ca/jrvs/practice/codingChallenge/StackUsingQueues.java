package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ticket: https://www.notion.so/jarvisdev/Implement-Stack-using-Queue-e4e70cdd8a634c6683362019f43d8934
 */

/**
 * This class implements a tack using 2 Queues, the two methods used are for:
 * push - Has a time complexity of O(1)
 * pop - Has a time complexity of O(n)
 */
public class StackUsingQueues {
    private Queue<Integer> q1 = new LinkedList<>();
    private Queue<Integer> q2 = new LinkedList<>();

    public void push(int num){
        q1.add(num);
    }

    public int pop(){
        while (q1.size() > 1){
            q2.add(q1.remove());
        }
        int popResult = q1.remove();
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
        return popResult;
    }
}
