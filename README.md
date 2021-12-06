## 2단계 : 플레이어 이동 구현하기

<details>
<summary> 문제 설명 및 요구사항 </summary>

### 문제 설명
- 1단계 Stage2의 지도를 읽고 사용자 입력을 받아서 캐릭터를 움직이게 하는 프로그램을 작성하자.

<br> 

#### 입력 명령
```
- w: 위쪽
- a: 왼쪽
- s: 아래쪽
- d: 오른쪽
- q: 프로그램 종료
```

<br>

#### 요구사항
- 처음 시작하면 Stage2의 지도를 출력한다.
- 간단한 프롬프트(예 : ``SOKOBAN``)를 표시해준다.
- 하나 이상의 문자를 입력받은 경우, 순서대로 처리해서 단계별 상태를 출력한다.
- 벽이나 공 등 다른 물체에 부딪히면 ``해당 명령을 수행할 수 없습니다`` 메시지를 출력하고 플레이어를 움직이지 않는다.

<br>

#### 동작 예시

```
Stage 2

  #######
###  O  ###
#    o    #
# Oo P oO #
###  o  ###
 #   O  # 
 ########

SOKOBAN> ddzw (엔터)

  #######
###  O  ###
#    o    #
# Oo  PoO #
###  o  ###
 #   O  # 
 ########
 
 D: 오른쪽으로 이동합니다.
 
  #######
###  O  ###
#    o    #
# Oo  PoO #
###  o  ###
 #   O  # 
 ########
 
 D: (경고!) 해당 명령을 수행할 수 없습니다!
 
  #######
###  O  ###
#    o    #
# Oo  PoO #
###  o  ###
 #   O  # 
 ########
 
 Z: (경고!) 해당 명령을 수행할 수 없습니다!
 
  #######
###  O  ###
#    o    #
# Oo  PoO #
###  o  ###
 #   O  # 
 ########
 
 W: 위로 이동합니다.
 
SOKOBAN> q
Bye~
```

### 2단계 코딩 요구사항

- 너무 크지 않은 함수 단위로 구현하고, 중복된 코드를 줄이도록 노력한다.
- 마찬가지로 Readme.md 파일과 작성한 소스 코드를 모두 기존 secret gist에 올려야 한다.
- 전역변수의 사용을 자제한다.
- 객체 또는 배열을 적절히 활용한다.

</details>

<br> 

#### 👉🏻 참고 Repository : [GitHub Step2 Repository](https://github.com/leejohy-0223/codesquad-sokoban-test/tree/step2)

## 🚀 실행 
- Git, Java는 설치되어 있다고 가정한다.
- step2 revision Id : 
```
$ git clone https://gist.github.com/fa8eb5f185967321dd0fb9a81fdc5baa.git step2
$ cd step2
$ git checkout step2_리비젼_id
$ javac *.java
$ java Application
```
- 위와 같이 gist repository를 통한 실행이 안 될 경우, [GitHub Step2 Repository](https://github.com/leejohy-0223/codesquad-sokoban-test/tree/step1) 에서 프로젝트 clone을 통해 다음과 같이 실행한다.
```
$ git clone https://github.com/leejohy-0223/codesquad-sokoban-test.git
$ cd codesquad-sokoban-test
$ git checkout step2
$ ./gradlew clean build
$ java -jar build/libs/codesquad-sokoban-test-1.0-SNAPSHOT.jar
```

<br>

## 🔧 구현 

- Application : 지정된 String 입력을 받아 전체적인 흐름을 구성한다.
- StageMapReader : Application으로 부터 전달받은 String을 parsing해서 StageMap 객체를 생성한다. 또한 StageMap 객체에 직접적으로 접근한다.
- StageMap : 지도 데이터의 정보를 저장하고, PlayerPosition 객체를 가진다.
- PlayerPosition : Player의 포지션 정보를 가지는 객체이다.
- ValueMapper : 기호(char)와 저장값(int)을 관리하는 Map을 가지며, 필요한 객체에게 제공한다.

<br> 

### 1. Application
> **main 메서드** 
```java
public static void main(String[] args){

    String input="..."; // 길이상 생략
    StageMapReader mapReader=StageMapReader.initialMapReader(new ArrayList<>());
    mapReader.mappingTwoDimensionalArray(input);
    mapReader.printStageInfo();
}
```
1. ```StageMapReader```의 ```initialMapReader``` 정적 팩토리 메서드를 통해 객체를 생성하고 할당받는다.
2. ```mappingTwoDimensionalArray```메서드를 호출하여 지정된 input을 넘긴다. 내부적으로 String을 parsing하여 ```StageMap```객체를 생성한다.
3. ```printStageInfo```메서드를 호출하여 초기화 된 ```StageMap```객체의 상태를 출력한다.

<br>

### 2. StageMapReader
> **변수 및 생성자, 정적 팩토리 메서드** 
```java
private final Map<Character, Integer> basicValue;
private List<StageMap> stageMaps;

private StageMapReader(Map<Character, Integer> basicValue, List<StageMap> stageMaps) {
    this.basicValue = basicValue;
    this.stageMaps = stageMaps;
}

public static StageMapReader initialMapReader(List<StageMap> stageMaps) {
    return new StageMapReader(ValueMapper.getBasicValue(), stageMaps);
}
```
- ```basicValue``` : 기호를 숫자로 변경하기 위한 Map 자료구조를 가진다. ```ValueMapper```의 ```getBasicValue```static 메서드를 통해 초기화된다.
- ```stageMaps``` : ```StageMap```객체를 관리하기 위한 ```stageMaps``` List 자료구조를 가지고 있다. 외부로부터 객체를 주입받게 된다.
- 생성자를 private으로 두고, ```ìnitialMapReader``` 메서드를 통해 객체를 생성하도록 하였다.

<br>

> **mappingTwoDimensionalArray 메서드 : String을 parsing하여 StageMap 객체를 생성** 
```java
    public void mappingTwoDimensionalArray(String input) {
        String[] split = input.split("\n");
        String stageNumber = "";
        List<String> tempStage = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("Stage")) {
                stageNumber = split[i];
                continue;
            }
            if (split[i].contains("=")) {
                stageMaps.add(StageMap.makeStage(stageNumber, tempStage));
                tempStage = new ArrayList<>();
                continue;
            }
            tempStage.add(changeToNumber(split[i]));
            if (i == split.length - 1) {
                stageMaps.add(StageMap.makeStage(stageNumber, tempStage));
                break;
            }
        }
    }
```
- "Stage"라는 문자를 포함하고 있을 경우 stageNumber에 이를 저장한다. 이후 ```StageMap```객체 생성 시 사용하기 위함이다.
- "="라는 문자를 포함하고 있을 경우 ```StageMap``` 정적 팩토리 메서드 ```makeStage```를 호출하여 객체를 생성하고, 반환된 객체를 ```stageMaps``` List에 저장한다. ```tempStage```는 다시 초기화한다.
- 둘 다 아닐 경우에는 map 정보이므로, ```changeToNumber``` 메서드를 통해 기호문자를 숫자문자로 변경 후, ```tempStage``` 리스트에 저장한다.
- i가 마지막 값을 가리킬 경우,  ```StageMap``` 정적 팩토리 메서드를 호출하여 마지막 객체를 생성하고 break한다.

<br>

> **changeToNumber 메서드 : 기호 문자를 숫자 문자로 변경 후 반환** 
```java
    private String changeToNumber(String s) {
        StringBuilder sb = new StringBuilder();
        for (char tempChar : s.toCharArray()) {
           sb.append(basicValue.get(tempChar));
        }
        return sb.toString();
    }
```

<br>

> **printStageInfo 메서드 : StageMap의 printStatus 메서드를 호출하여 상태를 출력** 
```java
    public void printStageInfo() {
        stageMaps.forEach(StageMap::printStatus);
    }
```

<br>

### 3. StageMap
> **변수 및 생성자** 
```java
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
```
- ```reverseValue``` : 숫자를 문자로 다시 역변환 하기 위한 ```Map``` 자료구조이다. ```ValueMapper```의 static 메서드를 통해 전달받는다.
- ```stageNumber``` : Stage Number를 통해 몇 번째 Stage인지 구분한다.
- ```stageMap``` : 숫자로 mapping 된 stage이다.
- ```holeAndBallCount``` : hole과 Ball의 개수는 동일하므로 함께 관리한다.
- ```position``` : player의 좌표를 가리키는 객체이다.

<br>

> **makeStage : 정적 팩터리 메서드**
```java
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
```
- ```StageMap```객체를 생성하는 정적 팩터리 메서드이다.
- 입력되는 input의 가로(Column) 사이즈는 모두 다르다. 따라서 가장 긴 길이를 columnSize로 고정하고, ```makeIntStage``` 메서드를 통해 2차원 배열의 초기화를 진행한다. 이를 통해 공백 영역은 숫자 5로 초기화 된다.
- private 생성자를 호출하며 객체를 반환한다.

<br>

> **initialStageMap : 2차원 배열 초기화 메서드**
```java
    private static int[][] initialStageMap(int rowSize, int columnSize) {
        int[][] tempStageMap = new int[rowSize][columnSize];
        for (int[] ints : tempStageMap) {
            Arrays.fill(ints, 5);
        }
        return tempStageMap;
    }
```
- 2차원 배열을 공백을 의미하는 숫자 5로 모두 초기화 한다.

<br>

> **printStatus : 상태 출력 메서드**
```java
    public void printStatus() {
        System.out.println(stageNumber + "\n");
        printStage();
        System.out.println("가로 크기 : " + stageMap[0].length);
        System.out.println("세로 크기 : " + stageMap.length);
        System.out.println("구멍의 수 : " + holeAndBallCount);
        System.out.println("공의 수 : " + holeAndBallCount);
        System.out.println("플레이어 위치 (" + position.getPosX() + ", " + position.getPosY() + ")\n");
    }
```

<br>

> **printStage : stage 출력 메서드 : 숫자를 다시 문자로 변경하여 출력**
```java
    private void printStage() {
        for (int[] r : stageMap) {
            for (int c : r) {
                System.out.print(reverseValue.get(c));
            }
            System.out.println();
        }
        System.out.println();
    }
```

<br>

### 4. PlayerPosition
> **변수 및 생성자, Getter만 포함**
```java
    private int posX;
    private int posY;

    public PlayerPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
```

<br>

### 5. ValueMapper
> **변수 및 static 생성자, Getter만 포함**
```java
private static final Map<Character, Integer> basicValue = new HashMap<>();
private static final Map<Integer, Character> reverseValue = new HashMap<>();

static {
        initBasicValue();
        initReverseValue();
}

private static void initBasicValue() {
        basicValue.put('#', 0) ... ; 
}

private static void initReverseValue() {
        reverseValue.put(0, '#') ... ;
}

public static Map<Character, Integer> getBasicValue() {
        return basicValue;
}

public static Map<Integer, Character> getReverseValue() {
        return reverseValue;
}
```
- static 생성자를 통해 ```basicValue```, ```reverseValue```를 초기화 한다.
- getter를 통해 생성되어있는 Map 객체를 반환한다.