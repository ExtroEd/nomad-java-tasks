package beecrowd_1287;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // Инициализация объекта StringBuilder для накопления символов, которые будут составлять итоговое число
            StringBuilder numberStr = new StringBuilder();

            for (char ch : line.toCharArray()) {
                if (ch == 'l') {
                    numberStr.append('1');
                } else if (ch == 'o' || ch == 'O') {
                    numberStr.append('0');
                } else if (Character.isDigit(ch)) {
                    numberStr.append(ch);
                } else if (ch == ' ' || ch == ',') {
                    continue;
                } else {
                    numberStr = new StringBuilder("error");
                    break;
                }
            }

            if (numberStr.toString().equals("error")) {
                System.out.println("error");
            } else {
                try {
                    // Пробуем преобразовать строку в число
                    long number = Long.parseLong(numberStr.toString());
                    // Проверяем на переполнение
                    if (number > Integer.MAX_VALUE) {
                        System.out.println("error");
                    } else {
                        System.out.println(number);
                    }
                // Этот блок catch перехватывает исключение, если строка не может быть преобразована в число (например, если строка пуста или содержит недопустимые символы)
                } catch (NumberFormatException e) {
                    System.out.println("error");
                }
            }
        }
        
        scanner.close();
    }
}
