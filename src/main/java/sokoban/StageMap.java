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
        return new StageMap(stageNumber, tempStageMap, findHoleAndBallCount(tempStageMap),
            findPlayerPosition(tempStageMap));
    }

    private static int findHoleAndBallCount(int[][] tempStageMap) {
        int result = 0;
        for (int[] row : tempStageMap) {
            result += Arrays.stream(row)
                .filter(num -> num == 1)
                .count();
        }
        return result;
    }

    private static PlayerPosition findPlayerPosition(int[][] tempStageMap) {
        for (int i = 0; i < tempStageMap.length; i++) {
            for (int j = 0; j < tempStageMap[i].length; j++) {
                if (tempStageMap[i][j] == 3) {
                    return new PlayerPosition(i, j); // i+1, j+1 -> i, j로 변경
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
        printOnlyStageMap();
    }

    public void printOnlyStageMap() {
        for (int[] r : stageMap) {
            for (int c : r) {
                System.out.print(reverseValue.get(c));
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isYourStage(String stageNum) {
        return this.stageNumber.equals(stageNum);
    }

    public void movePlayer(DirectionValue dValue) {
        int xTemp = position.getPosX() + dValue.getXValue();
        int yTemp = position.getPosY() + dValue.getYValue();

        if (stageMap[xTemp][yTemp] == 5) {
            stageMap[position.getPosX()][position.getPosY()] = 5;
            stageMap[xTemp][yTemp] = 3;
            position.setPosX(xTemp);
            position.setPosY(yTemp);
            printOnlyStageMap();
            System.out.println(dValue.getSign() + ": " + dValue.getDirectionName() +"으로 이동합니다.");
        } else {
            printOnlyStageMap();
            System.out.println(dValue.getSign() + ": " + "(경고!) 해당 명령을 수행할 수 없습니다!");
        }
    }

}
