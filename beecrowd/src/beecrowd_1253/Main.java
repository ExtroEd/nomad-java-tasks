package beecrowd_1253;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Создаём объект для ввода данных с клавиатуры.

        int n = scanner.nextInt(); // Читаем первое число - количество тестов.
        scanner.nextLine(); // Пропускаем остаток строки после ввода числа.

        for (int i = 0; i < n; i++) {
            String encodedText = scanner.nextLine(); // Считываем строку, которая зашифрована.
            int shift = scanner.nextInt(); // Считываем число сдвигов для тегущего теста.
            scanner.nextLine(); // Пропускаем остаток строки после ввода числа.

            String decodedText = decodeCeasarCipher(encodedText, shift); // Вызываем метод для декодирования строки.
            System.out.println(decodedText);
        }

        scanner.close(); // Закрываем объект сканера после завершения работы с вводом.
    }

    public static String decodeCeasarCipher(String encodedText, int shift) {
        StringBuilder decodedText = new StringBuilder(); // Создаём объект для построения декодированной строки.

        for (char ch : encodedText.toCharArray()) { // Проходим по каждому символу зашифрованного текста.
            char decodedChar = (char) (ch - shift); // Выполняем сдвиг влево на указанное количество позиций.

            if (decodedChar < 'A') {
                decodedChar += 26; // Делаем переход к букве 'Z' путём прибавления 26.
            }

            decodedText.append(decodedChar); // Добавляем декодированный символ в строку.
        }

        return decodedText.toString();
    }
}