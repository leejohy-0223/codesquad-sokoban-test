package sokoban;

import static sokoban.Constant.*;

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
        basicValue.put(CHAR_WALL, INT_WALL);
        basicValue.put(CHAR_HALL, INT_HALL);
        basicValue.put(CHAR_BALL, INT_BALL);
        basicValue.put(CHAR_PLAYER, INT_PLAYER);
        basicValue.put(CHAR_VOID, INT_VOID);
        basicValue.put(CHAR_PLAYER_WITH_HALL, INT_PLAYER_WITH_HALL);
        basicValue.put(CHAR_BALL_WITH_HALL, INT_BALL_WITH_HALL);
    }

    private static void initReverseValue() {
        reverseValue.put(INT_WALL, CHAR_WALL);
        reverseValue.put(INT_HALL, CHAR_HALL);
        reverseValue.put(INT_BALL, CHAR_BALL);
        reverseValue.put(INT_PLAYER, CHAR_PLAYER);
        reverseValue.put(INT_VOID, CHAR_VOID);
        reverseValue.put(INT_PLAYER_WITH_HALL, CHAR_PLAYER_WITH_HALL);
        reverseValue.put(INT_BALL_WITH_HALL, CHAR_BALL_WITH_HALL);
    }

    public static Map<Character, Integer> getBasicValue() {
        return basicValue;
    }

    public static Map<Integer, Character> getReverseValue() {
        return reverseValue;
    }
}
