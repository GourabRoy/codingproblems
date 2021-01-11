package leetcode.hard;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValidNumber {

    public boolean isNumber(String s) {
        System.out.print(s + " : ");
        s = s.trim();
//        if(s.length() == 0) return false;
//        Pattern validNumberPattern = Pattern.compile("[+-]?[0-9]*(\\.[0-9]*)?([0-9]+e[+-]?[0-9]+)?");
//        Pattern containsNumbers = Pattern.compile(".*[0-9]+.*");
//        return
//                        validNumberPattern.asMatchPredicate().test(s)
//                && containsNumbers.asMatchPredicate().test(s)
//        ;

        State state = State.START;
        Map<State, Map<Character, State>> stateTransitions = stateTransitions();
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            state = getNextState(stateTransitions, state, c);
            if(state == null) return false;
        }
        return state.terminal;
    }

    State getNextState(Map<State, Map<Character, State>> stateTransitions, State start, char c) {
        if(stateTransitions.containsKey(start))
            return stateTransitions.get(start).get(c);
        return null;
    }

    void addTransition(Map<State, Map<Character, State>> transitions, State start, char c, State end) {
        addTransition(transitions, start, Collections.singleton(c), end);
    }

    void addTransition(Map<State, Map<Character, State>> transitions, State start, Set<Character> cSet, State end) {
        transitions.putIfAbsent(start, new HashMap<>());
        cSet.stream().forEach(c -> transitions.get(start).put(c, end));
    }

    Set<Character> numbers() {
        Set<Character> numbers = new HashSet<>();
        for(char c='0'; c<='9'; c++) {
            numbers.add(c);
        }
        return numbers;
    }

    Set<Character> signs() {
        return Stream.of('+', '-').collect(Collectors.toSet());
    }

    Map<State, Map<Character, State>> stateTransitions() {
        Map<State, Map<Character, State>> stateTransitions = new HashMap<>();
        Set<Character> numbers = numbers();
        Set<Character> signs = signs();
        char DOT = '.';
        char E = 'e';
        addTransition(stateTransitions, State.START, numbers, State.NUMBER);
        addTransition(stateTransitions, State.START, signs, State.SIGN);
        addTransition(stateTransitions, State.START, DOT, State.DOT_AFTER_SIGN_OR_START);
        addTransition(stateTransitions, State.SIGN, numbers, State.NUMBER);
        addTransition(stateTransitions, State.SIGN, DOT, State.DOT_AFTER_SIGN_OR_START);
        addTransition(stateTransitions, State.DOT_AFTER_SIGN_OR_START, numbers, State.NUMBER_AFTER_DOT);
        addTransition(stateTransitions, State.NUMBER, numbers, State.NUMBER);

        addTransition(stateTransitions, State.NUMBER, DOT, State.DOT_AFTER_NUMBER);
        addTransition(stateTransitions, State.DOT_AFTER_NUMBER, numbers, State.NUMBER_AFTER_DOT);
        addTransition(stateTransitions, State.NUMBER_AFTER_DOT, numbers, State.NUMBER_AFTER_DOT);
        addTransition(stateTransitions, State.NUMBER, E, State.E);
        addTransition(stateTransitions, State.NUMBER_AFTER_DOT, E, State.E);

        addTransition(stateTransitions, State.E, numbers, State.NUMBER_AFTER_E);
        addTransition(stateTransitions, State.E, signs, State.SIGN_AFTER_E);
        addTransition(stateTransitions, State.SIGN_AFTER_E, numbers, State.NUMBER_AFTER_E);
        addTransition(stateTransitions, State.NUMBER_AFTER_E, numbers, State.NUMBER_AFTER_E);

        return stateTransitions;
    }

    enum State {
        START,
        SIGN,
        NUMBER(true),
        DOT_AFTER_NUMBER(true),
        NUMBER_AFTER_DOT(true),
        E,
        SIGN_AFTER_E,
        NUMBER_AFTER_E(true),
        DOT_AFTER_SIGN_OR_START;

        boolean terminal;
        private State() {
            terminal = false;
        }

        private State(boolean terminal) {
            this.terminal = terminal;
        }
    }

    public static void main(String[] args) {
        ValidNumber t = new ValidNumber();
        System.out.println(t.isNumber(" "));
        System.out.println(t.isNumber("."));
        System.out.println(t.isNumber("+"));
        System.out.println(t.isNumber("-"));
        System.out.println(t.isNumber("+-1"));
        System.out.println(t.isNumber("e"));
        System.out.println(t.isNumber(" 1."));
        System.out.println(t.isNumber(" .1."));
        System.out.println(t.isNumber(" 1.1."));
        System.out.println(t.isNumber(" 1e.e1"));
        System.out.println(t.isNumber(" 1"));
        System.out.println(t.isNumber(".1"));
        System.out.println(t.isNumber("+1"));
        System.out.println(t.isNumber("-2"));
        System.out.println(t.isNumber(" 1.e-1"));
        System.out.println(t.isNumber(".1e1"));
        System.out.println(t.isNumber("+1.e-2"));
        System.out.println(t.isNumber("-2.0e-1"));
        System.out.println(t.isNumber("e-1"));
        System.out.println(t.isNumber("e-+1"));
        System.out.println(t.isNumber("0e+1"));
    }
}
