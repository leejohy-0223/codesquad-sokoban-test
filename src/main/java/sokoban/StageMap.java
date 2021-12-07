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
                    return new PlayerPosition(i, j);
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
        System.out.println(stageNumber);
        printOnlyStageMap();
    }

    public void printOnlyStageMap() {
        System.out.println();
        for (int[] r : stageMap) {
            for (int c : r) {
                System.out.print(reverseValue.get(c));
            }
            System.out.println();
        }
        System.out.println();
    }

    public void movePlayer(DirectionValue dValue) {
        int xTemp = position.getPosX() + dValue.getXValue();
        int yTemp = position.getPosY() + dValue.getYValue();
        decideMoving(dValue, xTemp, yTemp, stageMap[xTemp][yTemp]);
    }

    private void decideMoving(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        // 벽일 경우 못움직인다.
        if (newBlock == 0) {
            moveImpossible(dValue);
        }
        // 공백일 경우
        if (newBlock == 5) {
            moveToVoid(dValue, xTemp, yTemp);
        }
        // 구멍일 경우
        if (newBlock == 1) {
            moveToHole(dValue, xTemp, yTemp);
        }
        // 공일 경우
        if (newBlock == 2) {
            moveToBall(dValue, xTemp, yTemp);
        }
        // 채워진 상태인 경우
        if (newBlock == 7) {
            moveToFillStatus(dValue, xTemp, yTemp);
        }
    }

    private void moveToFillStatus(DirectionValue dValue, int xTemp, int yTemp) {
        // 채워진 상태 뒤에 확인
        int nx = xTemp + dValue.getXValue();
        int ny = yTemp + dValue.getYValue();

        // 싱태 뒤에 벽 또는 새로운 공, 채워진 상태 있다면 이동 불가
        if (stageMap[nx][ny] == 0 || stageMap[nx][ny] == 2 || stageMap[nx][ny] == 7) {
            moveImpossible(dValue);
            return;
        }
        // 빈 땅이라면 player 와 한칸씩 같이 움직이기
        if (stageMap[nx][ny] == 5) {
            stageMap[nx][ny] = 2; // 공으로 채우기
        }
        // 구멍이라면
        if (stageMap[nx][ny] == 1) {
            stageMap[nx][ny] = 7; // 공 + 구멍으로 채우기
        }
        stageMap[xTemp][yTemp] = 6; // 공 + 플레이어로 채우기
        position.moveToHere(xTemp, yTemp);
        printOnlyStageMap();
        System.out.println(dValue.getSign() + ": " + dValue.getDirectionName() + "으로 이동합니다.");
    }

    private void moveToBall(DirectionValue dValue, int xTemp, int yTemp) {
        // 공 뒤에 확인
        int nx = xTemp + dValue.getXValue();
        int ny = yTemp + dValue.getYValue();

        // 공 뒤에 벽 또는 새로운 공, 채워진 상태 있다면 이동 불가
        if (stageMap[nx][ny] == 0 || stageMap[nx][ny] == 2 || stageMap[nx][ny] == 7) {
            moveImpossible(dValue);
            return;
        }
        // 빈 땅이라면 player 와 한칸씩 같이 움직이기
        if (stageMap[nx][ny] == 5) {
            stageMap[nx][ny] = 2; // 공으로 채우기
        }
        // 구멍이라면
        if (stageMap[nx][ny] == 1) {
            stageMap[nx][ny] = 7; // 공 + 구멍으로 채우기
        }
        stageMap[xTemp][yTemp] = 3;
        stageMap[position.getPosX()][position.getPosY()] = 5;

        position.moveToHere(xTemp, yTemp);
        printOnlyStageMap();
        System.out.println(dValue.getSign() + ": " + dValue.getDirectionName() + "으로 이동합니다.");
    }

    private void moveToHole(DirectionValue dValue, int xTemp, int yTemp) {
        if (stageMap[position.getPosX()][position.getPosY()] == 3) { // 플레이어만 있었을 경우에는 공백
            stageMap[position.getPosX()][position.getPosY()] = 5;
        } else {
            stageMap[position.getPosX()][position.getPosY()] = 1; // 플레이어 + 구멍이었을 경우에는 구멍
        }
        stageMap[xTemp][yTemp] = 6; // 구멍으로 이동했으므로, 플레어어 + 구멍으로 저장
        position.moveToHere(xTemp, yTemp);
        printOnlyStageMap();
        System.out.println(dValue.getSign() + ": " + dValue.getDirectionName() + "으로 이동합니다.");
    }

    private void moveToVoid(DirectionValue dValue, int xTemp, int yTemp) {
        if (stageMap[position.getPosX()][position.getPosY()] == 3) { // 플레이어만 있었을 경우에는 공백
            stageMap[position.getPosX()][position.getPosY()] = 5;
        } else {
            stageMap[position.getPosX()][position.getPosY()] = 1; // 플레이어 + 구멍이었을 경우에는 구멍
        }
        stageMap[xTemp][yTemp] = 3;
        position.moveToHere(xTemp, yTemp);
        printOnlyStageMap();
        System.out.println(dValue.getSign() + ": " + dValue.getDirectionName() + "으로 이동합니다.");
    }

    private void moveImpossible(DirectionValue dValue) {
        printOnlyStageMap();
        System.out.println(dValue.getSign() + ": " + "(경고!) 해당 명령을 수행할 수 없습니다!");
    }

}
