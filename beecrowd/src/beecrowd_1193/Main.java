package beecrowd_1193;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // Количество тестов.
        sc.nextLine(); // Пропустить оставшуюся часть строки.

        for (int i = 1; i <= N; i++) {
            String[] input = sc.nextLine().split(" ");
            String number = input[0]; // Число (в двоичной, десятичной или шестнадцатеричной системе).
            String format = input[1]; // Формат числа (bin, dec, hex).

            System.out.println("Case " + i + ":");
            
            switch (format) {
                case "bin":
                    int decimalFromBin = Integer.parseInt(number, 2); // Двоичое -> Десятичное.
                    System.out.println(decimalFromBin + " dec");
                    System.out.println(Integer.toHexString(decimalFromBin)+ " hex"); // Двоичное -> Шестнадцатеричное.
                    break;
                case "dec":
                    int decimal = Integer.parseInt(number); // Уже в двоичной.
                    System.out.println(Integer.toHexString(decimal) + " hex"); // Десятичное -> Шестнадцатеричное.
                    System.out.println(Integer.toBinaryString(decimal)+ " bin"); // Десятичное -> Двоичное.
                    break;
                case "hex":
                    int decimalFromHex = Integer.parseInt(number, 16); // Шестнадцатеричное -> Десятичное.
                    System.out.println(decimalFromHex + " dec");
                    System.out.println(Integer.toBinaryString(decimalFromHex)+ " bin"); // Шестнадцатеричное -> Двоичное.
                    break;
            }
            System.out.println(); // Печать пустой строки, после каждого теста.
        }

        sc.close();
    }
}