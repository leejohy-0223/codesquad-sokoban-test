package sokoban;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StageRepository {

    private static final StageRepository stageRepository = new StageRepository(new LinkedHashMap<>());
    private static final Map<Character, Integer> basicValue = ValueMapper.getBasicValue();
    private final Map<String, List<String>> stageMaps;

    private StageRepository(Map<String, List<String>> stageMaps) {
        this.stageMaps = stageMaps;
    }

    public static StageRepository makeRepository() {
        return stageRepository;
    }

    public void makeInnerMapValue(String input) {
        String[] split = input.split("\n");
        String stageNumber = "";
        List<String> tempStage = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("Stage")) {
                stageNumber = split[i].trim();
                continue;
            }
            if (split[i].contains("=")) {
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

    public Map<String, List<String>> getStageMaps() {
        return stageMaps;
    }

    private String changeToNumber(String s) {
        StringBuilder sb = new StringBuilder();
        for (char tempChar : s.toCharArray()) {
            sb.append(basicValue.get(tempChar));
        }
        return sb.toString();
    }
}
