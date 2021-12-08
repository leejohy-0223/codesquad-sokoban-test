package sokoban;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileMaker {
    public static void makeEncryptedFile(String mapFile) throws Exception {
        String encFileName = makeEncFileName(mapFile);
        String mapInput = InputView.makeMapInformation(mapFile);
        String encrypt = EncryptionMaker.encrypt(mapInput);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(encFileName, true));
            bw.write(encrypt);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String makeEncFileName(String mapFile) {
        String[] split = mapFile.split("/");
        String fileNameWithExtension = split[split.length - 1];
        String root = mapFile.substring(0, mapFile.indexOf(fileNameWithExtension));
        return root + fileNameWithExtension.substring(0, fileNameWithExtension.length() - 4) + "_enc.txt";
    }
}
