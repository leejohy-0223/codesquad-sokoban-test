package sokoban;

import java.util.HashMap;
import java.util.List;

public class GameController {

    private final GameSlot gameSlot;

    private GameController(GameSlot gameSlot) {
        this.gameSlot = gameSlot;
    }

    public static GameController initialController() {
        GameSlot gameSlot = new GameSlot(new HashMap<>());
        return new GameController(gameSlot);
    }

    public void gameStart(StageMap stageMap, StageRepository stageRepository) {
        List<Character> inputs;
        while (true) {
            inputs = InputView.requestInputFromUser();
            if (isSaveOrLoadRequest(inputs)) {
                stageMap = saveOrLoadRequest(stageMap, inputs);
                continue;
            }
            for (Character input : inputs) {
                if (input == 'r') {
                    stageMap = resetStage(stageMap, stageRepository);
                    continue;
                }
                moveByInput(stageMap, input);
            }
            if (isFinished(stageMap, inputs)) {
                return;
            }
        }
    }

    private boolean isSaveOrLoadRequest(List<Character> inputs) {
        return (inputs.contains('S') || inputs.contains('L'));
    }

    private StageMap saveOrLoadRequest(StageMap stageMap, List<Character> inputs) {
        if (inputs.contains('S')) {
            return saveRequest(stageMap, inputs);
        }
        return loadRequest(stageMap, inputs);
    }

    private StageMap saveRequest(StageMap stageMap, List<Character> inputs) {
        try {
            saveGame(Character.getNumericValue(inputs.get(0)), stageMap);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return stageMap;
    }

    private void saveGame(int slotNumber, StageMap stageMap) {
        if (gameSlot.isNotEmpty(slotNumber)) {
            String yesOrNo = InputView.requestYesOrNo();
            if (yesOrNo.equals("y")) {
                gameSlot.saveStageMap(slotNumber, stageMap);
            }
            return;
        }
        gameSlot.saveStageMap(slotNumber, stageMap);
    }

    private StageMap loadRequest(StageMap stageMap, List<Character> inputs) {
        try {
            stageMap = loadGame(Character.getNumericValue(inputs.get(0)), stageMap);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return stageMap;
    }

    private StageMap loadGame(int slotNumber, StageMap stageMap) {
        if (gameSlot.isNotEmpty(slotNumber)) {
            return gameSlot.loadSavedGame(slotNumber);
        }
        System.out.println(slotNumber + "번 세이브에 저장된 진행상황이 없습니다. [1-5] 사이의 세이브에 저장하신 후 불러와주세요.");
        return stageMap;
    }

    private static boolean isFinished(StageMap stageMap, List<Character> inputs) {
        if (inputs.contains('q') || stageMap.isFinished()) {
            System.out.println("\n< 빠밤! " + stageMap.getStageNumber() + " 클리어! >");
            System.out.println("[ 턴수 : " + stageMap.getTurnCount() + " ]\n");
            return true;
        }
        return false;
    }

    private static StageMap resetStage(StageMap stageMap, StageRepository stageRepository) {
        stageMap = initialStageMap(stageMap.getStageNumber(), stageRepository);
        stageMap.printOnlyStageMap();
        return stageMap;
    }

    private static StageMap initialStageMap(String stageNumber, StageRepository stageRepository) {
        List<String> initialMapValue = stageRepository.getStageMaps().get(stageNumber);
        System.out.println(stageNumber + "가 리셋되었습니다.");
        return StageMap.makeStage(stageNumber, initialMapValue);
    }

    private static void moveByInput(StageMap stageMap, Character input) {
        DirectionValue dValue = mappingToDirectionValue(input);
        if (dValue == DirectionValue.INVALID) {
            printInvalidCommand(stageMap, input);
            return;
        }
        if (dValue == DirectionValue.QUIT) {
            System.out.println(dValue.getDirectionName());
            return;
        }
        stageMap.movePlayer(dValue);
    }

    private static DirectionValue mappingToDirectionValue(Character input) {
        for (DirectionValue value : DirectionValue.values()) {
            if (value.getSign() == input) {
                return value;
            }
        }
        return DirectionValue.INVALID;
    }

    private static void printInvalidCommand(StageMap stageMap, Character input) {
        stageMap.printOnlyStageMap();
        System.out.println(Character.toUpperCase(input) + ": (경고!) 해당 명령을 수행할 수 없습니다!");
    }
}
