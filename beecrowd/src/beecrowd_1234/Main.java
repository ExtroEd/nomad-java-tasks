package beecrowd_1234;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Цикл который будет работать, пока есть ввод
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine(); // Читаем строку из ввода
            String result = ""; // Инициализируем пустую строку для результата
            boolean upperCase = true; // Переменная для отслеживания, должен ли текущий символ быть заглавным

            for (int i = 0; i < input.length(); i++) {
                char currentChar = input.charAt(i); // Получаем текущий символ

                if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')) {
                    if (upperCase) {
                        if (currentChar >= 'a' && currentChar <= 'z') {
                            currentChar = (char) (currentChar - 32); // Преоброзование строчной буквы в заглавную
                        }
                    } else {
                        if (currentChar >= 'A' && currentChar <= 'Z') {
                            currentChar = (char) (currentChar + 32); // Преоброзование заглавной буквы в строчную
                        }
                    }
                    // Добавляем текущий символ к результату
                    result += currentChar;
                    upperCase = !upperCase; // Переключаем состояние на следующий символ
                } else {
                    // Если символ не буква, то просто добавляем его в результат
                    result += currentChar;
                }
            }

            System.out.println(result);
        }

        scanner.close();
    }
}
