package sokoban;

import java.util.ArrayList;

public class Application {
    public static void main(String[] args) {
        String mapInput = InputView.makeMapInformation(args[0]);
        StageMapReader mapReader = StageMapReader.initialMapReader(new ArrayList<>(), mapInput);
        mapReader.startStage();
    }
}
