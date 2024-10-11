package leetcode_20;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the string of brackets: ");
        String s = scanner.nextLine(); // Читаем входную строку
        boolean isValid = isValid(s); // Проверяем валидность скобок
        System.out.println(isValid); // Выводим результат
        scanner.close();
    }

    public static boolean isValid(String s) {
        char[] stack = new char[s.length()]; // Массив для стека
        int top = -1; // Индекс верхнего элемента стека

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i); // Получаем текущий символ

            // Если это открывающая скобка, добавляем её в стек
            if (ch == '(' || ch == '{' || ch == '[') {
                top++; // Увеличиваем индекс
                stack[top] = ch; // Добавляем скобку в стек
            } 
            // Если это закрывающая скобка
            else {
                // Если стек пуст, значит нет соответствующей открывающей скобки
                if (top == -1) {
                    return false;
                }
                // Проверяем соответствие закрывающей и открывающей скобки
                char topChar = stack[top]; // Получаем верхнюю скобку
                top--; // Уменьшаем индекс, удаляя верхнюю скобку из стека

                // Проверяем соответствие
                if ((ch == ')' && topChar != '(') || 
                    (ch == '}' && topChar != '{') || 
                    (ch == ']' && topChar != '[')) {
                    return false; // Если не соответствуют, возвращаем false
                }
            }
        }

        // Проверяем, пуст ли стек в конце
        return top == -1; // Если стек пуст, значит все скобки закрыты правильно
    }
}
