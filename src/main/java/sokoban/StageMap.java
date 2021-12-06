package sokoban;

import java.util.Arrays;
import java.util.List;

public class StageMap {
    private String stageNumber;
    private int[][] stageMap;

    public StageMap(String stageNumber, int[][] stageMap) {
        this.stageNumber = stageNumber;
        this.stageMap = stageMap;
    }

    public static StageMap makeStage(String stageNumber, List<String> stageList) {
        int rowSize = stageList.size();
        int columnSize = stageList.stream()
            .mapToInt(String::length)
            .max()
            .orElse(0);

        int[][] tempStageMap = makeIntStage(stageList, rowSize, columnSize);
        return new StageMap(stageNumber, tempStageMap);
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

}
