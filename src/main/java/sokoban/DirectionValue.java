package sokoban;

public enum DirectionValue {
    LEFT("왼쪽", 'A', 0, -1),
    RIGHT("오른쪽", 'D', 0, +1),
    UP("위쪽", 'W', -1, 0),
    DOWN("아래쪽", 'S', 1, 0),
    QUIT("스테이지를 스킵합니다", 'q', 0, 0),
    INVALID("", ' ', 0, 0);

    private final String directionName;
    private final char sign;
    private final int xValue;
    private final int yValue;

    DirectionValue(String directionName, char sign, int xValue, int yValue) {
        this.directionName = directionName;
        this.sign = sign;
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public String getDirectionName() {
        return directionName;
    }

    public int getXValue() {
        return xValue;
    }

    public char getSign() {
        return sign;
    }

    public int getYValue() {
        return yValue;
    }
}
