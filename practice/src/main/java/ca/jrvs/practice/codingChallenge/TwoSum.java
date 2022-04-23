package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

/**
 * Ticket: https://www.notion.so/jarvisdev/Two-Sum-dbefe0f60ac3448cb581cb303ee0b44c
 */
public class TwoSum {

    /**
     * A brute force approach to the Two sum problem
     * The method has a time complexity of O(n^2), where n is the length of the nums list.
     * This time complexity is due to the nested for loop implementation
     * @param nums array of number
     * @param target target sum
     * @return the indicies of the two numbers that sums to the target, null otherwise
     */
    public int[] arrayImp(int[] nums, int target){
        int[] result = new int[2];

        for(int i=0; i<nums.length; i++){
            int val_1 = nums[i];
            for (int j=0; j< nums.length; j++){
                if (i != j){
                    int val_2 = nums[j];
                    int sum = val_1 + val_1;
                    if (sum == target){
                        result[0] = i;
                        result[1] = j;
                        return result;
                    }
                }

            }
        }
        return null;
    }

    /**
     * HashMap implementation of the twoSum problem. This approach takes advantage of the
     * constant time complexity of HashMaps
     * This method has a time complexity of O(n), where n is the length of the nums list
     * @param nums
     * @param target
     * @return
     */
    public int[] hashTableImp(int[] nums, int target) {
        Map<Integer, Integer> hashTable = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (hashTable.containsKey(diff)) {
                return new int[]{hashTable.get(diff), i};
            } else {
                hashTable.put(nums[i], i);
            }
        }
        return null;
    }
}



