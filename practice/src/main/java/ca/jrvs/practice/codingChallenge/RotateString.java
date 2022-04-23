package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Rotate-String-695e17f046f74767b039e84c8666754b
 */
public class RotateString {


    /**
     * This method solves the RotateString problem using the contains built in method
     * This approach has a time complexity of O(n)
     * @param s String that can become goal with after some number of shifts
     * @param goal
     * @return
     */
    public boolean rotateString(String s, String goal){
        if (s.length() != goal.length()){
            return false;
        }
        String text = s + s;
        return text.contains(goal);
    }
}
