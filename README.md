## 4단계 : 추가기능 구현

<details>
<summary> 문제 설명 및 요구사항 </summary>

### 요구 사항
- 다양한 추가 기능을 구현한다.

<br>

#### 1. 저장하기 불러오기 기능
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

#### 2. 지도 데이터 변환하기 프로그램
- 지도 데이터 map.txt 를 읽어서 일반 텍스트 에디터로 읽을 수 없는 map_enc.txt로 변환하는 프로그램을 추가로 작성한다.
- 3 단계에서 구현한 게임이 map.txt 가 아닌 map_enc.txt 를 불러와서 실행할 수 있도록 수정한다.

<br>

#### 3. 되돌리기 기능 및 되돌리기 취소 기능 구현
- u키를 누르면 한 턴 되돌리기, U키를 누르면 되돌리기 취소하기를 구현한다.

<br>

</details>

<details>
<summary> 구현 코드 실행 결과 </summary>

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
$ java Application stageMap.txt
```
- 위와 같이 gist repository를 통한 실행이 안 될 경우, [GitHub Step4 Repository](https://github.com/leejohy-0223/codesquad-sokoban-test/tree/step4) 에서 프로젝트 clone을 통해 다음과 같이 실행한다.
```
$ git clone https://github.com/leejohy-0223/codesquad-sokoban-test.git
$ cd codesquad-sokoban-test
$ git checkout step4
$ ./gradlew clean build
$ java -jar build/libs/codesquad-sokoban-test-1.0-SNAPSHOT.jar src/main/resources/stageMap.txt
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
    - 먼저 암호화 애플리케이션을 통해 암호화 파일을 생성하고, 이를 게임 실행 애플리케이션으로 전달하는 방식으로 구현한다. 

<br>

- [ ] 되돌리기 기능, 되돌리기 취소 기능 만들기
    - u를 누르면 한 턴을 되돌린다. 이 때 되돌린 방향을 stack에 저장한다.
    - U를 누르면 되돌리기가 취소된다. stack에서 꺼내 다시 턴을 수행한다.
    - turn count를 증감해야 한다.

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

<br>

### 2. StageMapReader
> **변수 및 생성자**

<br>

### 3. StageMap
> **movePlayer : Player의 움직임 여부를 결정** 

<br>

### 4. PlayerPosition : 변경사항 없음

<br>

### 5. ValueMapper : Constant 상수를 사용하도록 변경

<br>

### 6. DirectionValue : 변경 사항 없음

<br>

### 7. GameController
> **gameStart : StageMap을 기반으로 사용자에게 입력을 받아 게임 시작**

<br>

### 8. InputView
> **makeMapInformation : 파일 입력 처리**

<br>

### 9. Constant(추가) : static final로 기본 자료형에 의미를 부여한다.

<br>

### 10. StageRepository(추가)

> **변수 및 생성자**

