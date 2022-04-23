package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarviscanada/Check-if-a-number-is-even-or-odd-4cbdd9acd9504c04b9b4ef0213f8084d
 */

public class OddEven {

    /**
     * Big-O: O(1)
     * Justification: it's an arithmetic operation
     * @param i number to check
     * @return "even" if the number is even and "odd" if the number is odd
     */
    public String oddEven(int i){
        return i % 2 == 0 ? "even" : "odd";
    }

    /**
     * Big-O: O(1)
     * Justification: it's an arithmetic operation
     * @param i number to check
     * @return "even" if the number is even and "odd" if the number is odd
     */
    public String oddEvenBit(int i){
       String binary = Integer.toBinaryString(i);
       if(binary.charAt(binary.length()-1) == '1'){
           return "odd";
       }
       else {
           return "even";
       }
    }
}
