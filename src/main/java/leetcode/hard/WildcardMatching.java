package leetcode.hard;

import java.util.Objects;

public class WildcardMatching {

    public boolean isMatch(String s, String p) {
        if (Objects.isNull(p) || p.length() == 0 && s.length() > 0) return false;
        if (Objects.isNull(s)) return false;
        if (s.length() == 0) {
            return p.replaceAll("\\*", "").length() == 0;
        }

        boolean[][] dyna = new boolean[p.length()][s.length()];

        int k = 0;
        int[] kArray = new int[p.length()];

        // filling out first row
        dyna[0][0] = isCharacterMatch(s.charAt(0), p.charAt(0));
        for (int j = 1; j < s.length(); j++) {
            dyna[0][j] = p.charAt(0) == '*';
        }
        k = isCharacter(p.charAt(0)) ? k + 1 : k;
        kArray[0] = k;

        // fill out rest of dynamic matrix
        for (int i = 1; i < p.length(); i++) {
            int jStart = p.charAt(i) == '*' ? Integer.max(0, k - 1) : k;
            for (int j = jStart; j < s.length(); j++) {
                boolean isCharMatch = isCharacterMatch(s.charAt(j), p.charAt(i));
                if (p.charAt(i) == '*') {
                    dyna[i][j] = getDynaValue(dyna, i, j - 1)
                            || getDynaValue(dyna, i - 1, j)
                            || getDynaValue(dyna, i - 1, j - 1);
                } else if (isCharMatch) {
                    dyna[i][j] = getDynaValue(dyna, i - 1, j - 1) || j == 0;
                }
            }
            k = isCharacter(p.charAt(i)) ? k + 1 : k;
            kArray[i] = k;
        }

//        printDynaMatrix(dyna, kArray);

        return dyna[p.length() - 1][s.length() - 1];
    }

    boolean isCharacter(char c) {
        return c != '*';
    }

    boolean getDynaValue(boolean[][] dyna, int i, int j) {
        return i >= 0 && i < dyna.length && j >= 0 && j < dyna[0].length && dyna[i][j];
    }

    boolean isCharacterMatch(char s, char p) {
        return s == p || p == '*' || p == '?';
    }

    void matchAndPrint(String s, String p) {
        boolean r = isMatch(s, p);
        System.out.println(String.format("[%s] [%s] : %s", s, p, r));
    }


    void printDynaMatrix(boolean[][] dyna, int[] k) {
        for (int i = 0; i < dyna.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (boolean n : dyna[i]) {
                sb.append(n ? '1' : '0').append(" ");
            }
            sb.append(k[i]);
            System.out.println(sb);
        }
    }

    public static void main(String[] args) {
        WildcardMatching t = new WildcardMatching();
        t.matchAndPrint("aa", "a");
        t.matchAndPrint("aa", null);
        t.matchAndPrint("aa", "");
        t.matchAndPrint("", "a");
        t.matchAndPrint(null, "a");
        t.matchAndPrint("aa", "a?");
        t.matchAndPrint("aa", "??");
        t.matchAndPrint("aabb", "a*");
        t.matchAndPrint("aa", "?a");
        t.matchAndPrint("aa", "aa");
        t.matchAndPrint("aa", "*");
        t.matchAndPrint("aa", "*?");
        t.matchAndPrint("aa", "?*");
        t.matchAndPrint("adceb", "*a*");
        t.matchAndPrint("adceb", "*ac*");
        t.matchAndPrint("adceb", "*a?c*");
        t.matchAndPrint("adceb", "*a*b");
        t.matchAndPrint("adceb", "a*c*");
        t.matchAndPrint("adceb", "*a*d");
        t.matchAndPrint("adceb", "a*eb");
        t.matchAndPrint("mississippi", "m??*ss*?i*pi");
        t.matchAndPrint("", "*");
        t.matchAndPrint("abcabczzzde", "*abc???de*");

    }
}
