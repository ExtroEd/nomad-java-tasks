package beecrowd_1255;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt(); // Читаем количество тестов
        scanner.nextLine(); // Переход на следующую строку после считывания теста

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine().toLowerCase(); // Читаем строку и приводим к нижнему регистру

            // Массив для посчёта частоты букв (26 элементов для каждой буквы)
            int[] letterCount = new int[26];

            // Проходим по каждому символу
            for (int j = 0; j < line.length(); j++) {
                char ch = line.charAt(j);

                // Проверяем является ли символ буквой
                if (ch >= 'a' && ch <= 'z') {
                    letterCount[ch - 'a']++; // Увеличиваем частоту буквы
                }
            }

            // Находим максимальное количество вхождений букв
            int maxCount = 0;
            for (int count : letterCount) {
                if (count > maxCount) {
                    maxCount = count;
                }
            }

            // Выводим все буквы с максимальной частотой в алфавитном порядке
            StringBuilder result = new StringBuilder();
            for (int k = 0; k < 26; k++) {
                if (letterCount[k] == maxCount) {
                    result.append((char) (k + 'a')); // Преобразуем индекс обратно в букву
                }
            }

            System.out.println(result); // Выводим результат для текущего теста
        }

        scanner.close();
    }
}