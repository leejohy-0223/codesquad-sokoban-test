## 4단계 : 추가기능 구현

<details>
<summary> 문제 설명 및 요구사항 </summary>

### 요구 사항
- 다양한 추가 기능을 구현한다.

<br>

#### 1. 저장하기 불러오기 기능 [추가기능 1]
- 1 - 5: 세이브 슬롯 1 - 5 선택 
- S 키로 현재 진행상황을 저장한다.
- L 키로 세이브 슬롯에서 진행상황을 불러온다.
```
SOKOBAN> 2S
2번 세이브 슬롯 상태
2번 세이브에 진행상황을 저장합니다.
SOKOBAN> 3L
3번 세이브에서 진행상황을 불러옵니다.
```

<br>

#### 2. 지도 데이터 변환하기 프로그램 [추가기능 2]
- 지도 데이터 map.txt 를 읽어서 일반 텍스트 에디터로 읽을 수 없는 map_enc.txt로 변환하는 프로그램을 추가로 작성한다.
- 3 단계에서 구현한 게임이 map.txt 가 아닌 map_enc.txt 를 불러와서 실행할 수 있도록 수정한다.

<br>

#### 3. 되돌리기 기능 및 되돌리기 취소 기능 구현 [추가기능 3]
- u키를 누르면 한 턴 되돌리기, U키를 누르면 되돌리기 취소하기를 구현한다.

<br>

</details>

<details>
<summary> 구현 코드 실행 결과 </summary>

 
### 1. 저장 & 불러오기 기능 추가 구현

<br>

#### 1. 저장 및 불러오기 ([1-5] 중 빈 공간 저장)
```
SOKOBAN> s

######
#    #
#  oO#
# OoP#
######

s: 아래쪽으로 이동합니다.
SOKOBAN> 1S // 슬롯 1번에 저장 요청을 했다.
1번 세이브에 게임이 정상적으로 저장되었습니다.

######
#    #
#  oO#
# OoP#
######

... // 저장 후 다른 공간으로 Player 이동시켰다.

SOKOBAN> a

######
# P  #
#  oO#
# Oo #
######

a: 왼쪽으로 이동합니다. // 현재 Player 위치
SOKOBAN> 1L
1번 세이브에서 진행 상황을 불러옵니다.
< Stage 3 >

######
#    #
#  oO#
# OoP# // Player의 위치가 변경되었다.
######

SOKOBAN> 
```
- 1S에는 기존에 아무 저장내용이 없어 바로 저장된다.
- 이후 1L을 통해 1S에서 저장된 상태 및 움직인 횟수를 가져온다.

<br>

#### 2. 저장 (빈 공간 아닐 경우)
```
SOKOBAN> 1S
SOKOBAN> 현재 슬롯에 저장된 상태가 있습니다. 덮어씌우시겠습니까? (y/n 입력) : 
```
- 현재 슬롯이 비어있지 않을 경우 덮어씌울지 여부를 물어본다.

<br>

#### 3. 불러오기 (빈 공간일 경우)
```
SOKOBAN> 5L
5번 세이브에 저장된 진행상황이 없습니다. [1-5] 사이의 세이브에 저장하신 후 불러와주세요.
```
- 저장된 상태가 없는 슬롯에 대해 불러오기 요청을 할 경우, 위와 같은 메시지를 출력하고 다시 입력을 요청한다.

<br>

#### 4. [1-5]가 아닌 입력에 대한 저장 및 불러오기 요청
```
SOKOBAN> 6S
1-5 사이의 숫자만 선택하세요
SOKOBAN> 6L
1-5 사이의 숫자만 선택하세요
```
- 위와 같이 재 입력을 요청한다.

<br>

### 2. 지도 데이터 변환하기 프로그램 구현

<br>

#### 1. 변환 전 맵 정보 (stateMap.txt)
```
Stage 1
#####
#OoP#
#####
=====
Stage 2
######
#  P #
#o####
# #
#O#
###
=====
Stage 3
######
#P   #
#  oO#
# Oo #
######
=====
Stage 4
######
#P   ##
# oo  #
# #O O#
#     #
#######
=====
Stage 5
  ####
###  #
#P Oo##
#   o #
# #O  #
#     #
#######
```

<br>

#### 2. 변환 후 맵 정보 (stageMap_enc.txt)
```
y56GAg4a8nagUANuAOlxVpGyoTKOYFzhbKe04On+qvVSBYyxGRaLFBIgQOIeNGBJivxhfuNJpdyRa5+KlJSPY5+XyFK9jowXZNURvljidFnm8qvAShFoIUbrGYp0Ct7uA91WmdElt+3GOJZB3nDsGrrSiA46xP+QdMMzgWMOhXL6D5/D9Da2Sux0HQdEy/6Od/HVbYESeDsvjlPrE0xepWxqaOx3yigPx+j1NfVlME6HrUNC3mxopjYM+gj2/cF2B7Rn5g+VcE9YOBbG7SPteGDzCvgrmKHZwZZuhdtxTKjz57SM/3mkDhX4OZ9BNgnocShEzz9EjLNO/qCYDWLRGg==
```

<br>

#### 3. 변환 된 맵 기반으로 정상 출력 확인
```
소코반의 세계에 오신 것을 환영합니다!

< Stage 1 >

#####
#OoP#
#####

< Stage 2 >

######
#  P #
#o####
# #   
#O#   
###   

< Stage 3 >

######
#P   #
#  oO#
# Oo #
######

< Stage 4 >

###### 
#P   ##
# oo  #
# #O O#
#     #
#######

< Stage 5 >

  #### 
###  # 
#P Oo##
#   o #
# #O  #
#     #
#######
```
- encryption 된 파일을 전달했음에도, 정상적으로 맵 정보를 읽어와 출력한다.

<br>

### 3. 되돌리기 기능 및 되돌리기 취소 기능 구현

<br>

#### 1. 되돌리기(u) 실행 
```
######
#P   #
#  oO#
# Oo #
######

SOKOBAN> d

######
# P  #
#  oO#
# Oo #
######

d: 오른쪽으로 이동합니다.
SOKOBAN> s

######
#    #
# PoO#
# Oo #
######

s: 아래쪽으로 이동합니다.
SOKOBAN> u // 되돌리기 실행
한 턴 되돌렸습니다.

######
# P  #
#  oO#
# Oo #
######

현 시점에서 가능한 되돌리기 수는 1회 입니다.
SOKOBAN> u
한 턴 되돌렸습니다.

######
#P   #
#  oO#
# Oo #
######

현 시점에서 가능한 되돌리기 수는 0회 입니다.
SOKOBAN> u
되돌릴 상태가 없습니다.
```
- 되돌린 후의 맵과 가능한 되돌리기 횟수를 출력한다.
- 되돌릴 상태가 없을 경우, 상태가 없다고 출력한다.

<br>

#### 2. 되돌리기 취소(u) 실행
```
(1. 되돌리기 실행에 이어짐) 
SOKOBAN> U
되돌리기를 취소했습니다.

######
# P  #
#  oO#
# Oo #
######

현 시점에서 가능한 취소 수는 1회 입니다.
SOKOBAN> U
되돌리기를 취소했습니다.

######
#    #
# PoO#
# Oo #
######

현 시점에서 가능한 취소 수는 0회 입니다.
SOKOBAN> U
되돌리기 취소할 상태가 없습니다.
```
- 되돌리기 취소 상태의 가능 횟수는 되돌리기(u)를 수행한 횟수와 동일하다.(2회 되돌렸으므로 취소도 2회 가능)

<br>

#### 3. 되돌리기 취소(U) 후 되돌리기(u)
```
######
# P  #
#  oO#
# Oo #
######

현 시점에서 가능한 되돌리기 수는 1회 입니다.
SOKOBAN> U
되돌리기를 취소했습니다.

######
#P   #
#  oO#
# Oo #
######

현 시점에서 가능한 취소 수는 0회 입니다.
SOKOBAN> u
되돌릴 상태가 없습니다.
```
- 되돌리기 취소(U)가 정상적으로 수행되면, 되돌리기(u)는 0으로 초기화된다.
- 즉 되돌리기 취소(U)는 되돌리기(u)할 수 없도록 구현하였다.

<br>

#### 4. 정상 동작 후 되돌리기 취소(U)
```
< Stage 4 >

###### 
#P   ##
# oo  #
# #O O#
#     #
#######

SOKOBAN> d

###### 
# P  ##
# oo  #
# #O O#
#     #
#######

d: 오른쪽으로 이동합니다.
SOKOBAN> d

###### 
#  P ##
# oo  #
# #O O#
#     #
#######

d: 오른쪽으로 이동합니다.
SOKOBAN> U
되돌리기 취소할 상태가 없습니다.
```
- 정상적으로 움직인 후에는 되돌리기 취소(U)할 상태가 존재하지 않는다. 
- 되돌리기(u)를 할 때만 되돌리기 취소(U)가 가능하다.

<br>

</details>

<br> 

#### 👉🏻 참고 Repository : [GitHub Step4 Repository](https://github.com/leejohy-0223/codesquad-sokoban-test/tree/step4)

## 🚀 실행 
- Git, Java는 설치되어 있다고 가정한다.
- step4 revision Id : 
```
$ git clone https://gist.github.com/fa8eb5f185967321dd0fb9a81fdc5baa.git step4
$ cd step4
$ git checkout step4_리비젼_id
$ javac *.java
$ java EncryptionApplication stageMap.txt  // Encryption map을 만드는 Application을 먼저 실행한다.
$ java Application stageMap_enc.txt // Application에는 Encryption 된 결과를 전달한다. 
```
- 위와 같이 gist repository를 통한 실행이 안 될 경우, [GitHub Step4 Repository](https://github.com/leejohy-0223/codesquad-sokoban-test/tree/step4) 에서 프로젝트 clone을 통해 다음과 같이 실행한다.
```
$ git clone https://github.com/leejohy-0223/codesquad-sokoban-test.git
$ cd codesquad-sokoban-test
$ git checkout step4
$ ./gradlew clean build
$ java -jar build/libs/codesquad-sokoban-test-1.0-SNAPSHOT.jar src/main/resources/stageMap_enc.txt
```

<br>

## 📝 풀이

- [X] 상태를 저장할 수 있는 새로운 세이브 슬롯을 만들기 
    - 순차 stage 진행 과정에서 세이브를 진행한 후 L을 통해 저장된 세이브의 상태를 그대로 가져와서 실행한다.
    - Load : 기존에 저장 내역이 없을 경우, ``세이브에 저장된 진행상황이 없습니다``를 출력한다.
    - Save : 기존에 저장 내역이 있을 경우, ``덮어씌우시겠습니까``를 출력 후, 사용자로부터 y, n을 입력받아 진행한다.
    - 1~5 사이의 숫자 이외에 다른 값이 입력되면 ``1-5 사이의 숫자만 선택하세요``를 출력하고 재 입력을 요청한다. 
    - 해당 세이브가 clear되면 load 전 순차적으로 실행되고 있던 Stage의 다음 단계가 실행된다. 
    
<br>

- [X] 지도 데이터 변환 프로그램 만들기
    - map.txt를 encryption할 수 있는 프로그램을 추가로 생성한다.
    - java AES 암호화를 사용한다.
    - 먼저 암호화처리 애플리케이션을 통해 암호화 파일을 생성하고, 이를 게임 실행 애플리케이션으로 전달하는 방식으로 구현한다. 

<br>

- [X] 되돌리기 기능, 되돌리기 취소 기능 만들기
    #### u (한 턴 되돌리기)
      - 매번 이동 마다 되돌리기 위한 restoreStack에 이동 전 상태를 push한다.
      - u가 입력되면 되돌리기 위한 cancelStack에 현재 상태를 push하고, restoreStack에서 가장 최근 상태를 꺼내어 현재 객체 상태로 변경한다.
      - 매 번 돌린 후 지도 결과와 가능한 되돌리기 회수를 출력한다.
      - 이후 U(되돌리기 취소)가 정상적으로 수행되면 restoreStack를 clear 하여 되돌리기 취소 <-> 되돌리기가 반복되지 않도록 한다.
  <br>
  
    #### U (되돌린거 취소하기)
      - 앞서 설명된 u (한 턴 되돌리기)가 수행될 때만, cancelStack으로 되돌리기 전 상태가 저장된다.
      - U가 입력되면 이곳에 저장된 상태를 가져와 현재 객체 상태를 변경한다.
      - u, U가 아닌 정상적인 이동을 했을 경우에는 U 내용이 필요가 없어지므로 clear 처리를 한다.
      - U가 정상적으로 수행되면 restoreStack을 clear 한다.

<br>

## 🔧 구현 
- 주요 로직에 대한 메서드 설명을 작성한다.
- 기존과 동일한 메서드 설명은 생략한다.
- 추가 기능은 다음과 같이 명명한다.
   - **추가기능 1** : 저장 / 불러오기 기능 
   - **추가기능 2** : 지도 데이터 변환 기능
   - **추가기능 3** : 되돌리기 / 되돌리기 취소 기능
  
<br>

#### 1. Application 
- Step 1 : 지정된 String 입력을 받아 전체적인 흐름을 구성한다.
- Step 2 : 몇 번째 Stage를 시작할 지 추가로 전달한다.
- Step 3 : main의 args로 넘어오는 인자를 InputView로 전달하여 처리한다.
- Step 4 : main의 args로 넘어오는 파일은 Encryption 상태이므로, 이를 InputView로 전달해서 처리한다.

<br>

#### 2. StageMapReader 
- Step 1 : Application으로부터 전달받은 String을 parsing해서 StageMap 객체를 생성한다. 또한 StageMap 객체에 직접적으로 접근한다.
- Step 2 : Application으로부터 전달받은 stage Number에 해당하는 StageMap 객체를 가져와 GameController에 전달한다. 
- Step 3 : 기존의 입력 String에 대한 Parsing 역할을 새로운 객체인 StageRepository로 할당했다. 필요한 초기화는 StageRepository를 통해 진행한다.
- Step 4 : 동일

<br>

#### 3. StageMap
- Step 1 : 지도 데이터의 정보를 저장하고, PlayerPosition 객체를 가진다.
- Step 2 : GameController로부터 이동 정보를 받아 PlayerPosition을 변경시킨다.
- Step 3 : 입력에 대한 이동 로직이 주로 추가되었다.
- Step 4 : Slot에 저장하기 위한 deep copy 함수 및 되돌리기 / 취소 기능을 위한 stack이 추가되었다.

<br>

#### 4. PlayerPosition
- Step 1 : Player의 위치 정보를 가지는 객체이다.
- Step 2 : 동일
- Step 3 : 동일
- Step 4 : 동일

<br>  

#### 5. ValueMapper
- Step 1 : 기호(char)와 저장값(int)을 관리하는 Map을 가지며, 필요한 객체에게 제공한다.
- Step 2 : 동일
- Step 3 : 로직은 동일하나, Constant(상수)를 사용하도록 변경하였다.
- Step 4 : 동일

<br>

#### 6. (Step2부터 추가) DirectionValue
- Step 2 : 방향에 따른 방향 이름, 기호, 이동량을 가지는 Enum 이다.
- Step 3 : 동일
- Step 4 : 되돌리기, 되돌리기 취소를 위한 enum 객체가 추가되었다.

<br>

#### 7. (Step2부터 추가) GameController
- Step 2 : StageMapReader로 부터 StageMap 객체를 전달받는다. 사용자 입력을 받아 Enum으로 변경 후 StageMap에 전달하는 역할을 수행한다.
- Step 3 : 리셋 및 game Finish 로직을 추가했다.
- Step 4 : 게임 저장을 위한 GameSlot 클래스를 인스턴스 변수로 갖도록 하였다. 처리 관련 로직이 추가되었다.

<br>

#### 8. (Step2부터 추가) InputView
- Step 2 : 사용자 입력 담당을 한다. 
- Step 3 : FileReader와 BufferedReader를 통해 Application.main 메서드로 전달되는 파라미터와 일치하는 파일을 찾아 내용을 String으로 변경하는 역할이 추가 되었다. 
- Step 4 : Encryption된 file을 Decryption 한 후 결과를 반환하는 로직이 추가되었다. 또한 슬롯 값을 덮어씌울지 여부를 묻는 메서드도 추가되었다.

<br> 

#### 9. (Step3부터 추가) Constant
- Step 3 : 상수를 관리하며, 기본 자료형(primitive types)에 의미를 부여했다.
- Step 4 : 동일

<br>

#### 10. (Step3부터 추가) StageRepository
- Step 3 : 처음에 입력된 지도를 저장하여, reset에 활용하기 위한 객체이다. 싱글톤으로 관리된다.
- Step 4 : 동일

<br>

#### 11. (Step4부터 추가) EncryptionApplication
- Step 4 : 파일을 Encryption 하기 위한 main 함수를 가진다.

<br>

#### 12. (Step4부터 추가) EncryptionMaker 
- Step 4 : String을 encrypt, decrypt할 수 있는 메서드를 가진다. 

<br>

#### 13. (Step4부터 추가) FileMaker
- Step 4 : 정상 map 파일을 encryption 파일로 변환하여 저장하는 메서드를 가진다.

<br>

#### 14. (Step4부터 추가) GameSlot
- Step 4 : 현재 게임의 상태를 save, load할 수 있는 메서드를 가진다.  

<br>

### 1. Application
> **main 메서드** 
```java
    public static void main(String[] args) throws Exception {
        String mapInput = InputView.makeDecryptString(args[0]);
        StageMapReader mapReader = StageMapReader.initialMapReader(new ArrayList<>(), mapInput);
        mapReader.startStage();
}
```
- [추가기능 2]```InputView.makeDecryptString```을 통해 암호화된 파일로부터 복호를 통해 정상 Map을 읽어온 후 mapReader로 전달하는 방식으로 변경했다.

<br>

### 2. StageMapReader : 변경 없음
<br>

### 3. StageMap
> **[추가기능 3] movePlayer** 
```java
    public void movePlayer(DirectionValue dValue) {
        if (restoreOrCancel(dValue)) {
            return;
        }
        if (cancelStack.size() != 0) {
            cancelStack.clear();
        }
        ... 
    }
```
- ``DirectionValue``가 restore / cancel일 경우 ``restoreOrCancel`` 실행 후 return한다.
- 일반 움직임이 감지될 경우 ```cancelStack```를 초기화 하여 'U'를 통한 되돌리기 취소를 할 수 없도록 한다.

<br>

> **[추가기능 3] executeRestore : 되돌리기가 입력될 경우 수행**
```java
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
```
1. 되돌릴 상태가 없다면 return
2. 현재 상태를 복사하여 ``cancelStack``에 넣는다. 이를 통해 되돌리기를 취소할 수 있다.
3. ``restoreStack``의 내용을 pop하여 현재 상태에 반영한다.

<br>

> **[추가기능 3] executeCancel : 되돌리기 취소가 입력될 경우 수행**
```java
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
```
1. 되돌리기 취소할 상태가 없다면 return
2. 되돌리기 취소가 정상적으로 수행된다면, ``restoreStack``을 clear하여 다시 되돌릴 수 없도록 한다.
3. ``cancelStack``에서 최신 상태를 Pop하여 현재 상태로 반영한다.

<br>

> **[추가기능 3] stackPopAndChangeMap, changeMapToThis**
```java
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
```
- ```stackPopAndChangeMap``` : 파라미터로 넘어온 stack에서 pop 수행 후 현재 상태를 변경할 수 있는 ``changeMapToThis``를 호출한다.
- ``changeMapToThis`` : 파라미터로 넘어온 map을 현재 상태로 반영한다.

<br>

> **[추가기능 1] allStatusCopy, statusCopyExcludeStack, stageMapDeepCopy**
```java
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
```
- slot에 저장하기 위한 상태 복사 메서드이다.

<br>

### 4. PlayerPosition : 변경 사항 없음

<br>

### 5. ValueMapper : 변경 사항 없음

<br>

### 6. DirectionValue : [추가 기능 3] restore, cancel 추가
```java
        RESTORE("한 턴 되돌립니다", 'u', 0, 0),
        CANCEL("되돌리기를 취소합니다", 'U', 0, 0),
```

<br>

### 7. GameController
> **gameStart : StageMap을 기반으로 사용자에게 입력을 받아 게임 시작**
```java
    public void gameStart(StageMap stageMap, StageRepository stageRepository) {
        List<Character> inputs;
        while (true) {
            inputs = InputView.requestInputFromUser();
            if (isSaveOrLoadRequest(inputs)) {
                stageMap = saveOrLoadRequest(stageMap, inputs);
                continue;
            }
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
        }
    }
```
- [추가 기능 1] : ``saveOrLoadRequest``를 통해 저장, 불러오기를 수행한다.

<br>

> **[추가 기능 1] saveRequest, saveGame**
```java
    private StageMap saveRequest(StageMap stageMap, List<Character> inputs) {
        try {
            saveGame(Character.getNumericValue(inputs.get(0)), stageMap);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return stageMap;
    }

    private void saveGame(int slotNumber, StageMap stageMap) {
        if (gameSlot.isNotEmpty(slotNumber)) {
            String yesOrNo = InputView.requestYesOrNo();
            if (yesOrNo.equals("y")) {
                gameSlot.saveStageMap(slotNumber, stageMap);
            }
            return;
        }
        gameSlot.saveStageMap(slotNumber, stageMap);
    }
```
- ``saveRequest`` : ``saveGame``메서드를 호출하여 예외가 있을 경우 catch하는 역할을 수행한다.
- ``saveGame`` : ``GameSlot``이 비워져있는지 여부에 따라 저장할지 말지를 결정하는 역할을 한다.

<br>

> **[추가 기능 1] loadRequest, loadGame**
```java
    private StageMap loadRequest(StageMap stageMap, List<Character> inputs) {
        try {
            stageMap = loadGame(Character.getNumericValue(inputs.get(0)), stageMap);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return stageMap;
    }

    private StageMap loadGame(int slotNumber, StageMap stageMap) {
        if (gameSlot.isNotEmpty(slotNumber)) {
            return gameSlot.loadSavedGame(slotNumber);
        }
        System.out.println(slotNumber + "번 세이브에 저장된 진행상황이 없습니다. [1-5] 사이의 세이브에 저장하신 후 불러와주세요.");
        return stageMap;
    }
```
- ```loadRequest``` : ``loadGame`` 수행 시 발생하는 예외를 catch한다.
- ``loadGame`` : Slot의 상태에 따라 저장된 값을 반환하는 역할을 한다.

<br>

### 8. InputView
> **[추가기능 2] makeDecryptString : 파일 입력 처리**
```java
    public static String makeDecryptString(String fileName) throws Exception {
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            line = br.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("[ERROR] 유효한 파일이 아닙니다.");
        }
        return EncryptionMaker.decrypt(line);
    }
```
- Encryption된 파일을 불러와, ``EncryptionMaker``를 통해 decrypt한 String을 반환한다.

<br>

### 9. Constant : 변경사항 없음

<br>

### 10. StageRepository : 변경사항 없음

<br>

### 11. (추가) EncryptApplication
> **[추가기능 2] main**
```java
    public static void main(String[] args) throws Exception {
        FileMaker.makeEncryptedFile(args[0]);
    }
```
- ``FileMaker``를 통해 정상 map 파일을 encryption한 파일을 생성한다.

<br>

### 12. (추가) EncryptionMaker
> **[추가기능 2]내용 추가 필요 **
```java
    public static String alg = "AES/CBC/PKCS5Padding";
    private static final String key = "01234567890123456789012345678901";
    private static final String iv = key.substring(0, 16); // 16byte

    public static String encrypt(String text) throws Exception {
        ...
    }

    public static String decrypt(String cipherText) throws Exception {
        ...
    }
```
- ``encrypt`` : text를 encryption 한다.
- ``decrypt`` : text를 decryption 한다.

<br>

### 13. (추가) FileMaker
> **[추가기능 2]makeEncryptedFile, makeEncFileName**
```java
    public static void makeEncryptedFile(String mapFile) throws Exception {
        String encFileName = makeEncFileName(mapFile);
        String mapInput = InputView.makeMapInformation(mapFile);
        String encrypt = EncryptionMaker.encrypt(mapInput);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(encFileName, true));
            bw.write(encrypt);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String makeEncFileName(String mapFile) {
        String[] split = mapFile.split("/");
        String fileNameWithExtension = split[split.length - 1];
        String root = mapFile.substring(0, mapFile.indexOf(fileNameWithExtension));
        return root + fileNameWithExtension.substring(0, fileNameWithExtension.length() - 4) + "_enc.txt";
    }
```
- ```makeEncFileName``` : 입력된 파일 이름을 기반으로 새로운 encrytion 파일 이름을 생성한다.
- ```makeEncryptedFile``` : 새로운 파일 이름에 encryption된 String 내용을 담은 파일을 생성한다.

<br>

### 14. (추가) GameSlot
> **[추가기능 1] 인스턴스 변수 및 생성자**
```java
    private final Map<Integer, StageMap> gameSlot;

    public GameSlot(Map<Integer, StageMap> gameSlot) {
        this.gameSlot = gameSlot;
        for (int i = 1; i <= 5; i++) {
            gameSlot.put(i, null);
        }
    }
```
- ``StageMap`` 5개만 저장하고 있는 map을 생성하고 관리한다.

<br>

> **[추가기능 1] isNotEmpty, saveStageMap, loadSavedGame**
```java
    public boolean isNotEmpty(int slotNumber) {
        if (!gameSlot.containsKey(slotNumber)) {
            throw new IllegalArgumentException("1-5 사이의 숫자만 선택하세요");
        }
        return gameSlot.get(slotNumber) != null;
    }

    public void saveStageMap(int slotNumber, StageMap stageMap) {
        System.out.println(slotNumber + "번 세이브에 게임이 정상적으로 저장되었습니다.");
        stageMap.printOnlyStageMap();
        StageMap nStageMap = stageMap.allStatusCopy();
        gameSlot.put(slotNumber, nStageMap);
    }

    public StageMap loadSavedGame(int slotNumber) {
        System.out.println(slotNumber + "번 세이브에서 진행 상황을 불러옵니다.");
        StageMap calledStageMap = gameSlot.get(slotNumber).allStatusCopy();
        calledStageMap.printStatus();
        return calledStageMap;
    }
```
- ``isNotEmpty`` : 잘못된 slot number가 들어오면 예외를 발생시킨다. 해당 slot이 비워져있는 상태인지 출력한다.
- ``saveStageMap`` : gameSlot에 현재 게임상태의 모든 정보를 deep copy하여 저장한다.
- ``loadSavedGame`` : gameSlot에서 요청된 게임의 모든 정보를 depp copy하여 반환한다.


