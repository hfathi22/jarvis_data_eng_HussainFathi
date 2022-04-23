package ca.jrvs.practice.codingChallenge;
import java.util.Map;

/**
 * ticket: https://www.notion.so/jarvisdev/How-to-compare-two-maps-d2b028c4435541a29af15d87173ebe6c
 */
public class CompareTwoMaps {

    /**
     * A function that checks if two maps are equal
     * Have a time complexity of O(n) where n is the length of the shortest map
     * @param m1 Map to compare
     * @param m2 Map to compare
     * @return true if the two maps are equal, false if not
     */
    public boolean compareMaps(Map m1, Map m2 ){return m1.equals(m2);};

}
