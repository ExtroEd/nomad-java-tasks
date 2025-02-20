package leetcode_2000;
public class Solution {
    public static String reversePrefix(String word, char ch) {
        int index = -1;

        // Ищем, где в строке находится первый символ ch.
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ch) {
                index = i; // Запоминаем индекс.
                break;
            }
        }

        // Если символ не найден, возвращаем исходную строку.
        if (index == -1) {
            return word;
        }

        // Создаём строку с реверсированной частью.
        StringBuilder result = new StringBuilder();

        // Реверсирует часть от 0 до index.
        for (int i = index; i >= 0; i--) {
            result.append(word.charAt(i));
        }

        // Добавляем оставшуюся часть строки.
        for (int i = index + 1; i < word.length(); i++) {
            result.append(word.charAt(i));
        }

        return result.toString();
    }
}