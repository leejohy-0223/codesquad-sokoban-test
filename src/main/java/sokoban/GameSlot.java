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

    public boolean isNotEmpty(int slotNumber) {
        if (!gameSlot.containsKey(slotNumber)) {
            throw new IllegalArgumentException("1-5 사이의 숫자만 선택하세요");
        }
        return gameSlot.get(slotNumber) != null;
    }

    public void saveStageMap(int slotNumber, StageMap stageMap) {
        System.out.println(slotNumber + "번 세이브에 게임이 정상적으로 저장되었습니다.");
        stageMap.printOnlyStageMap();
        StageMap nStageMap = stageMap.allStatusCopy();
        gameSlot.put(slotNumber, nStageMap);
    }

    public StageMap loadSavedGame(int slotNumber) {
        System.out.println(slotNumber + "번 세이브에서 진행 상황을 불러옵니다.");
        StageMap calledStageMap = gameSlot.get(slotNumber).allStatusCopy();
        calledStageMap.printStatus();
        return calledStageMap;
    }
}
