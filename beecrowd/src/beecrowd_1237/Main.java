package beecrowd_1237;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Основной цикл для обработки нескольких тестов.
        while (scanner.hasNextLine()){
            String str1 = scanner.nextLine();
            String str2 = scanner.nextLine();

            int maxLength = findLongestCommonSubstring(str1, str2);
            System.out.println(maxLength);
        }

        scanner.close();
    }

    // Функция для нажождения длины наибольшей общей подстроки.
    public static int findLongestCommonSubstring(String str1, String str2) {
        int length1 = str1.length();
        int length2 = str2.length();

        // Создаём двумерный массив для хранения длин совпадающих подстрок.
        int [][] dp = new int[length1 + 1][length2 + 1];
        int maxLength = 0;

        // Основной алгоритм для заполнения массива и поиска максимальной длины.
        for (int i = 1; i <= length1; i++) {
            for (int j = 1; j <= length2; j++) {
                // Проверяем символы строк вручную (без готовых функций).
                if (getCharAt(str1, i - 1) == getCharAt(str2, j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1; // Если символы совпадают, увеличием длину подстроки.
                    if (dp[i][j] > maxLength) {      // Обновляем максимальную длину.
                        maxLength = dp[i][j];
                    }
                } else {
                    dp[i][j] = 0; // Если символы не совпадают, сбрасывает длину до 0.
                }
            }
        }

        return maxLength;
    }

    // Функция для получения символа строки вручную.
    public static char getCharAt(String str, int index) {
        // Проходим по строке и возвращаем символ на указанной позиции.
        for (int i = 0; i < str.length(); i++) {
            if (i == index) {
                return str.charAt(i); // Вручную возвращаем символ по индексу.
            }
        }
        return '\0'; // Если индекс не верный, возвращаем пустой символ.
    }
}