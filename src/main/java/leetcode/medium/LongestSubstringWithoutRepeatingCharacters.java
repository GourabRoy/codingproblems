package leetcode.medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LongestSubstringWithoutRepeatingCharacters {

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charFilter = new HashMap<>();
        if(Objects.isNull(s) || s.length() == 0) return 0;

        int[] maxTillIndex = new int[s.length()];
        maxTillIndex[0] = 1;
        charFilter.put(s.charAt(0), 0);

        for(int i=1; i<s.length(); i++) {
            char c = s.charAt(i);
            if(!charFilter.containsKey(c)) {
                maxTillIndex[i] = maxTillIndex[i-1] + 1;
            } else {
                int distanceFromPreviousInstance = i - charFilter.get(c);
                if(distanceFromPreviousInstance < maxTillIndex[i-1]) {
                    maxTillIndex[i] = distanceFromPreviousInstance;
                } else if (distanceFromPreviousInstance == maxTillIndex[i-1]) {
                    maxTillIndex[i] = maxTillIndex[i-1];
                } else {
                    maxTillIndex[i] = maxTillIndex[i-1] + 1;
                }
            }
            charFilter.put(c, i);
        }

        return findMax(maxTillIndex);
    }

    private int findMax(int[] arr) {
        printArray(arr);
        return Arrays.stream(arr).max().getAsInt();
    }

    void printArray(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<arr.length; i++) {
            sb.append(arr[i]).append(" ");
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters test = new LongestSubstringWithoutRepeatingCharacters();
        System.out.println(test.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(test.lengthOfLongestSubstring("abcabded"));
        System.out.println(test.lengthOfLongestSubstring("abba"));
        System.out.println(test.lengthOfLongestSubstring("dvdf"));
        System.out.println(test.lengthOfLongestSubstring("bb"));
        System.out.println(test.lengthOfLongestSubstring("a"));
    }
}
