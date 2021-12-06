package sokoban;

import java.util.HashMap;
import java.util.Map;

public class ValueMapper {
    private static final Map<Character, Integer> basicValue = new HashMap<>();
    private static final Map<Integer, Character> reverseValue = new HashMap<>();

    static {
        initBasicValue();
        initReverseValue();
    }

    private static void initBasicValue() {
        basicValue.put('#', 0);
        basicValue.put('O', 1);
        basicValue.put('o', 2);
        basicValue.put('P', 3);
        basicValue.put(' ', 5);
    }

    private static void initReverseValue() {
        reverseValue.put(0, '#');
        reverseValue.put(1, 'O');
        reverseValue.put(2, 'o');
        reverseValue.put(3, 'P');
        reverseValue.put(5, ' ');
    }

    public static Map<Character, Integer> getBasicValue() {
        return basicValue;
    }

    public static Map<Integer, Character> getReverseValue() {
        return reverseValue;
    }
}
