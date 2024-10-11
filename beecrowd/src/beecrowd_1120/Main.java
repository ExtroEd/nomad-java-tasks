package beecrowd_1120;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Читаем сломанную цифру и исходное число.
            char D = scanner.next().charAt(0); // Символ цифры, который не работает.
            String N = scanner.next();         // Исходное число как строка.

            // Если ввод "0 0", заканчиваем выполнение программы.
            if (D == '0' && N.equals("0")) {
                break;
            }

            // Строим строку результата, игнорируя сломанную цифру.
            StringBuilder filtered = new StringBuilder();
            for (int i = 0; i < N.length(); i++) {
                if (N.charAt(i) != D) {
                    filtered.append(N.charAt(i)); // Копируем символы, кроме сломанной цифры.
                }
            }

            // Убираем ведущие нули вручную.
            int firstNonZeroIndex = 0;
            while (firstNonZeroIndex < filtered.length() && filtered.charAt(firstNonZeroIndex) == '0') {
                firstNonZeroIndex++;
            }

            // Если после удаления сломанной цифры строка пуста или только нули...
            if (firstNonZeroIndex == filtered.length()) {
                System.out.println(0);
            } else {
                // Выводим строку начиная с первого ненулевого символа.
                for (int i = firstNonZeroIndex; i < filtered.length(); i++) {
                    System.out.print(filtered.charAt(i));
                }
                System.out.println();
            }
        }

        scanner.close();
    }
}