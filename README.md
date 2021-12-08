## 3단계 : 소코반 게임 완성하기 

<details>
<summary> 문제 설명 및 요구사항 </summary>

### 문제 설명
- 정상적인 소코반 게임을 완성한다.
- 참고 링크 : [소코반 게임](https://www.cbc.ca/kids/games/play/sokoban)

<br>

#### 요구사항
- 난이도를 고려하여 스테이지 1부터 5까지 플레이 가능한 map.txt 파일을 스스로 작성한다.
- 지도 파일 map.txt를 문자열로 읽어서 처리하도록 개선한다.
- 처음 시작 시 Stage 1의 지도와 프롬프트가 표시된다.
- r 명령 입력 시 스테이지를 초기화 한다.
- 모든 o를 O자리에 이동시키면 클리어 화면을 표시하고 다음 스테이지로 이동한다.
- 주어진 모든 스테이지를 클리어 시 축하 메시지를 출력하고 게임을 종료한다.

<br>

#### 참고 : 플레이어 이동 조건
- 플레이어는 o를 밀어서만 이동시킬 수 있다.
- o를 O 지점에 밀어 넣으면 0으로 변경된다.
- 플레이어는 O를 통과할 수 있다.
- 플레이어는 #을 통과할 수 없다.
- 0 상태의 o를 밀어내면 다시 o와 O로 분리된다.
- 플레이어가 움직일 때마다 턴수를 카운트 한다.
- 상자가 두 개 연속으로 붙어있는 경우 밀 수 없다. 상자 뒤에 공백이 있을 경우에만 밀 수 있다.
- 기타 필요한 로직은 실제 게임을 참고해서 완성한다.

<br>

#### 실행 예시
```
소코반의 세계에 오신 것을 환영합니다!
^오^

Stage 1

#####
#OoP#
#####

SOKOBAN> A

#####
#0P #
#####

빠밤! Stage 1 클리어!
턴수: 1

Stage 2
...

Stage 5
...

빠밤! Stage 5 클리어!
턴수: 5

전체 게임을 클리어하셨습니다!
축하드립니다! 
```

<br>

### 3단계 코딩 요구사항

- 가능한 한 커밋을 자주 하고 구현의 의미가 명확하게 전달되도록 커밋 메시지를 작성한다.
- 함수나 메서드는 한 번에 한 가지 일을 하고 가능하면 20줄이 넘지 않도록 구현한다.
- 함수나 메서드의 들여쓰기를 가능하면 적게(3단계까지만) 할 수 있도록 노력한다.

```
  function main() {
        for() { // 들여쓰기 1단계
            if() { // 들여쓰기 2단계
                return; // 들여쓰기 3단계
            }
        }
    }
```

</details>

<details>
<summary> 구현 코드 실행 결과 </summary>

#### 1. 게임 시작

```
소코반의 세계에 오신 것을 환영합니다!

< Stage 1 >

#####
#OoP#
#####

SOKOBAN> 
```
<br>

#### 2. 입력 (미션 클리어)

```
SOKOBAN> a // 왼쪽 이동

#####
#0P #
#####

A: 왼쪽으로 이동합니다.

< 빠밤! Stage 1 클리어! >
[ 턴수 : 1 ]

< Stage 2 >

######
#  P #
#o####
# #   
#O#   
###   

SOKOBAN> 
```
- 이동 시 구멍(O)과 공(o)가 겹쳐지면 0으로 변경된다.
- 모든 구멍과 공이 겹치지면 미션 클리어. 턴 수를 출력하고 다음 라운드로 넘어간다.

<br>

#### 3. 입력 (불가능한 이동)
```
SOKOBAN> d // 오른쪽 이동

######
#   P#
#o####
# #   
#O#   
###   

D: (경고!) 해당 명령을 수행할 수 없습니다!
SOKOBAN> z // 없는 명령

######
#   P#
#o####
# #   
#O#   
###   

Z: (경고!) 해당 명령을 수행할 수 없습니다!
```
- 현 위치에서 불가능한 이동 또는 지정되지 않은 명령어가 입력될 경우, 경고를 출력한 후 다시 입력받는다.

<br>

#### 4. 입력 (스테이지 스킵)
```
< Stage 4 >

###### 
#P   ##
# oo  #
# #O O#
#     #
#######

SOKOBAN> q // 스킵 명령 : q
스테이지를 스킵합니다

< 빠밤! Stage 4 클리어! >
[ 턴수 : 0 ]
```
- 테스트 용도로 q를 입력하면, 해당 스테이지를 스킵할 수 있다.

<br>

#### 5. 입력 (리셋)

```
  #### 
###  # 
#P Oo##
#   o #
# #O  #
#     #
#######

SOKOBAN> d

  #### 
###  # 
# POo##
#   o #
# #O  #
#     #
#######

D: 오른쪽으로 이동합니다.
SOKOBAN> s

  #### 
###  # 
#  Oo##
# P o #
# #O  #
#     #
#######

S: 아래쪽으로 이동합니다.
SOKOBAN> r
Stage 5가 리셋되었습니다.

  #### 
###  # 
#P Oo##
#   o #
# #O  #
#     #
#######
```
- 이동 중 r을 입력하면 해당 stage를 초기화 한다.
- 이동 횟수도 0으로 초기화된다.

<br>

#### 6. 게임 종료
```
< 빠밤! Stage 5 클리어! >
[ 턴수 : 0 ]

전체 게임을 클리어하셨습니다!
축하드립니다!
```
- 모든 Stage 종료 시, 축하 메시지를 출력하고 종료된다.

</details>

<br> 

#### 👉🏻 참고 Repository : [GitHub Step3 Repository](https://github.com/leejohy-0223/codesquad-sokoban-test/tree/step3)

## 🚀 실행 
- Git, Java는 설치되어 있다고 가정한다.
- step3 revision Id : 
```
$ git clone https://gist.github.com/fa8eb5f185967321dd0fb9a81fdc5baa.git step3
$ cd step3
$ git checkout step3_리비젼_id
$ javac *.java
$ java Application stageMap.txt
```
- 위와 같이 gist repository를 통한 실행이 안 될 경우, [GitHub Step3 Repository](https://github.com/leejohy-0223/codesquad-sokoban-test/tree/step3) 에서 프로젝트 clone을 통해 다음과 같이 실행한다.
```
$ git clone https://github.com/leejohy-0223/codesquad-sokoban-test.git
$ cd codesquad-sokoban-test
$ git checkout step3
$ ./gradlew clean build
$ java -jar build/libs/codesquad-sokoban-test-1.0-SNAPSHOT.jar src/main/resources/stageMap.txt
```

<br>

## 📝 풀이

- map.txt를 읽을 수 있는 InputView 메서드를 생성한다.
- 초기화(reset) 처리가 필요하다. txt로 부터 map 정보를 List로 묶어서 저장하는 방식을 고려한다.
- o(ball) + O(hall) = 0이 되므로, 0의 개수와 처음 초기화 된 ball의 개수가 동일해질 경우 다음 스테이지로 넘어간다.
- 플레이어의 턴 수를 카운트 한다. 매 스테이지마다 몇 턴 걸렸는지 출력한다.
- ball + hall => 0 으로 표현된다.
- player + hall => * (asterisk) 로 표현된다.

<br>

## 🔧 구현 
- 주요 로직에 대한 메서드 설명을 작성한다.
- 기존과 동일한 메서드 설명은 생략한다.

<br>

#### 1. Application 
- Step 1 : 지정된 String 입력을 받아 전체적인 흐름을 구성한다.
- Step 2 : 몇 번째 Stage를 시작할 지 추가로 전달한다.
- Step 3 : main의 args로 넘어오는 인자를 InputView로 전달하여 처리한다.

<br>

#### 2. StageMapReader 
- Step 1 : Application으로부터 전달받은 String을 parsing해서 StageMap 객체를 생성한다. 또한 StageMap 객체에 직접적으로 접근한다.
- Step 2 : Application으로부터 전달받은 stage Number에 해당하는 StageMap 객체를 가져와 GameController에 전달한다. 
- Step 3 : 기존의 입력 String에 대한 Parsing 역할을 새로운 객체인 StageRepository로 할당했다. 필요한 초기화는 StageRepository를 통해 진행한다.

<br>

#### 3. StageMap
- Step 1 : 지도 데이터의 정보를 저장하고, PlayerPosition 객체를 가진다.
- Step 2 : GameController로부터 이동 정보를 받아 PlayerPosition을 변경시킨다.
- Step 3 : 입력에 대한 이동 로직이 주로 추가되었다.

<br>

#### 4. PlayerPosition
- Step 1 : Player의 포지션 정보를 가지는 객체이다.
- Step 2 : 동일
- Step 3 : 동일

<br>  

#### 5. ValueMapper
- Step 1 : 기호(char)와 저장값(int)을 관리하는 Map을 가지며, 필요한 객체에게 제공한다.
- Step 2 : 동일
- Step 3 : 로직은 동일하나, Constant(상수)를 사용하도록 변경하였다.

<br>

#### 6. (Step2부터 추가) DirectionValue
- Step 2 : 방향에 따른 방향 이름, 기호, 이동량을 가지는 Enum 이다.
- Step 3 : 동일

<br>

#### 7. (Step2부터 추가) GameController
- Step 2 : StageMapReader로 부터 StageMap 객체를 전달받는다. 사용자 입력을 받아 Enum으로 변경 후 StageMap에 전달하는 역할을 수행한다.
- Step 3 : 리셋 및 game Finish 로직을 추가했다.

<br>

#### 8. (Step2부터 추가) InputView
- Step 2 : 사용자 입력 담당을 한다. 
- Step 3 : FileReader와 BufferedReader를 통해 Application.main 메서드로 전달되는 파라미터와 일치하는 파일을 찾아 내용을 String으로 변경하는 역할이 추가 되었다. 

<br> 

#### 9. (Step3부터 추가) Constant
- Step 3 : 상수를 관리하며, 기본 자료형(primitive types)에 의미를 부여했다.

<br>

#### 10. (Step3부터 추가) StageRepository
- Step 3 : 처음에 입력된 지도를 저장하여, reset에 활용하기 위한 객체이다. 싱글톤으로 관리된다.

<br>

### 1. Application
> **main 메서드** 
```java
    public static void main(String[] args) {
        String mapInput = InputView.makeMapInformation(args[0]);
        StageMapReader mapReader = StageMapReader.initialMapReader(new ArrayList<>(), mapInput);
        mapReader.startStage();
    }
```
- ``InputView``로 부터 String으로 변환된 입력을 ``StageMapReader``로 전달하여 객체를 생성한다.
- ``startStage``메서드를 호출하여 Stage를 시작한다.

<br>

### 2. StageMapReader
> **변수 및 생성자**
```java
    private final StageRepository stageRepository;
    private List<StageMap> stageMaps;

    private StageMapReader(List<StageMap> stageMaps, StageRepository stageRepository) {
        this.stageMaps = stageMaps;
        this.stageRepository = stageRepository;
    }

    public static StageMapReader initialMapReader(List<StageMap> stageMaps, String mapInput) {
        StageRepository repository = StageRepository.makeRepository();
        repository.makeInnerMapValue(mapInput);
        return new StageMapReader(stageMaps, repository);
    }
```
- String을 parsing 하는 로직을``StageRepository``으로 옮기고 변수로 가지도록 변경하였다.
- ``initialMapReader``메서드 초기화 과정에서 ``StageRepository``를 생성 및 초기화한다.

<br>

> **주요 메서드**
```java
    public void startStage() {
        initialStage();
        System.out.println("소코반의 세계에 오신 것을 환영합니다!\n");
        for (StageMap stageMap : stageMaps) {
            GameController.gameStart(stageMap, stageRepository);
        }
        System.out.println("전체 게임을 클리어하셨습니다!\n" + "축하드립니다!");
    }

    private void initialStage() {
        Map<String, List<String>> initialMap = stageRepository.getStageMaps();
        for (String key : initialMap.keySet()) {
            stageMaps.add(StageMap.makeStage(key, initialMap.get(key)));
        }
    }
```
- ```startStage``` : 자신의 ``stageMaps``를 초기화 하고, ``StageMap``을 하나씩 꺼내 GameController에게 전달하며 게임을 진행한다.
- ``initialStage`` : ``StageRepository``로 부터 초기화 된 지도의 key, value를 전달받아 ``StageMap``객체를 생성하고 ``stageMaps``List에 추가하여 초기화한다.

<br>

### 3. StageMap
> **movePlayer : Player의 움직임 여부를 결정** 
```java
    public void movePlayer(DirectionValue dValue) {
        int xTemp = position.getPosX() + dValue.getXValue();
        int yTemp = position.getPosY() + dValue.getYValue();
        decideMoving(dValue, xTemp, yTemp, stageMap[xTemp][yTemp]);
    }
```
- dValue를 활용하여 이동 예정 위치를 만들고, ```decideMoving```메서드로 전달하여 움직임을 결정하도록 한다.

<br>

> **decideMoving : 다음 블록 상태에 따라 움직임을 결정**
```java
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
```
- ``newBlock(다음 블록의 상태)``에 따라서 구분하여 메서드를 호출하여 방향을 결정한다.

<br>

> **moveImpossible : 위치 이동 불가**
```java
    private void moveImpossible(DirectionValue dValue) {
        printOnlyStageMap();
        System.out.println(dValue.getSign() + ": " + "(경고!) 해당 명령을 수행할 수 없습니다!");
    }
```
- 위치 이동 불가 시, 현재 지도 상태 및 경고를 출력한다.

<br>

> **moveToHoleOrVoid : 새로운 블록이 구멍 또는 빈 영역인 경우**
```java
    private void moveToHoleOrVoid(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        decideBeforePlayerPositionStatus();
        decidePlayerPosition(dValue, xTemp, yTemp, newBlock);
        turnCount++;
    }
```
- 움직이기 전 player의 상태에 따라 해당 위치를 어떤 걸로 채울지를 결정하는 ```decideBeforePlayerPosition```을 호출한다.
- 새 위치를 어떤 value로 채워야할 지 결정하는 ``decidePlayerPosition``메서드를 호출한다.

<br>

> **moveToBallOrFillStatus : 새로운 블록이 볼 혹은 채워진 상태(볼 + 구멍)인 경우**
```java
    private void moveToBallOrFillStatus(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        int nx = xTemp + dValue.getXValue();
        int ny = yTemp + dValue.getYValue();
        // 싱태 뒤에 벽 또는 새로운 공, 채워진 상태 있다면 이동 불가
        if (stageMap[nx][ny] == INT_WALL || stageMap[nx][ny] == INT_BALL || stageMap[nx][ny] == INT_BALL_WITH_HALL) {
            moveImpossible(dValue);
            return;
        }
        // 빈 땅이라면
        if (stageMap[nx][ny] == INT_VOID) {
            stageMap[nx][ny] = INT_BALL; // 공으로 채우기
        }
        // 구멍이라면
        if (stageMap[nx][ny] == INT_HALL) {
            stageMap[nx][ny] = INT_BALL_WITH_HALL; // 공 + 구멍으로 채우기
        }
        decideBeforePlayerPositionStatus();
        decidePlayerPosition(dValue, xTemp, yTemp, newBlock);
        turnCount++;
    }
```
- 볼을 움직일 경우에는, 이동 방향으로 한 칸 더 있는 상태에 대한 확인이 필요하다. 따라서 nx, ny에 해당 위치를 저장한다.
- 다음 순서로 진행된다.
    1. 상태 뒤가 벽, 새로운 공, 채워진 상태(공 + 구멍)인 경우 이동 불가이므로,```moveImpossible```를 호출하고 return 한다.
    2. 해당 위치가 빈 위치라면 공으로 채운다.
    3. 해당 위치가 구멍이라면 공 + 구멍 상태로 채운다.
    4. 움직이기 전 player의 상태에 따라 해당 위치를 어떤걸로 채울지를 결정하는 ```decideBeforePlayerPosition```을 호출한다.
    5. 이동 후 플레이어의 위치를 결정하는 ```decidePlayerPosition```을 호출한다.
  
<br>

> **decideBeforePlayerPositionStatus : 기존 플레이어의 위치에 어떤걸 채울지 결정**

```java
    private void decideBeforePlayerPositionStatus() {
        if (stageMap[position.getPosX()][position.getPosY()] == INT_PLAYER) {
            stageMap[position.getPosX()][position.getPosY()] = INT_VOID;
        }
        if (stageMap[position.getPosX()][position.getPosY()] == INT_PLAYER_WITH_HALL) {
            stageMap[position.getPosX()][position.getPosY()] = INT_HALL;
        }
    }
```
- 기존 위치에 플레이어만 있었다면 공백으로 변경한다.
- 기존 위치에 플레이어와 구멍이 함께 있었다면, 구멍으로 변경한다. 

<br>


> **decidePlayerPosition : 새로 결정된 플레이어의 위치에 어떤걸 채울지 결정**
```java
    private void decidePlayerPosition(DirectionValue dValue, int xTemp, int yTemp, int newBlock) {
        if (newBlock == INT_HALL || newBlock == INT_BALL_WITH_HALL) { // 새로운 위치가 공 + 구멍이었다면, 플레이어 + 공으로 채운다.
            mappingPlayerPosition(dValue, xTemp, yTemp, INT_PLAYER_WITH_HALL);
        }
        if (newBlock == INT_BALL || newBlock == INT_VOID) { // 새로운 위치가 그냥 공 위치였다면, 플레이어로 채운다.
            mappingPlayerPosition(dValue, xTemp, yTemp, INT_PLAYER);
        }
    }
```
- 새로 이동할 위치가 구멍이거나 볼 + 구멍이었다면, 플레이어 + 구멍으로 채운다.
- 새로 이동할 위치가 볼이거나 공백이었다면, 플레이어로만 채운다.

<br>

> **mappingPlayerPosition : 새로 결정된 플레이어의 위치에 어떤걸 채울지 결정**
```java
    private void mappingPlayerPosition(DirectionValue dValue, int xTemp, int yTemp, int nextStatus) {
        stageMap[xTemp][yTemp] = nextStatus;
        position.moveToHere(xTemp, yTemp);
        printOnlyStageMap();
        System.out.println(dValue.getSign() + ": " + dValue.getDirectionName() + "으로 이동합니다.");
    }
```
- stageMap에 최종 플레이어의 위치 상태를 반영하고, 실제 position을 옮긴다.
- 성공적으로 이동하였으므로 상태를 출력한다.

<br>

> **isFinished : stage 종료 여부를 확인**
```java
    public boolean isFinished() {
        return holeAndBallCount == countingFill();
    }
```
- ``countingFill``메서드를 통해 채워진(볼 + 구멍) 개수를 계산하고, 이를 전체 볼의 수와 비교한다.
- true일 경우, 모든 볼-구멍 쌍이 일치하므로 stage를 종료한다.

<br>

### 4. PlayerPosition : 변경사항 없음

<br>

### 5. ValueMapper : Constant 상수를 사용하도록 변경

<br>

### 6. DirectionValue : 변경 사항 없음

<br>

### 7. GameController
> **gameStart : StageMap을 기반으로 사용자에게 입력을 받아 게임 시작**
```java
    public static void gameStart(StageMap stageMap, StageRepository stageRepository) {
        stageMap.printStatus();
        List<Character> inputs = InputView.requestInputFromUser();
        while (true) {
            for (Character input : inputs) {
                if (input == 'r') {
                    stageMap = resetStage(stageMap, stageRepository);
                    continue;
                }
                moveByInput(stageMap, input);
            }
            if (isFinished(stageMap, inputs)) {
                return;
            }
            inputs = InputView.requestInputFromUser();
        }
    }
```
- ```resetStage```메서드를 호출하여 StageMap을 리셋시키는 작업이 추가되었다.
- ```isFinished```메서드를 호출하여 종료 여부를 파악하는 작업이 추가되었다.

<br>

> **resetStage, initialStageMap : 초기화 된 StageMap을 반환**
```java
    private static StageMap resetStage(StageMap stageMap, StageRepository stageRepository) {
        stageMap = initialStageMap(stageMap, stageRepository);
        stageMap.printOnlyStageMap();
        return stageMap;
    }

    private static StageMap initialStageMap(StageMap stageMap, StageRepository stageRepository) {
        String stageNumber = stageMap.getStageNumber();
        List<String> initialMapValue = stageRepository.getStageMaps().get(stageNumber);
        System.out.println(stageNumber + "가 리셋되었습니다.");
        return StageMap.makeStage(stageNumber, initialMapValue);
    }
```
- ```initialStageMap``` : StageRepository로 부터 초기화된 지도를 이용하여 새로운 ```StageMap```을 생성하여 반환한다.
- ``resetStage`` : 새로운 ```StageMap```의 상태를 출력하고 반환한다.

<br>

> **isFinished : 종료 여부 확인**
```java
    private static boolean isFinished(StageMap stageMap, List<Character> inputs) {
        if (inputs.contains('q') || stageMap.isFinished()) {
            System.out.println("\n< 빠밤! " + stageMap.getStageNumber() + " 클리어! >");
            System.out.println("[ 턴수 : " + stageMap.getTurnCount() + " ]\n");
            return true;
        }
        return false;
    }
```
- q를 포함하고 있거나, StageMap이 모두 공+구멍 상태가 되어 종료되었을 경우 축하 메시지와 턴 수를 출력한다. 

<br>

### 8. InputView
> **makeMapInformation : 파일 입력 처리**

```java
    public static String makeMapInformation(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("[ERROR] 유효한 파일이 아닙니다.");
        }
        return sb.toString();
    }
```
- fileName 경로에 있는 텍스트 파일을 읽어 Stringbuilder로 묶은 후, 반환하는 로직이다.

<br>

### 9. Constant(추가) : static final로 기본 자료형에 의미를 부여한다.

<br>

### 10. StageRepository(추가)

> **변수 및 생성자**
```java
    private static final StageRepository stageRepository = new StageRepository(new LinkedHashMap<>());
    private static final Map<Character, Integer> basicValue = ValueMapper.getBasicValue();
    private final Map<String, List<String>> stageMaps;

    private StageRepository(Map<String, List<String>> stageMaps) {
        this.stageMaps = stageMaps;
    }

    public static StageRepository makeRepository() {
        return stageRepository;
    }
```
- ```stageMaps```에 초기화된 지도를 가지고 있으며, 이를 사용하여 ```StageMap```객체를 reset한다.
- 초기화 후 상태 변경이 되지 않으므로, 싱글톤으로 설계하였다. ```makeRepository``` 메서드를 통해 싱글톤 객체를 반환받을 수 있다.

<br>

> **MakeInnerMapValue** : 기존 StageMapReader의 로직을 활용한다.
```java
    public void makeInnerMapValue(String input) {
        String[] split = input.split("\n");
        String stageNumber = "";
        List<String> tempStage = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("Stage")) {
                stageNumber = split[i].trim();
                continue;
            }
            if (split[i].contains(String.valueOf(CHAR_DIVIDE_STAGE))) {
                stageMaps.put(stageNumber, tempStage);
                tempStage = new ArrayList<>();
                continue;
            }
            tempStage.add(changeToNumber(split[i]));
            if (i == split.length - 1) {
                stageMaps.put(stageNumber, tempStage);
                break;
            }
        }
    }
```
- input을 ```Map<String, List<String>```으로 관리하기 위해 분해 및 저장하는 로직을 수행한다.
