package sokoban;

import static sokoban.Constant.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputView {
    public static String makeMapInformation(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("[ERROR] 유효한 파일이 아닙니다.");
        }
        return sb.toString();
    }

    public static List<Character> requestInputFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print(PROMPT);
        String input = sc.nextLine();
        return input.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
    }

    public static String requestYesOrNo() {
        System.out.print(PROMPT);
        System.out.print("현재 슬롯에 저장된 상태가 있습니다. 덮어씌우시겠습니까? (y/n 입력) : ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
