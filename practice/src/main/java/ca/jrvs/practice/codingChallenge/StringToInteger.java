package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/String-to-Integer-atoi-0aa0056312184b1fbc6fa4465776dc3b
 */

public class StringToInteger {

    /**
     * A method that converts a string to an integer using java built-in method
     * Time complexity will be O(n) where n is the length of the string
     * @param text the string to be converted to an integer
     * @return text as an integer
     */
    public int stringToInteger_1 (String text){
        int result;
        try{
            result = Integer.parseInt(text);
        }catch (NumberFormatException e){
            throw new RuntimeException("ERROR: Cannot convert string to integer");
        }
        return result;
    }
}
