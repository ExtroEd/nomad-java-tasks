package beecrowd_1551;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        scanner.nextLine(); // Пропускаем перевод строки после числа

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine().toLowerCase();

            // Массив для подсчета вхождений букв
            boolean[] letterPresent = new boolean[26];
            int uniqueLettersCount = 0;

            for (int j = 0; j < line.length(); j++) {
                char ch = line.charAt(j);
                if (ch >= 'a' && ch <= 'z') {
                    int index = ch - 'a'; // Вычисляем индекс для буквы

                    // Если это первая встреча данной буквы, отмечаем её
                    if (!letterPresent[index]) {
                        letterPresent[index] = true;
                        uniqueLettersCount++;
                    }
                }
            }

            if (uniqueLettersCount == 26) {
                System.out.println("frase completa");
            } else if (uniqueLettersCount >= 13) {
                System.out.println("frase quase completa");
            } else {
                System.out.println("frase mal elaborada");
            }
        }

        scanner.close();
    }
}
