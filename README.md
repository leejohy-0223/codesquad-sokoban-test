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
$ java Application
```
- 위와 같이 gist repository를 통한 실행이 안 될 경우, [GitHub Step3 Repository](https://github.com/leejohy-0223/codesquad-sokoban-test/tree/step3) 에서 프로젝트 clone을 통해 다음과 같이 실행한다.
```
$ git clone https://github.com/leejohy-0223/codesquad-sokoban-test.git
$ cd codesquad-sokoban-test
$ git checkout step3
$ ./gradlew clean build
$ java -jar build/libs/codesquad-sokoban-test-1.0-SNAPSHOT.jar
```

<br>

## 📝 풀이

- map.txt를 읽을 수 있는 InputView 메서드를 생성한다.
- 초기화(reset) 처리가 필요하다. txt로 부터 map 정보를 String으로 묶어서 저장하는 방식으로 변경한다.
- o + O = 0이 되고, 분리도 가능하다. o 또는 0의 개수가 0이라면, 다음 스테이지로 넘어간다.
- 플레이어의 턴 수를 카운트 한다. 매 스테이지마다 몇 턴 걸렸는지 출력한다.

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

<br>

### 2. StageMapReader
> **startThisStage : 요청된 Stage를 대상으로 gameStart 호출**


<br>

### 3. StageMap
> **movePlayer : Player의 움직임 여부를 결정** 

<br>

### 4. PlayerPosition
> **moveToHere : position 변경**


<br>

### 5. ValueMapper : 변경사항 없음

<br>

### 6. DirectionValue(추가)
> **변수**

<br>

### 7. GameController(추가)
> **gameStart : StageMap을 기반으로 사용자에게 입력을 받아 게임 시작**

<br>

### 8. InputView(추가)
> **requestInputFromUser : 사용자 입력 담당**


