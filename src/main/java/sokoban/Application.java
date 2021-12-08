package sokoban;

import java.util.ArrayList;

public class Application {
    public static void main(String[] args) throws Exception {
        // args[0]은 encryption 되어있으므로, 변환 처리
        String mapInput = InputView.makeDecryptString(args[0]);

        StageMapReader mapReader = StageMapReader.initialMapReader(new ArrayList<>(), mapInput);
        mapReader.startStage();
    }
}
