package sokoban;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputView {

    public static final String PROMPT = "SOKOBAN> ";

    public static List<Character> requestInputFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print(PROMPT);

        String input = sc.nextLine();
        return input.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
    }
}
