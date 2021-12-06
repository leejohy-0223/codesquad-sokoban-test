package sokoban;

public class Application {
    public static void main(String[] args) {
        String input = "Stage 1\n"
            + "#####\n"
            + "#OoP#\n"
            + "#####\n"
            + "=====\n"
            + "Stage 2\n"
            + "  #######\n"
            + "###  O  ###\n"
            + "#    o    #\n"
            + "# Oo P oO #\n"
            + "###  o  ###\n"
            + " #   O  # \n"
            + " ########";

        StageMapReader mapReader = StageMapReader.initialMapReader();
        mapReader.mappingTwoDimensionalArray(input);
        mapReader.printStageInfo();
    }
}
