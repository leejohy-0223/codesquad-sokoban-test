package sokoban;

import java.util.HashMap;
import java.util.Map;

public class StageMapRepository {

    private static final StageMapRepository stageMapRepository = new StageMapRepository(new HashMap<>());
    private final Map<String, String> stageMaps;

    private StageMapRepository(Map<String, String> stageMaps) {
        this.stageMaps = stageMaps;
    }

    public static StageMapRepository makeRepository() {
        return stageMapRepository;
    }

    public void makeInnerMapValue(String input) {
        String[] split = input.split("\n");
        String stageNumber = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("Stage")) {
                stageNumber = split[i].trim();
                continue;
            }
            if (split[i].contains("=")) {
                stageMaps.put(stageNumber, sb.toString());
                sb = new StringBuilder();
                continue;
            }
            sb.append(split[i]).append("\n");
            if (i == split.length - 1) {
                stageMaps.put(stageNumber, sb.toString());
                break;
            }
        }
    }
}
