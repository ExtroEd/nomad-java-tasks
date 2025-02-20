package beecrowd_1170;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        // Создаём объект Scanner для чтения входных данных.
        Scanner scanner = new Scanner(System.in);

        // Читаем количество тестов.
        int N = scanner.nextInt();

        // Цикл по каждому тестовому случаю.
        for (int i = 0; i < N; i++) {
            // Читаем количество Х для текущего теста.
            double X = scanner.nextDouble();

            // Счётчик дней.
            int days = 0;

            // Пока пищи больше 1 кг, Blobs съедает половину.
            while (X > 1.0) {
                X /= 2.0;
                days++;
            }

            // Выводим количество дней и слово "bias".
            System.out.println(days + " dias");
        }

        // Закрываем сканнер.
        scanner.close();
    }
}