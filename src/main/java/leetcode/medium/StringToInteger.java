package leetcode.medium;

import java.util.Objects;

public class StringToInteger {

    public int myAtoi(String s) {
        String ps = preprocess(s);
        if(isValid(ps)) {
            boolean isNegative = ps.charAt(0) == '-';
            int i = isNegative ? 1 : 0;

            StringBuilder sb = new StringBuilder();
            for(; i<ps.length(); i++) {
                char c = ps.charAt(i);
                if(isNumber(c))
                    sb.append(c);
                else
                    break;
            }

            try {
                if(sb.length() > 0) {
                    int n = Integer.parseInt(sb.toString());
                    return sb.toString().equals(n + "")?
                            (isNegative ? n * -1 : n)
                            : Integer.MIN_VALUE;
                }
            } catch (NumberFormatException e) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
        }
        return 0;
    }

    String preprocess(String s) {
        String ps = s.trim();
        if(ps.charAt(0) == '+' && ps.length() > 1 && ps.charAt(1) != '-')
            return ps.substring(1);
        else {
            return ps;
        }
    }

    boolean isValid(String s) {
        return Objects.nonNull(s) && s.length() > 0 && (isNumber(s.charAt(0)) || s.charAt(0) == '-');
    }

    boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    public static void main(String[] args) {
        StringToInteger test = new StringToInteger();
        System.out.println(test.myAtoi("+1"));
        System.out.println(test.myAtoi("+-1"));
        System.out.println(test.myAtoi("+"));
        System.out.println(test.myAtoi("-"));
        System.out.println(test.myAtoi("+123"));
        System.out.println(test.myAtoi("   -123"));
        System.out.println(test.myAtoi("   -"));
        System.out.println(test.myAtoi("   12b jh"));
        System.out.println(test.myAtoi("   -12b jh"));
        System.out.println(test.myAtoi("-91283472332"));
    }

}
