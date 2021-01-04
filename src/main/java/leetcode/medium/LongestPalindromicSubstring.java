package leetcode.medium;

import java.util.Objects;

public class LongestPalindromicSubstring {

    public String longestPalindrome(String s) {
        if(Objects.isNull(s)) return "";
        if(s.length() == 1) return s;

        char[] array = s.toCharArray();
        int[][] dyn = new int[array.length][array.length];

        int max = 1;
        int maxIndexI = 0;
        int maxIndexJ = 0;

        // fill size 1 positions
        for(int i=0; i<array.length; i++) {
            dyn[i][i] = 1;
            if(i + 1 < array.length && array[i] == array[i+1]) {
                dyn[i][i + 1] = 2;
                max = 2;
                maxIndexI = i;
                maxIndexJ = i+1;
            }
        }

        for(int x=2; x < array.length; x++) {
            for (int i = 0; i + x < array.length; i++) {
                int j = i + x;
                dyn[i][j] = dyn[i + 1][j - 1] > 0 && array[i] == array[j]? j-i+1 : 0;
                if(dyn[i][j] > max) {
                    max = dyn[i][j];
                    System.out.println(String.format("[%s][%s]=%s -> [%s][%s]=%s", maxIndexI, maxIndexJ, dyn[maxIndexI][maxIndexJ], i, j, dyn[i][j]));
                    maxIndexI = i;
                    maxIndexJ = j;
                }
            }
        }
        return s.substring(maxIndexI, maxIndexJ+1);
    }

    public static void main(String[] args) {
        LongestPalindromicSubstring test = new LongestPalindromicSubstring();
        System.out.println(test.longestPalindrome("abba"));
        System.out.println(test.longestPalindrome("abbc"));
        System.out.println(test.longestPalindrome("ababc"));
        System.out.println(test.longestPalindrome("abababc"));
        System.out.println(test.longestPalindrome("aaa"));
    }
}
