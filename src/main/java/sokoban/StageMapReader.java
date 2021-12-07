package sokoban;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StageMapReader {

    private final Map<Character, Integer> basicValue;
    private List<StageMap> stageMaps;

    private StageMapReader(Map<Character, Integer> basicValue, List<StageMap> stageMaps) {
        this.basicValue = basicValue;
        this.stageMaps = stageMaps;
    }

    public static StageMapReader initialMapReader(List<StageMap> stageMaps) {
        return new StageMapReader(ValueMapper.getBasicValue(), stageMaps);
    }

    public void mappingTwoDimensionalArray(String input) {
        String[] split = input.split("\n");
        String stageNumber = "";
        List<String> tempStage = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("Stage")) {
                stageNumber = split[i].strip();
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

    private String changeToNumber(String s) {
        StringBuilder sb = new StringBuilder();
        for (char tempChar : s.toCharArray()) {
            sb.append(basicValue.get(tempChar));
        }
        return sb.toString();
    }

    public void printStageInfo() {
        stageMaps.forEach(StageMap::printStatus);
    }

    public void startThisStage(String stageNum) {
        for (StageMap stageMap : stageMaps) {
            if (stageMap.isYourStage(stageNum)) {
                GameController.gameStart(stageMap);
                break;
            }
        }
    }
}
