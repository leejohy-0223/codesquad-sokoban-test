package sokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Application {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader("src/main/resources/stageMap.txt");
            BufferedReader br = new BufferedReader(fr);
            sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StageMapReader mapReader = StageMapReader.initialMapReader(new ArrayList<>());
        mapReader.mappingTwoDimensionalArray(sb.toString());
        mapReader.printStageInfo();
        mapReader.startThisStage("Stage 2");
    }
}
