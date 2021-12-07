package sokoban;

import java.util.ArrayList;

public class Application {

    public static void main(String[] args) {
        String mapInput = InputView.makeMapInformation();
        StageMapReader mapReader = StageMapReader.initialMapReader(new ArrayList<>(), mapInput);
        mapReader.mappingTwoDimensionalArray(mapInput);
        // mapReader.startThisStage("Stage 2");
    }
}
