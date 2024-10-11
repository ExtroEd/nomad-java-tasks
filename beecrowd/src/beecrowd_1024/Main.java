package beecrowd_1024;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Чтение количества строк
        int n = sc.nextInt();
        sc.nextLine(); // Пропустить перевод строки после числа

        for (int i = 0; i < n; i++) {
            String input = sc.nextLine();

            // Сдвигаем буквы на 3 позийии вправо
            StringBuilder shifted = new StringBuilder();
            for (char c : input.toCharArray()) {
                if (Character.isLetter(c)) {
                    c += 3;
                }
                shifted.append(c);
            }

            // Разворачиваем строку
            shifted.reverse();

            // Сдвиг второй половины на одну позицию влево
            int halfLength = shifted.length() / 2;
            for (int j = halfLength; j < shifted.length(); j++) {
                shifted.setCharAt(j, (char)(shifted.charAt(j) - 1));
            }

            // Вывод результата для данной строки
            System.out.println(shifted.toString());
        }
        sc.close();
    }
}