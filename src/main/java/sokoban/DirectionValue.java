package sokoban;

public enum DirectionValue {
    LEFT("왼쪽", 'a', 0, -1),
    RIGHT("오른쪽", 'd', 0, +1),
    UP("위쪽", 'w', -1, 0),
    DOWN("아래쪽", 's', 1, 0),
    QUIT("프로그램 종료", 'q', 0, 0),
    INVALID("", ' ', 0, 0)
    ;

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
