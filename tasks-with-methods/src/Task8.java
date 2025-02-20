public class Task8 {
    public static String repeatString(String input, int count) {
        String repeatedString = input.repeat(count);

        return repeatedString;
    }

    public static void main(String[] args) {
        System.out.print(repeatString("Java", 3));
    }
}
