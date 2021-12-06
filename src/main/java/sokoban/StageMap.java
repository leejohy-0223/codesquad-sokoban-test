package sokoban;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StageMap {

    private final Map<Integer, Character> reverseValue;
    private String stageNumber;
    private int[][] stageMap;
    private int holeAndBallCount;
    private PlayerPosition position;

    private StageMap(String stageNumber, int[][] stageMap, int holeAndBallCount, PlayerPosition position) {
        reverseValue = ValueMapper.getReverseValue();
        this.stageNumber = stageNumber;
        this.stageMap = stageMap;
        this.holeAndBallCount = holeAndBallCount;
        this.position = position;
    }

    public static StageMap makeStage(String stageNumber, List<String> stageList) {
        int rowSize = stageList.size();
        int columnSize = stageList.stream()
            .mapToInt(String::length)
            .max()
            .orElseThrow(IllegalArgumentException::new);

        int[][] tempStageMap = makeIntStage(stageList, rowSize, columnSize);
        return new StageMap(stageNumber, tempStageMap, findHoleAndBallCount(tempStageMap), findPlayerPosition(tempStageMap));
    }

    private static int findHoleAndBallCount(int[][] tempStageMap) {
        int result = 0;
        for (int[] row : tempStageMap) {
            result += Arrays.stream(row)
                .filter(i -> i == 1)
                .count();
        }
        return result;
    }

    private static PlayerPosition findPlayerPosition(int[][] tempStageMap) {
        for (int i = 0; i < tempStageMap.length; i++) {
            for (int j = 0; j < tempStageMap[i].length; j++) {
                if (tempStageMap[i][j] == 3) {
                    return new PlayerPosition(i + 1, j + 1);
                }
            }
        }
        throw new IllegalArgumentException("유저가 없습니다.");
    }

    private static int[][] makeIntStage(List<String> stageList, int rowSize, int columnSize) {
        int[][] tempStageMap = initialStageMap(rowSize, columnSize);
        for (int i = 0; i < rowSize; i++) {
            char[] chars = stageList.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                tempStageMap[i][j] = Character.getNumericValue(chars[j]);
            }
        }
        return tempStageMap;
    }

    private static int[][] initialStageMap(int rowSize, int columnSize) {
        int[][] tempStageMap = new int[rowSize][columnSize];
        for (int[] ints : tempStageMap) {
            Arrays.fill(ints, 5);
        }
        return tempStageMap;
    }

    public void printStatus() {
        System.out.println(stageNumber + "\n");
        printStage();
        System.out.println("가로 크기 : " + stageMap[0].length);
        System.out.println("세로 크기 : " + stageMap.length);
        System.out.println("구멍의 수 : " + holeAndBallCount);
        System.out.println("공의 수 : " + holeAndBallCount);
        System.out.println("플레이어 위치 (" + position.getPosX() + ", " + position.getPosY() + ")\n");
    }

    private void printStage() {
        for (int[] r : stageMap) {
            for (int c : r) {
                System.out.print(reverseValue.get(c));
            }
            System.out.println();
        }
        System.out.println();
    }
}
