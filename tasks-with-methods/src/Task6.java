public class Task6 {
    public static double fahrenheitToCelsius(double fahrenheit) {
        double celsius = (fahrenheit - 32) * 5 / 9;
        return celsius;
    }

    public static void main(String[] args) {
        System.out.println("Градусов по Цельсию: " + fahrenheitToCelsius(98.6));
    }
}
