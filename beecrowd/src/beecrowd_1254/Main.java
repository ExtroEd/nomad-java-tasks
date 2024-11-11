// package beecrowd_1254;

// import java.util.Scanner;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

// public class Main {
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         while (scanner.hasNextLine()) {
//             // Чтение данных для текущего случая
//             String tagText = scanner.nextLine().trim();
//             int tagNumber = Integer.parseInt(scanner.nextLine().trim());
//             String documentText = scanner.nextLine();

//             // Шаблон для поиска открывающих и закрывающих тегов
//             String tagPattern = "(?i)<(/?)" + tagText + "(.*?)>"; // Находит <tagText...> и </tagText...>
//             Pattern pattern = Pattern.compile(tagPattern);
//             Matcher matcher = pattern.matcher(documentText);

//             // StringBuffer для построения результата
//             StringBuffer updatedDocument = new StringBuffer();

//             // Проходим по каждому тегу и заменяем внутри него tagText на tagNumber
//             while (matcher.find()) {
//                 // Определяем префикс ("/" для закрывающего тега или пустая строка для открывающего)
//                 String prefix = matcher.group(1);
//                 String suffix = matcher.group(2);

//                 // Многократная замена внутри одного тега
//                 String processedSuffix = suffix.replaceAll("(?i)" + tagText, String.valueOf(tagNumber));

//                 // Формируем новый тег с заменой
//                 String newTag = "<" + prefix + tagNumber + processedSuffix + ">";
//                 matcher.appendReplacement(updatedDocument, newTag);
//             }
//             matcher.appendTail(updatedDocument);

//             // Выводим окончательный текст
//             System.out.println(updatedDocument.toString());
//         }
//         scanner.close();
//     }
// }

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String tag = scanner.next();
            int num = scanner.nextInt();
            scanner.nextLine();
            String inputString = scanner.nextLine();
            processInputString(tag, String.valueOf(num), inputString);
        }

        scanner.close();
    }

    private static void processInputString(String tag, String num, String text) {
        ArrayList<String> arr = new ArrayList<>();
        boolean a = false;
        StringBuilder tagBuilder = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '<') {
                a = true;
                tagBuilder = new StringBuilder();
            }
            if (a) {
                tagBuilder.append(c);
            }
            if (c == '>') {
                a = false;
                arr.add(tagBuilder.toString());
            }
        }

        for (String tagInArr : arr) {
            text = text.replaceAll("(?i)" + tagInArr, tagInArr.replaceAll("(?i)" + tag, num));
        }

        System.out.println(text);
    }
}