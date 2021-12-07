package sokoban;

import java.util.List;

public class GameController {
    public static void gameStart(StageMap stageMap, StageRepository stageRepository) {
        stageMap.printStatus();
        List<Character> inputs = InputView.requestInputFromUser();
        while (true) {
            for (Character input : inputs) {
                if (input == 'r') {
                    stageMap = resetStage(stageMap, stageRepository);
                    continue;
                }
                moveByInput(stageMap, input);
            }
            if (inputs.contains('q') || stageMap.isFinished()) {
                System.out.println("\n<< 빠밤! " + stageMap.getStageNumber() + " 클리어! >>");
                System.out.println("[ 턴수 : " + stageMap.getTurnCount() + " ]\n");
                return;
            }
            inputs = InputView.requestInputFromUser();
        }
    }

    private static StageMap resetStage(StageMap stageMap, StageRepository stageRepository) {
        stageMap = initialStageMap(stageMap, stageRepository);
        stageMap.printOnlyStageMap();
        return stageMap;
    }

    private static StageMap initialStageMap(StageMap stageMap, StageRepository stageRepository) {
        String stageNumber = stageMap.getStageNumber();
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
            if (Character.toLowerCase(value.getSign()) == Character.toLowerCase(input)) {
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
