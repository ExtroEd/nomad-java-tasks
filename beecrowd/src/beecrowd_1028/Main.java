package beecrowd_1028;
import java.util.Scanner;

public class Main {

    // Функция для нахождения НОД с использованием алгоритма Евклида.
    public static int gcd(int a, int b) {
        // Пока одно из чисел не станет 0.
        while (b != 0) {
            int temp = b;
            b = a % b; // Остаток от деления большего числа на меньшее.
            a = temp; // Меняем значение местами.
        }
        return a; // Когда b стало 0, a - это НОД.
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Читаем количество тестов.
        int N = scanner.nextInt();

        // Обрабатываем каждый тест.
        for (int i = 0; i < N; i++) {
            int F1 = scanner.nextInt(); // Количество карточек у Ричрда.
            int F2 = scanner.nextInt(); // Количество карточек у Винсента.

            // Находим НОД двух чисел.
            int result = gcd(F1, F2);

            // Выводим результат.
            System.out.println(result);
        }

        scanner.close();
    }
}