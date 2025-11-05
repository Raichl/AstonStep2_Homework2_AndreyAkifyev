package manager_UI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.StringsHolder;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;


public record Menu(String text, List<Button> btnList) {

    @AllArgsConstructor
    @Getter
    public static class Button {
        private String text;
        private Pressed press;

        public void pressBtn() {
            press.press();
        }
    }

    public void showMenuAndChoose() {
        showMenu();
        getChoose();
    }

    private void showMenu() {
        StringBuffer outString = new StringBuffer(String.format("%s\n", text));
        IntStream.range(0, btnList.size()).forEach(index -> {
            Button btn = btnList.get(index);
            outString.append(String.format("%d. %s\n", index + 1, btn.text));
        });
        System.out.println(outString);
    }

    private void getChoose() {
        Scanner scanner = new Scanner(System.in);
        int userAnswer = 0;
        try {
            userAnswer = Integer.parseInt(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println(StringsHolder.ERROR_ILLEGAL_ARGUMENT);
            getChoose();
        }
        if (userAnswer < 1 || userAnswer > btnList.size()) {
            System.out.println(StringsHolder.ERROR_ILLEGAL_ARGUMENT);
            getChoose();
        } else {
            btnList.get(userAnswer - 1).pressBtn();
        }
    }
}
