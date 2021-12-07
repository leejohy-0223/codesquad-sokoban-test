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

<details>
<summary> 실행 결과 </summary>

#### 빈 곳 이동
```
Stage 2

#######
###  O  ###
#    o    #
# Oo P oO #
###  o  ###
#   O  #  
########

SOKOBAN> aw (사용자가 왼쪽 -> 위쪽 입력)

#######
###  O  ###
#    o    #
# OoP  oO #
###  o  ###
#   O  #  
########

A: 왼쪽으로 이동합니다.

#######
###  O  ###
#   Po    #
# Oo   oO #
###  o  ###
#   O  #  
########

W: 위쪽으로 이동합니다.
```

<br>

#### 막힌 곳 이동

```
SOKOBAN> aaa (왼쪽 3번 이동 요청)

  #######  
###  O  ###
#    o    #
# OoP  oO #
###  o  ###
 #   O  #  
 ########  

A: 왼쪽으로 이동합니다.

  #######  
###  O  ###
#    o    #
# OoP  oO #
###  o  ###
 #   O  #  
 ########  

A: (경고!) 해당 명령을 수행할 수 없습니다!

  #######  
###  O  ###
#    o    #
# OoP  oO #
###  o  ###
 #   O  #  
 ########  

A: (경고!) 해당 명령을 수행할 수 없습니다!
```

<br>

#### 정상 이동 후 사용자 종료 요청

```
SOKOBAN> dwq

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
#    oP   #
# Oo   oO #
###  o  ###
 #   O  #  
 ########  

W: 위쪽으로 이동합니다.
프로그램 종료
```
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

## 📝 풀이

- Client(Application)로부터 원하는 Stage를 입력받아 게임을 시작하는 객체를 만든다. 
- 입력을 처리하는 객체를 만든다. q를 입력받으면 Application을 종료한다.
- 정상적인 입력에 맞게 Player의 위치를 변경한다.

<br>

## 🔧 구현 
- 주요 로직에 대한 메서드 설명을 작성한다.
- 기존과 동일한 메서드 설명은 생략한다.

<br>

#### 1. Application 
- Step 1 : 지정된 String 입력을 받아 전체적인 흐름을 구성한다.
- Step 2 : 몇 번째 Stage를 시작할 지 추가로 전달한다.

<br>

#### 2. StageMapReader 
- Step 1 : Application으로부터 전달받은 String을 parsing해서 StageMap 객체를 생성한다. 또한 StageMap 객체에 직접적으로 접근한다.
- Step 2 : Application으로부터 전달받은 stage Number에 해당하는 StageMap 객체를 가져와 GameController에 전달한다. 
  
<br>

#### 3. StageMap
- Step 1 : 지도 데이터의 정보를 저장하고, PlayerPosition 객체를 가진다.
- Step 2 : GameController로부터 이동 정보를 받아 PlayerPosition을 변경시킨다.

<br>

#### 4. PlayerPosition
- Step 1 : Player의 포지션 정보를 가지는 객체이다.
- Step 2 : 동일

<br>  

#### 5. ValueMapper
- Step 1 : 기호(char)와 저장값(int)을 관리하는 Map을 가지며, 필요한 객체에게 제공한다.
- Step 2 : 동일

<br>

#### 6. (추가) DirectionValue
- Step 2 : 방향에 따른 방향 이름, 기호, 이동량을 가지는 Enum 이다.

<br>

#### 7. (추가) GameController
- Step 2 : StageMapReader로 부터 StageMap 객체를 전달받는다. 사용자 입력을 받아 Enum으로 변경 후 StageMap에 전달하는 역할을 수행한다.

<br>

#### 8. (추가) InputView
- Step 2 : 사용자 입력 담당을 한다. 

<br> 

### 1. Application
> **main 메서드** 
```java
public static void main(String[] args){

    String input="..."; // 길이상 생략
    StageMapReader mapReader=StageMapReader.initialMapReader(new ArrayList<>());
    mapReader.mappingTwoDimensionalArray(input);
    mapReader.startThisStage("Stage 2");
}
```
1. ```StageMapReader```의 ```initialMapReader``` 정적 팩토리 메서드를 통해 객체를 생성하고 할당받는다.
2. ```mappingTwoDimensionalArray```메서드를 호출하여 지정된 input을 넘긴다. 내부적으로 String을 parsing하여 ```StageMap```객체를 생성한다.
3. ```startThisStage```메서드에 원하는 Stage name을 전달하며 호출한다. 

<br>

### 2. StageMapReader
> **startThisStage : 요청된 Stage를 대상으로 gameStart 호출**
```java
    public void startThisStage(String stageNum) {
        for (StageMap stageMap : stageMaps) {
            if (stageMap.isYourStage(stageNum)) {
                GameController.gameStart(stageMap);
                break;
            }
        }
    }
```
- ```StartMapReader```에서 가지고 있는 ```StageMap``` 중, 일치하는 객체를 찾아 ```GameController```에게 전달한다.

<br>

### 3. StageMap
> **movePlayer : Player의 움직임 여부를 결정** 
```java
    public void movePlayer(DirectionValue dValue) {
        int xTemp = position.getPosX() + dValue.getXValue();
        int yTemp = position.getPosY() + dValue.getYValue();
        if (stageMap[xTemp][yTemp] == 5) {
            movePossible(dValue, xTemp, yTemp);
            return;
        }
        moveImpossible(dValue);
    }
```
- ```DirectionValue```로 부터 이동량을 얻어 새로운 x, y 좌표를 계산한다.
- 문제의 요구 사항인 ```빈 곳(5)```에 대해서만 움직임을 실행한다. 따라서 ```movePossible```메서드를 통해 이동을 수행한다.
- 아닐 경우 ```moveImpossible```메서드를 수행한다.

<br>

> **movePossible : Player 이동**
```java
    private void movePossible(DirectionValue dValue, int xTemp, int yTemp) {
        stageMap[position.getPosX()][position.getPosY()] = 5;
        stageMap[xTemp][yTemp] = 3;
        position.moveToHere(xTemp, yTemp);
        printOnlyStageMap();
        System.out.println(dValue.getSign() + ": " + dValue.getDirectionName() + "으로 이동합니다.");
    }
```
- 기존 Player의 위치에는 빈 곳을 의미하는 ```5```를 작성한다. 
- 새로운 위치로 Player를 이동시키고, Player를 의미하는 ```3```을 작성한다.
- ```position.moveToHere``` 메서드를 통해 position을 변경한다. 이는 Poosition의 setter 역할을 대신한다.
- 갱신된 맵과 상태를 출력한다.

<br>

> **moveImpossible : Player 정지**
```java
    private void moveImpossible(DirectionValue dValue) {
        printOnlyStageMap();
        System.out.println(dValue.getSign() + ": " + "(경고!) 해당 명령을 수행할 수 없습니다!");
    }
```
- 이동할 수 없는 위치에 대해서는 현재 맵과 상태를 출력한다.

<br>

### 4. PlayerPosition
> **moveToHere : position 변경**
```java
    public void moveToHere(int nx, int ny) {
        posX = nx;
        posY = ny;
    }
```
- Setter 대용으로, 직관적으로 position을 변경하도록 naming 했다. 

<br>

### 5. ValueMapper : 변경사항 없음

<br>

### 6. DirectionValue(추가)
> **변수**
```java
public enum DirectionValue {
    LEFT("왼쪽", 'A', 0, -1),
    RIGHT("오른쪽", 'D', 0, +1),
    UP("위쪽", 'W', -1, 0),
    DOWN("아래쪽", 'S', 1, 0),
    QUIT("프로그램 종료", 'q', 0, 0),
    INVALID("", ' ', 0, 0);

    private final String directionName;
    private final char sign;
    private final int xValue;
    private final int yValue;
    
    ... // getter 생략
}
```
- ``directionName`` : game message 출력용
- ``sign`` : 사용자 입력으로부터 enum Value를 얻기 위한 Key 역할 
- ``xValue, yValue`` : 좌표 상 이동량 표현

<br>

### 7. GameController(추가)
> **gameStart : StageMap을 기반으로 사용자에게 입력을 받아 게임 시작**
```java
    public static void gameStart(StageMap stageMap) {
        stageMap.printStatus();
        List<Character> inputs = InputView.requestInputFromUser();
        while (true) {
            for (Character input : inputs) {
                moveByInput(stageMap, input);
            }
            if (inputs.contains('q')) {
                break;
            }
            inputs = InputView.requestInputFromUser();
        }
    }
``` 
- ``InputView.requestInputFromUser``를 통해 사용자 입력을 ``List<Character>`` 형태로 받는다.
- 입력은 1자 이상으로 입력된다. 각각의 ``Character``를 대상으로 ``moveByInput``메서드를 호출하여 움직임을 결정한다.
- 입력에 ``q``가 포함되어 있을 경우, 종료를 의미하므로 break를 통해 while을 빠져나온다. 
- 2자 이상으로 입력되는 경우, 입력의 끝에 q가 오는 경우도 처리할 수 있다. (예시 : adsaq)
- ``q``가 포함되지 않았을 경우, 사용자로부터 다시 입력을 받아 반복 수행한다.

<br>

> **moveByInput : 입력으로부터 일치하는 DirectionValue를 찾아 그에 맞는 메서드를 수행**
```java
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
```
- ``mappingToDirectionValue``를 통해 사용자 입력과 일치하는 DirectionValue Type을 가져온다. 
- 타입이 ``INVALID``일 경우, `명령 수행 불가`를 출력하는 메서드를 실행하고 return 한다.
- 타입이 ``QUIT``일 경우, 종료 내용을 출력하고 return 한다.
- 그 외에는 VALID한 Type이므로, ``stageMap.movePlayer``메서드를 통해 해당 방향을 전달하며 움직임을 요청한다.

<br>

> **mappingToDirectionValue : 입력으로부터 일치하는 DirectionValue를 찾아 해당 enum 객체를 반환**
```java
    private static DirectionValue mappingToDirectionValue(Character input) {
        for (DirectionValue value : DirectionValue.values()) {
            if (Character.toLowerCase(value.getSign()) == input) {
                return value;
            }
        }
        return DirectionValue.INVALID;
    }
```
- input과 일치하는 enum 객체를 찾아 반환한다. 없을 경우 ``INVALID``` 객체를 반환한다.

<br>

### 8. InputView(추가)
> **requestInputFromUser : 사용자 입력 담당**
```java
    public static final String PROMPT = "SOKOBAN> ";

    public static List<Character> requestInputFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print(PROMPT);

        String input = sc.nextLine();
        return input.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
    }
```
- 프롬프트를 전역 변수로 가지고 있다. 사용자의 요청의 앞에 항상 출력된다.
- ``Scanner``를 통해 사용자 입력을 받고, ``List<Character>``로 반환한다.


