package sokoban;

import static sokoban.Constant.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StageMap {

    private final Map<Integer, Character> reverseValue;
    private String stageNumber;
    private int[][] stageMap;
    private int holeAndBallCount;
    private PlayerPosition position;
    private int turnCount;

    private StageMap(String stageNumber, int[][] stageMap, int holeAndBallCount, PlayerPosition position) {
        reverseValue = ValueMapper.getReverseValue();
        this.stageNumber = stageNumber;
        this.stageMap = stageMap;
        this.holeAndBallCount = holeAndBallCount;
        this.position = position;
        this.turnCount = 0;
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
                .filter(num -> num == INT_HALL)
                .count();
        }
        return result;
    }

    private static PlayerPosition findPlayerPosition(int[][] tempStageMap) {
        for (int i = 0; i < tempStageMap.length; i++) {
            PlayerPosition playerPosition = findPlayerPositionInRow(tempStageMap, i);
            if (playerPosition != null) {
                return playerPosition;
            }
        }
        throw new IllegalArgumentException("Player가 없습니다.");
    }

    private static PlayerPosition findPlayerPositionInRow(int[][] tempStageMap, int row) {
        for (int j = 0; j < tempStageMap[row].length; j++) {
            if (tempStageMap[row][j] == INT_PLAYER) {
                return new PlayerPosition(row, j);
            }
        }
        return null;
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
            Arrays.fill(ints, INT_VOID);
        }
        return tempStageMap;
    }

    public void printStatus() {
        System.out.println("< " + stageNumber + " >");
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

    public String getStageNumber() {
        return stageNumber;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void movePlayer(DirectionValue dValue) {
        int xTemp = position.getPosX() + dValue.getXValue();
        int yTemp = position.getPosY() + dValue.getYValue();
        decideMoving(dValue, xTemp, yTemp, stageMap[xTemp][yTemp]);

    }

    private void decideMoving(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        // 벽일 경우 못 움직인다.
        if (newBlock == INT_WALL) {
            moveImpossible(dValue);
            return;
        }
        // 구멍 또는 공백일 경우
        if (newBlock == INT_HALL || newBlock == INT_VOID) {
            moveToHoleOrVoid(dValue, xTemp, yTemp, newBlock);
        }
        // 공이거나 채워진 상태일 경우
        if (newBlock == INT_BALL || newBlock == INT_BALL_WITH_HALL) {
            moveToBallOrFillStatus(dValue, xTemp, yTemp, newBlock);
        }
    }

    private void moveToHoleOrVoid(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        if (stageMap[position.getPosX()][position.getPosY()] == INT_PLAYER) { // 이전 위치에 플레이어만 있었을 경우에는 공백 넣기
            stageMap[position.getPosX()][position.getPosY()] = INT_VOID;
        }
        if (stageMap[position.getPosX()][position.getPosY()] == INT_PLAYER_WITH_HALL) { // 플레이어 + 구멍(6)이었을 경우에는 구멍 넣기
            stageMap[position.getPosX()][position.getPosY()] = INT_HALL;
        }
        decidePlayerPosition(dValue, xTemp, yTemp, newBlock);
        turnCount++;
    }

    private void moveToBallOrFillStatus(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        int nx = xTemp + dValue.getXValue();
        int ny = yTemp + dValue.getYValue();
        // 싱태 뒤에 벽 또는 새로운 공, 채워진 상태 있다면 이동 불가
        if (stageMap[nx][ny] == INT_WALL || stageMap[nx][ny] == INT_BALL || stageMap[nx][ny] == INT_BALL_WITH_HALL) {
            moveImpossible(dValue);
            return;
        }
        // 빈 땅이라면 player 와 한칸씩 같이 움직이기
        if (stageMap[nx][ny] == INT_VOID) {
            stageMap[nx][ny] = INT_BALL; // 공으로 채우기
        }
        // 구멍이라면
        if (stageMap[nx][ny] == INT_HALL) {
            stageMap[nx][ny] = INT_BALL_WITH_HALL; // 공 + 구멍으로 채우기
        }
        stageMap[position.getPosX()][position.getPosY()] = INT_VOID;
        decidePlayerPosition(dValue, xTemp, yTemp, newBlock);
        turnCount++;
    }

    private void decidePlayerPosition(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        if (newBlock == INT_HALL || newBlock == INT_BALL_WITH_HALL) {
            mappingCommonValue(dValue, xTemp, yTemp, INT_PLAYER_WITH_HALL);
        }
        if (newBlock == INT_BALL || newBlock == INT_VOID) { // 새로운 위치가 그냥 공 위치였다면, 플레이어로 채운다.
            mappingCommonValue(dValue, xTemp, yTemp, INT_PLAYER);
        }
    }

    private void mappingCommonValue(DirectionValue dValue, int xTemp, int yTemp, int nextStatus) {
        stageMap[xTemp][yTemp] = nextStatus;
        position.moveToHere(xTemp, yTemp);
        printOnlyStageMap();
        System.out.println(dValue.getSign() + ": " + dValue.getDirectionName() + "으로 이동합니다.");
    }

    private void moveImpossible(DirectionValue dValue) {
        printOnlyStageMap();
        System.out.println(dValue.getSign() + ": " + "(경고!) 해당 명령을 수행할 수 없습니다!");
    }

    public boolean isFinished() {
        return holeAndBallCount == countingFill();
    }

    private int countingFill() {
        int count = 0;
        for (int[] row : stageMap) {
            count += countInRow(row);
        }
        return count;
    }

    private int countInRow(int[] row) {
        int count = 0;
        for (int i = 0; i < stageMap[0].length; i++) {
            if (row[i] == INT_BALL_WITH_HALL) {
                count++;
            }
        }
        return count;
    }
}
