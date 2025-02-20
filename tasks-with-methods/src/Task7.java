public class Task7 {
    public static String reverseString(String input) {
        StringBuilder reversedString = new StringBuilder(); // Класс, который позволяет изменять строку без создания новых объектов на каждой итерации
        
        for (int i = input.length() - 1; i >= 0; i--) {
            reversedString.append(input.charAt(i));
        }

        return reversedString.toString();
    }

    public static void main(String[] args) {
        System.out.println(reverseString("Привет"));
    }
}