package beecrowd_1243;

// Сайт не принимает код.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            int totalLength = 0;
            int wordCount = 0;
            int wordStart = -1; // Индекс начала слова
            int length = line.length();

            for (int i = 0; i < length; i++) {
                char ch = line.charAt(i);

                // Проверяем, является ли текущий символ буквой
                if (isLetter(ch)) {
                    if (wordStart == -1) {
                        wordStart = i; // Начало нового слова
                    }
                } else {
                    // Если символ не буква, проверим, есть ли слово, которое мы закончили
                    if (wordStart != -1) {
                        int wordEnd = i - 1;
                        int wordLength = calculateWordLength(line, wordStart, wordEnd);

                        // Проверяем, что слово валидное
                        if (isValidWord(line, wordStart, wordEnd)) {
                            totalLength += wordLength;
                            wordCount++;
                        }

                        wordStart = -1; // Сбрасываем начало слова
                    }
                }
            }

            // Проверяем последнее слово в строке
            if (wordStart != -1) {
                int wordEnd = length - 1;
                int wordLength = calculateWordLength(line, wordStart, wordEnd);

                if (isValidWord(line, wordStart, wordEnd)) {
                    totalLength += wordLength;
                    wordCount++;
                }
            }

            // Рассчитываем среднюю длину
            int averageLength = (wordCount == 0) ? 0 : totalLength / wordCount;

            // Определяем сложность
            if (averageLength <= 3) {
                System.out.println(250);
            } else if (averageLength <= 5) {
                System.out.println(500);
            } else {
                System.out.println(1000);
            }
        }

        scanner.close();
    }

    // Метод для проверки, является ли символ буквой
    private static boolean isLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    // Метод для подсчета длины слова
    private static int calculateWordLength(String line, int start, int end) {
        // Если слово заканчивается точкой, уменьшаем длину
        if (line.charAt(end) == '.') {
            return end - start;
        }
        return end - start + 1;
    }

    // Метод для проверки валидности слова (все символы должны быть буквами)
    private static boolean isValidWord(String line, int start, int end) {
        for (int i = start; i <= end; i++) {
            char ch = line.charAt(i);
            // Если внутри слова есть точка или цифра, слово невалидно
            if (!isLetter(ch) && ch != '.') {
                return false;
            }
        }
        return true;
    }
}
