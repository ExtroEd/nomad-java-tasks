public class Task4 {
    public static int countVowels(String input) {
        int vowels = 0;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (ch == 'а' || ch == 'е' || ch == 'ё' || ch == 'о' || ch == 'у' || ch == 'ы' || ch == 'э' || ch == 'я' || ch == 'ю' || ch == 'и') {
                vowels++;
            }
        }

        return vowels;
    }

    public static void main(String[] args) {
        String sentence = "Привет";

        System.out.println("Количество гласных: " + countVowels(sentence));
    }
}
