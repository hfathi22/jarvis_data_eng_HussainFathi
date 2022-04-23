package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * ticket: https://www.notion.so/jarvisdev/Valid-Parentheses-f2025c6f97544a509df96c94ab553bce
 */

public class ValidParentheses {

    /**
     * This method implements a stack to solve the valid parentheses' problem
     * Has a time complexity of O(n) where n is the length of the inputted string
     * @param s string of parenthesis
     * @return true if the parenthesis are valid, false otherwise
     */
    public boolean stackImp(String s){
        Stack<Character> stack = new Stack<>();
        char[] s_array = s.toCharArray();
        for (char c:s_array){
            if (c == '('){
                stack.push(')');
            }
            else if (c == '{'){
                stack.push('}');
            }
            else if (c == '['){
                stack.push(']');
            }
            else if (stack.isEmpty() || stack.pop() != c){
                return false;
            }
        }

        if(!s.isEmpty()){
            return false;
        }
        return true;
    }

}
