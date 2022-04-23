package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-55a0ef35bcbd4391bfe423fad6a13bb7
 */

public class FibonacciNumber {

    /**
     * A method that recursively computes the nth Fibonacci number.
     * The method has a time complexity of O(2^n), where n=num
     * The method has a space complexity of O(n)
     * @param num integer
     * @return the nth Fibonacci number (n=num)
     */
    public int recursionImp(int num){
        if (num==0){
            return 0;
        }
        if (num==1){
            return 1;
        }
        return recursionImp(num-1) + recursionImp(num-2);
    }

    /**
     * A method that dynamically computes the Fibonacci number
     * Time complexity of O(n), where n=num
     * Space complexity of O(n), where n=num because we're storing all the Fibonacci numbers in an array
     * @param num
     * @return the nth Fibonacci number (n=num)
     */
    public int arrayImp(int num){
        int[] fib = new int[num+1];
        fib[0] = 0;
        fib[1] = 1;

        for(int i=2; i<num+1; i++){
            fib[i] = fib[i-1] + fib[i-2];
        }

        return fib[num];
    }
}
