package sokoban;

import java.util.List;
import java.util.Map;

public class StageMapReader {

    private final StageRepository stageRepository;
    private List<StageMap> stageMaps;

    private StageMapReader(List<StageMap> stageMaps, StageRepository stageRepository) {
        this.stageMaps = stageMaps;
        this.stageRepository = stageRepository;
    }

    public static StageMapReader initialMapReader(List<StageMap> stageMaps, String mapInput) {
        StageRepository repository = StageRepository.makeRepository();
        repository.makeInnerMapValue(mapInput);
        return new StageMapReader(stageMaps, repository);
    }

    public void startStage() {
        initialStage();
        GameController gameController = GameController.initialController();
        System.out.println("소코반의 세계에 오신 것을 환영합니다!\n");
        for (StageMap stageMap : stageMaps) {
            stageMap.printStatus();
            gameController.gameStart(stageMap, stageRepository);
        }
        System.out.println("전체 게임을 클리어하셨습니다!\n" + "축하드립니다!");
    }

    private void initialStage() {
        Map<String, List<String>> initialMap = stageRepository.getStageMaps();
        for (String key : initialMap.keySet()) {
            stageMaps.add(StageMap.makeStage(key, initialMap.get(key)));
        }
    }
}
