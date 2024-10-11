package beecrowd_1138;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int A = scanner.nextInt();
            int B = scanner.nextInt();

            if (A == 0 && B == 0) {
                break;
            }

            int[] countB = countDigits(B);
            int[] countA = countDigits(A - 1);

            for (int i = 0; i < 10; i++) {
                System.out.print((countB[i] - countA[i]) + (i < 9 ? " " : "\n"));
            }
        }

        scanner.close();
    }

    public static int[] countDigits(int N) {
        int[] count = new int[10];
        if (N < 0) return count; // Если N меньше 0, возвращаем нули (предотвращаем ошибки)

        int factor = 1; // Множитель для текущего разряда

        while (N / factor > 0) {
            int lower = N % factor; // Меньшая часть числа
            int current = (N / factor) % 10; // Текущая цифра
            int higher = N / (factor * 10); // Большая часть числа

            // Для всех цифр кроме текущей
            for (int i = 0; i < current; i++) {
                count[i] += (higher + 1) * factor;
            }

            // Обработка текущей цифры (корректируем для старших разрядов)
            count[current] += higher * factor + lower + 1;

            // Для цифр больше текущей
            for (int i = current + 1; i < 10; i++) {
                count[i] += higher * factor;
            }

            // Специальная обработка для цифры 0
            if (current == 0) {
                count[0] -= factor; // Корректируем подсчёт для 0, чтобы учесть отсутствие ведущих нулей
            }

            factor *= 10; // Переход к следующему разряду
        }

        return count;
    }
}