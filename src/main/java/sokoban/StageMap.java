package sokoban;

import static sokoban.Constant.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class StageMap {

    private final Map<Integer, Character> reverseValue;
    private Stack<StageMap> restoreStack;
    private Stack<StageMap> cancelStack;
    private final String stageNumber;
    private int[][] stageMap;
    private int holeAndBallCount;
    private PlayerPosition position;
    private int turnCount;

    private StageMap(String stageNumber, int[][] stageMap, int holeAndBallCount, PlayerPosition position, int turnCount,
        Stack<StageMap> restoreStack, Stack<StageMap> cancelStack) {
        reverseValue = ValueMapper.getReverseValue();
        this.stageNumber = stageNumber;
        this.stageMap = stageMap;
        this.holeAndBallCount = holeAndBallCount;
        this.position = position;
        this.turnCount = turnCount;
        this.restoreStack = restoreStack;
        this.cancelStack = cancelStack;
    }

    public static StageMap makeStage(String stageNumber, List<String> stageList) {
        int rowSize = stageList.size();
        int columnSize = stageList.stream()
            .mapToInt(String::length)
            .max()
            .orElseThrow(IllegalArgumentException::new);

        int[][] tempStageMap = makeIntStage(stageList, rowSize, columnSize);
        return new StageMap(stageNumber, tempStageMap, findHoleAndBallCount(tempStageMap),
            findPlayerPosition(tempStageMap), 0, new Stack<>(), new Stack<>());
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
        if (restoreOrCancel(dValue)) {
            return;
        }
        if (cancelStack.size() != 0) {
            cancelStack.clear();
        }
        int xTemp = position.getPosX() + dValue.getXValue();
        int yTemp = position.getPosY() + dValue.getYValue();
        decideMoving(dValue, xTemp, yTemp, stageMap[xTemp][yTemp]);
    }

    private boolean restoreOrCancel(DirectionValue dValue) {
        if (dValue.getSign() == 'u') {
            executeRestore();
        }
        if (dValue.getSign() == 'U') {
            executeCancel();
        }
        return dValue.getSign() == 'u' || dValue.getSign() == 'U';
    }

    private void executeRestore() {
        if (restoreStack.isEmpty()) {
            System.out.println("되돌릴 상태가 없습니다.");
            return;
        }
        cancelStack.push(statusCopyExcludeStack());
        stackPopAndChangeMap(restoreStack);
        System.out.println("한 턴 되돌렸습니다.");
        printOnlyStageMap();
        System.out.println("현 시점에서 가능한 되돌리기 수는 " + restoreStack.size() + "회 입니다.");
    }

    private void executeCancel() {
        if (cancelStack.isEmpty()) {
            System.out.println("되돌리기 취소할 상태가 없습니다.");
            return;
        }
        restoreStack.clear();
        stackPopAndChangeMap(cancelStack);
        System.out.println("되돌리기를 취소했습니다.");
        printOnlyStageMap();
        System.out.println("현 시점에서 가능한 취소 수는 " + cancelStack.size() + "회 입니다.");
    }

    private void stackPopAndChangeMap(Stack<StageMap> stack) {
        StageMap beforeStageMap = stack.pop();
        changeMapToThis(beforeStageMap);
    }

    private void changeMapToThis(StageMap beforeStageMap) {
        stageMap = beforeStageMap.stageMap;
        holeAndBallCount = beforeStageMap.holeAndBallCount;
        position = beforeStageMap.position;
        turnCount = beforeStageMap.turnCount;
    }

    private void decideMoving(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        if (newBlock == INT_WALL) {
            moveImpossible(dValue);
            return;
        }
        if (newBlock == INT_HALL || newBlock == INT_VOID) {
            moveToHoleOrVoid(dValue, xTemp, yTemp, newBlock);
        }
        if (newBlock == INT_BALL || newBlock == INT_BALL_WITH_HALL) {
            moveToBallOrFillStatus(dValue, xTemp, yTemp, newBlock);
        }
    }

    private void moveToHoleOrVoid(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        restoreStack.push(statusCopyExcludeStack());
        decideBeforePlayerPositionStatus();
        decidePlayerPosition(dValue, xTemp, yTemp, newBlock);
        turnCount++;
    }

    private void decideBeforePlayerPositionStatus() {
        if (stageMap[position.getPosX()][position.getPosY()] == INT_PLAYER) {
            stageMap[position.getPosX()][position.getPosY()] = INT_VOID;
        }
        if (stageMap[position.getPosX()][position.getPosY()] == INT_PLAYER_WITH_HALL) {
            stageMap[position.getPosX()][position.getPosY()] = INT_HALL;
        }
    }

    private void moveToBallOrFillStatus(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        int nx = xTemp + dValue.getXValue();
        int ny = yTemp + dValue.getYValue();
        if (stageMap[nx][ny] == INT_WALL || stageMap[nx][ny] == INT_BALL || stageMap[nx][ny] == INT_BALL_WITH_HALL) {
            moveImpossible(dValue);
            return;
        }
        restoreStack.push(statusCopyExcludeStack());
        if (stageMap[nx][ny] == INT_VOID) {
            stageMap[nx][ny] = INT_BALL;
        }
        if (stageMap[nx][ny] == INT_HALL) {
            stageMap[nx][ny] = INT_BALL_WITH_HALL;
        }
        decideBeforePlayerPositionStatus();
        decidePlayerPosition(dValue, xTemp, yTemp, newBlock);
        turnCount++;
    }

    private void decidePlayerPosition(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        if (newBlock == INT_HALL || newBlock == INT_BALL_WITH_HALL) {
            mappingPlayerPosition(dValue, xTemp, yTemp, INT_PLAYER_WITH_HALL);
        }
        if (newBlock == INT_BALL || newBlock == INT_VOID) {
            mappingPlayerPosition(dValue, xTemp, yTemp, INT_PLAYER);
        }
    }

    private void mappingPlayerPosition(DirectionValue dValue, int xTemp, int yTemp, int nextStatus) {
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

    public StageMap allStatusCopy() {
        PlayerPosition positionCopy = new PlayerPosition(position.getPosX(), position.getPosY());
        int[][] arrCopy = stageMapDeepCopy();
        return new StageMap(stageNumber, arrCopy, holeAndBallCount, positionCopy, turnCount, new Stack<>(),
            new Stack<>());
    }

    public StageMap statusCopyExcludeStack() {
        PlayerPosition positionCopy = new PlayerPosition(position.getPosX(), position.getPosY());
        int[][] arrCopy = stageMapDeepCopy();
        return new StageMap(stageNumber, arrCopy, holeAndBallCount, positionCopy, turnCount, restoreStack, cancelStack);
    }

    private int[][] stageMapDeepCopy() {
        int[][] arrCopy = new int[stageMap.length][stageMap[0].length];
        for (int i = 0; i < stageMap.length; i++) {
            System.arraycopy(stageMap[i], 0, arrCopy[i], 0, stageMap[i].length);
        }
        return arrCopy;
    }
}
