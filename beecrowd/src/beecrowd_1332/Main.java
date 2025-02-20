package beecrowd_1332;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt(); // Переход на следующую строку
        scanner.nextLine(); // Переход на следующую строку

        for (int i = 0; i < n; i++) {
            String word = scanner.nextLine(); // Считываем каждое слово

            if (isOne(word)) {
                System.out.println(1);
            } else if (isTwo(word)) {
                System.out.println(2);
            } else if (isThree(word)) {
                System.out.println(3);
            }
        }

        scanner.close();
    }

    // Метод для проверки, что слово похоже на "one"
    private static boolean isOne(String word) {
        int count = 0;
        if (word.charAt(0) != 'o') count++;
        if (word.charAt(1) != 'n') count++;
        if (word.charAt(2) != 'e') count++;
        return count <= 1;
    }

    // Метод для проверки, что слово похоже на "two"
    private static boolean isTwo(String word) {
        int count = 0;
        if (word.charAt(0) != 't') count++;
        if (word.charAt(1) != 'w') count++;
        if (word.charAt(2) != 'o') count++;
        return count <= 1;
    }

    // Метод для проверки, что слово похоже на "three"
    private static boolean isThree(String word) {
        int count = 0;
        if (word.charAt(0) != 't') count++;
        if (word.charAt(1) != 'h') count++;
        if (word.charAt(2) != 'r') count++;
        if (word.charAt(3) != 'e') count++;
        if (word.charAt(4) != 'e') count++;
        return count <= 1;
    }
}
