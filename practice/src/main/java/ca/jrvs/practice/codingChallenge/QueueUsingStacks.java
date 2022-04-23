package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * ticket: https://www.notion.so/jarvisdev/Implement-Queue-using-Stacks-6c379167026041f8a6462bbeecd7eef6
 */

/**
 * Implementation of a queue using two stacks, the two methods implemented are for
 * push - Has a time complexity of O(n)
 * pop - Has a time complexity of O(1)
 */
public class QueueUsingStacks {
    private Stack<Integer> s1 = new Stack<>();
    private Stack<Integer> s2 = new Stack<>();

    public void push(int num){
        while(!s1.isEmpty()){
            s2.push(s1.pop());
        }
        s2.push(num);
        while(!s2.isEmpty()){
            s1.push(s2.pop());
        }
    }

    public int pop(){
        return s1.pop();
    }

}
