package sokoban;

import java.util.Map;

public class GameSlot {
    private final Map<Integer, StageMap> gameSlot;

    public GameSlot(Map<Integer, StageMap> gameSlot) {
        this.gameSlot = gameSlot;
        for (int i = 1; i <= 5; i++) {
            gameSlot.put(i, null);
        }
    }
}
