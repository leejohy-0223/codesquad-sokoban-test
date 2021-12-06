package sokoban;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StageMapReader {

    private static final Map<Character, Integer> basicValue = new HashMap<>();
    private static final List<StageMap> stageMaps = new ArrayList<>();

    public static StageMapReader initialMapReader() {
        basicValue.put('#', 0);
        basicValue.put('O', 1);
        basicValue.put('o', 2);
        basicValue.put('P', 3);
        basicValue.put('=', 4);
        basicValue.put(' ', 5); // void

        return new StageMapReader();
    }

    void mappingTwoDimensionalArray(String input) {
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
            if (i == split.length - 1) {
                tempStage.add(changeToNumber(split[i]));
                stageMaps.add(StageMap.makeStage(stageNumber, tempStage));
                break;
            }
            tempStage.add(changeToNumber(split[i]));
        }
    }

    private String changeToNumber(String s) {
        StringBuilder sb = new StringBuilder();
        for (char tempChar : s.toCharArray()) {
            sb.append(basicValue.get(tempChar));
        }
        return sb.toString();
    }

    public void printStageInfo() {

    }
}
